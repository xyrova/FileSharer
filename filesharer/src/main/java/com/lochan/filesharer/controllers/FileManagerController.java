package com.lochan.filesharer.controllers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lochan.filesharer.model.FileEntity;
import com.lochan.filesharer.services.FileStorageService;

@RestController
@CrossOrigin("http://localhost:5173/")
public class FileManagerController {

    @Autowired
    private FileStorageService fileStorageService;

    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());

    // File upload: Save file to disk and generate a PIN
    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Save the file on disk and return the generated PIN
            String pin = fileStorageService.saveFile(file);
            return ResponseEntity.ok(pin); // Return the PIN to the client
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception during file upload", e);
            return ResponseEntity.status(500).body("File upload failed");
        }
    }

    // File download: Retrieve the file using the PIN, check expiration and one-time
    // download
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("pin") String pin) {
        try {
            // Get the file entity using the PIN
            FileEntity fileEntity = fileStorageService.getFileByPin(pin);

            // Check if the file exists
            if (fileEntity == null) {
                return ResponseEntity.status(404).body(null); // File not found
            }

            // Check if the file has expired
            if (fileStorageService.isExpired(fileEntity)) {
                return ResponseEntity.status(403).body(null); // File has expired
            }

            // Check if the file has already been downloaded (one-time download)
            if (fileEntity.isDownloaded()) {
                return ResponseEntity.status(403).body(null); // File already downloaded
            }

            // Load the file from the file system using the file path
            java.io.File fileOnDisk = fileStorageService.getDownloadFile(fileEntity.getFilePath());
            if (!fileOnDisk.exists()) {
                return ResponseEntity.status(404).body(null); // File not found on disk
            }

            // Mark the file as downloaded
            fileStorageService.markFileAsDownloaded(fileEntity);

            // Load the file into an InputStreamResource for download
            InputStreamResource resource = new InputStreamResource(new FileInputStream(fileOnDisk));

            // Return the file as a download response
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .contentLength(fileOnDisk.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            log.log(Level.SEVERE, "Error during file download", e);
            return ResponseEntity.status(500).body(null);
        }
    }
}

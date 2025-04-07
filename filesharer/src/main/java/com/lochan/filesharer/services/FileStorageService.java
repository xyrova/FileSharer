package com.lochan.filesharer.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lochan.filesharer.model.FileEntity;
import com.lochan.filesharer.repository.FileRepository;

import jakarta.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;
import org.slf4j.Logger;
import java.nio.file.Path;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    // Inject the storage directory from application properties
    @Value("${file.storage.directory}")
    private String storageDirectoryPath;

    private Path storageLocation;

    @Autowired
    private FileRepository fileRepository;

    @PostConstruct
    public void init() {
        this.storageLocation = Paths.get(storageDirectoryPath);
        initStorageDirectory();
    }

    private void initStorageDirectory() {
        try {
            // Create directories if they don't exist
            if (!Files.exists(storageLocation)) {
                logger.info("Creating storage directory: {}", storageLocation);
                Files.createDirectories(storageLocation);
                logger.info("Storage directory created successfully");
            }
        } catch (IOException e) {
            logger.error("Could not initialize storage directory: {}", e.getMessage());
            throw new RuntimeException("Could not initialize storage directory", e);
        }
    }

    // Save the file into the file system and store the file path in the database
    public String saveFile(MultipartFile fileToSave) throws IOException {
        if (fileToSave == null) {
            throw new NullPointerException("File is null");
        }

        // Generate a unique file name to avoid overwriting files with the same name
        String fileName = System.currentTimeMillis() + "_" + fileToSave.getOriginalFilename();

        // Ensure the file path stays within the intended directory (security check)
        File targetFile = new File(storageDirectoryPath + File.separator + fileName);
        if (!Objects.equals(targetFile.getParent(), storageDirectoryPath)) {
            throw new SecurityException("Unsupported filename!");
        }

        // Copy the file to the storage directory
        Files.copy(fileToSave.getInputStream(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Generate a unique 4-digit PIN
        String pin = generatePin();

        LocalDateTime uploadTime = LocalDateTime.now();
        LocalDateTime expirationTime = uploadTime.plusHours(24); // File expires in 24 hours

        // Save the file metadata (file path) to the database
        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName); // Store the original file name
        fileEntity.setFilePath(targetFile.getAbsolutePath()); // Store the file path
        fileEntity.setPin(pin);
        fileEntity.setUploadTime(uploadTime);
        fileEntity.setExpirationTime(expirationTime);
        fileEntity.setDownloaded(false); // File hasn't been downloaded yet

        // Save file metadata in the database
        fileRepository.save(fileEntity);

        return pin; // Return the generated PIN for accessing the file
    }

    // Retrieve the file entity using the PIN
    public FileEntity getFileByPin(String pin) {
        return fileRepository.findByPin(pin);
    }

    // Mark the file as downloaded (to enforce one-time download)
    public void markFileAsDownloaded(FileEntity fileEntity) {
        fileEntity.setDownloaded(true);
        fileRepository.save(fileEntity);
    }

    // Check if the file has expired
    public boolean isExpired(FileEntity fileEntity) {
        return LocalDateTime.now().isAfter(fileEntity.getExpirationTime());
    }

    // Retrieve the file from the file system using the stored file path
    public File getDownloadFile(String filePath) throws IOException {
        if (filePath == null) {
            throw new NullPointerException("filePath is null");
        }

        File fileToDownload = new File(filePath);

        // Ensure the file path is valid and within the allowed directory
        if (!fileToDownload.getCanonicalPath().startsWith(storageDirectoryPath)) {
            throw new SecurityException("Unsupported filename!");
        }

        if (!fileToDownload.exists()) {
            throw new IOException("No file found at: " + filePath);
        }

        return fileToDownload; // Return the file for download
    }

    // Helper method to generate a random 4-digit PIN
    private String generatePin() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
}

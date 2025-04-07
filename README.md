**File Sharing App**

A simple file-sharing web application that allows users to upload a file and generate a unique PIN for secure file sharing. Other users can download the file using the correct PIN, ensuring privacy and ease of access.

File Upload: Upload a single file at a time.
PIN Generation: A unique PIN is generated for each uploaded file.
Secure Download: Only users with the correct PIN can download the file.
No Signup Required: Share files without the need for creating an account.

_Technologies Used_

Frontend: React, Tailwind CSS
Backend: Spring Boot, Java
Database: Mysql
File Handling: Multipart File Upload via Axios, File System storage
Security: PIN-based file access control

_Prerequisites_

Node.js: Download Node.js
Java 17: Download Java
Maven: Download Maven

`git clone https://github.com/nahcol10/file-sharer.git
cd file-sharer/backend`

Configure application properties: In src/main/resources/application.properties, you can update settings if needed, especially for storage locations and database configurations. For example

_properties_

Run the Spring Boot application

`mvn spring-boot:run`

By default, the backend will be running at `http://localhost:8080`

Navigate to the frontend directory:

Install the dependencies:

Run the development server:

`npm start`

The frontend will be running at `http://localhost:5173`

Running the Application

_Upload File:_
Navigate to the upload page (`http://localhost:5173`).
Upload a file. After the file is uploaded, a PIN will be generated and displayed. Share this PIN with the intended recipient.

_Download File:_
The recipient can use the same frontend to input the PIN on the download page (`http://localhost:5173/download`) and retrieve the file.

**API Endpoints**

_File Upload_

URL: /upload-file
Method: POST
Request Body: MultipartFile
Response: A unique PIN associated with the uploaded file

_File Download_

URL: /download
Method: GET
Query Parameter: pin (The unique PIN to access the file)
Response: The requested file for download

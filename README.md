File Sharing App

A simple file-sharing web application that allows users to upload a file and generate a unique PIN for secure file sharing. Other users can download the file using the correct PIN, ensuring privacy and ease of access.
Features

    File Upload: Upload a single file at a time.
    PIN Generation: A unique PIN is generated for each uploaded file.
    Secure Download: Only users with the correct PIN can download the file.
    No Signup Required: Share files without the need for creating an account.

Technologies Used

    Frontend: React, Tailwind CSS
    Backend: Spring Boot, Java
    Database: Mysql
    File Handling: Multipart File Upload via Axios, File System storage
    Security: PIN-based file access control

Prerequisites

To run this project, you need to have the following installed:

    Node.js: Download Node.js
    Java 17: Download Java
    Maven: Download Maven

Getting Started
Backend Setup (Spring Boot)

    Clone the repository:

    bash

git clone https://github.com/nahcol10/file-sharer.git
cd file-sharer/backend

Configure application properties: In src/main/resources/application.properties, you can update settings if needed, especially for storage locations and database configurations. For example:

properties

# File upload settings
file.upload-dir=./uploads

Run the Spring Boot application:

bash

    mvn spring-boot:run

    By default, the backend will be running at http://localhost:8080.

Frontend Setup (React)

    Navigate to the frontend directory:

    bash

cd ../frontend

Install the dependencies:

bash

npm install

Run the development server:

bash

    npm start

    The frontend will be running at http://localhost:5173.

Running the Application

    Upload File:
        Navigate to the upload page (http://localhost:5173).
        Upload a file. After the file is uploaded, a PIN will be generated and displayed. Share this PIN with the intended recipient.

    Download File:
        The recipient can use the same frontend to input the PIN on the download page (http://localhost:5173/download) and retrieve the file.

API Endpoints
File Upload

    URL: /upload-file
    Method: POST
    Request Body: MultipartFile
    Response: A unique PIN associated with the uploaded file

File Download

    URL: /download
    Method: GET
    Query Parameter: pin (The unique PIN to access the file)
    Response: The requested file for download

Folder Structure

bash

file-sharer/
├── backend/
│   ├── src/
│   ├── target/
│   └── pom.xml
├── frontend/
│   ├── src/
│   ├── public/
│   └── package.json
└── README.md

    Add unit tests for backend services

License

This project is licensed under the MIT License - see the LICENSE file for details.

This README.md provides an overview of your project, setup instructions, and basic usage details. You can modify or add sections as you further develop the project! Let me know if you need any changes.

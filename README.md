# File Sharing App

## Overview

A simple file-sharing web application that allows users to upload a file and generate a unique PIN for secure file sharing. Other users can download the file using the correct PIN, ensuring privacy and ease of access.

## Features

- **File Upload**: Upload a single file at a time.
- **PIN Generation**: A unique PIN is generated for each uploaded file.
- **Secure Download**: Only users with the correct PIN can download the file.
- **No Signup Required**: Share files without the need for creating an account.

## Technologies Used

- **Frontend**: React, Tailwind CSS
- **Backend**: Spring Boot, Java
- **Database**: MySQL
- **File Handling**: Multipart File Upload via Axios, File System storage
- **Security**: PIN-based file access control

## Prerequisites

- Node.js
- Java 17
- Maven

## Installation

### Backend Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/nahcol10/file-sharer.git
   cd file-sharer/backend
   ```

2. Configure application properties:
   In src/main/resources/application.properties, you can update settings if needed, especially for storage locations and database configurations.

   # Add your custom configuration here

3. Run the Spring Boot application:
   `mvn spring-boot:run`
   By default, the backend will be running at `http://localhost:8080`

### Frontend Setup

1. Navigate to the frontend directory:
   `cd ../frontend`

2. Install the dependencies:
   `npm install`

3. Run the development server:
   `npm start`

The frontend will be running at `http://localhost:5173`

### Usage

1. Upload File

   Navigate to the upload page (`http://localhost:5173`).
   Upload a file. After the file is uploaded, a PIN will be generated and displayed.
   Share this PIN with the intended recipient.

2. Download File

   The recipient can use the same frontend to input the PIN on the download page (`http://localhost:5173/download`).
   After entering the correct PIN, they can download the file.

### API Endpoints

1. File Upload

   URL : /upload-file
   Method : POST
   Request Body : MultipartFile
   Response : A unique PIN associated with the uploaded file

2. File Download

   URL : /download
   Method : GET
   Query Parameter : pin (The unique PIN to access the file)
   Response : The requested file for download

import { useState } from "react";
import axios from "axios";

const FileUpload = () => {
  const [file, setFile] = useState(null);
  const [pin, setPin] = useState("");
  const [error, setError] = useState(null);
  const [message, setMessage] = useState("");

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
    setError(null);
    setMessage("");
  };

  const handleUpload = async (event) => {
    event.preventDefault();
    if (!file) {
      setError("Please select a file to upload.");
      return;
    }

    const formData = new FormData();
    formData.append("file", file);

    try {
      const response = await axios.post(
        "http://localhost:8080/upload-file",
        formData
      );
      if (response.data) {
        setPin(response.data);
        setMessage("File uploaded successfully! ");
        setError(null);
      }
    } catch (err) {
      setError("Failed to upload file.");
      setMessage("");
      console.error("Upload error:", err);
    }
  };

  return (
    <div className="bg-white shadow-lg p-6 rounded-lg w-1/2 mx-auto mt-5">
      <h2 className="text-2xl font-bold text-center mb-4">Upload File</h2>
      <form
        onSubmit={handleUpload}
        className="flex flex-col items-center space-y-4"
      >
        <input
          type="file"
          onChange={handleFileChange}
          className="border border-gray-300 p-2 rounded-lg"
        />
        <button
          type="submit"
          className="bg-blue-500 text-white py-2 px-4 rounded-lg hover:bg-blue-700 transition-all"
        >
          Upload
        </button>
      </form>
      {message && <p className="text-center text-green-500 mt-4">{message}</p>}
      {error && <p className="text-center text-red-500 mt-4">{error}</p>}
      {pin && (
        <p className="text-center text-gray-700 mt-4">
          Your download PIN:{" "}
          <span className="font-bold text-lg text-blue-700">{pin}</span>
        </p>
      )}
    </div>
  );
};

export default FileUpload;

import { useState } from "react";
import axios from "axios";

const FileDownload = () => {
  const [pin, setPin] = useState("");
  const [message, setMessage] = useState("");
  const [error, setError] = useState(null);

  const handlePinChange = (event) => {
    setPin(event.target.value);
  };

  const handleDownload = async (event) => {
    event.preventDefault();
    if (!pin) {
      setError("Please enter the PIN.");
      setMessage("");
      return;
    }

    try {
      const response = await axios.get(
        `http://localhost:8080/download?pin=${pin}`,
        {
          responseType: "blob", // Important for file download
        }
      );
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "downloaded-file");
      document.body.appendChild(link);
      link.click();
      link.remove();
      setMessage("Download started!");
      setError(null);
    } catch (err) {
      setError("Failed to download file. Please check the PIN or expiration.");
      setMessage("");
      console.error("Download error:", err);
    }
  };

  return (
    <div className="bg-white shadow-lg p-6 rounded-lg w-1/2 mx-auto mt-5">
      <h2 className="text-2xl font-bold text-center mb-4">Download File</h2>
      <form
        onSubmit={handleDownload}
        className="flex flex-col items-center space-y-4"
      >
        <input
          type="text"
          value={pin}
          onChange={handlePinChange}
          placeholder="Enter PIN"
          className="border border-gray-300 p-2 rounded-lg w-full"
        />
        <button
          type="submit"
          className="bg-green-500 text-white py-2 px-4 rounded-lg hover:bg-green-700 transition-all"
        >
          Download
        </button>
      </form>
      {message && <p className="text-center text-green-500 mt-4">{message}</p>}
      {error && <p className="text-center text-red-500 mt-4">{error}</p>}
    </div>
  );
};

export default FileDownload;

// import { useState } from "react";
// import { bulkRegisterUsers } from "../services/AdminApi";

// const BulkRegister = () => {
//   const [bulkFile, setBulkFile] = useState(null);

//   const handleBulkRegister = async () => {
//     if (!bulkFile) {
//       alert("Please select an Excel file");
//       return;
//     }
//     try {
//         const formData = new FormData();
//         formData.append("file", bulkFile);
    
//         const response = await bulkRegisterUsers(formData);
        
//         // Check if response is valid
//         if (response && response.data) {
//             const { message, registeredUsers, skippedUsers } = response.data;
    
//             // Build alert message
//             let alertMessage = message + "\n\n";
    
//             if (registeredUsers.length > 0) {
//                 alertMessage += " Registered Users:\n" + registeredUsers.join(", ") + "\n\n";
//             }
    
//             if (skippedUsers.length > 0) {
//                 alertMessage += " Skipped Users (Already Registered):\n" + skippedUsers.join(", ") + "\n";
//             }
    
//             alert(alertMessage);
//         } else {
//             alert("Error: Unexpected response from server.");
//         }
//     } catch (error) {
//       alert("Failed to register users in bulk: " + error);
//     }
//   };

//   return (
//     <div><br></br>
//       <h1 className="text-2xl font-bold ml-2 text-center mb-6">Bulk Register Users</h1><br></br>
//       <p><b className="text-red-500">Note:</b> The uploaded file must be in Excel format (.csv or .xls) and contain the following columns with exact headers:</p>
//         <ul className="p-4 list-disc" >
//             <li>Name</li>
//             <li>Email</li>
//             <li>Password</li>
//             <li>Role ID</li>
//         </ul>
   
//       <input type="file" onChange={(e) => setBulkFile(e.target.files[0])} className="border p-2 rounded w-full"/>
//       <button onClick={handleBulkRegister} className="mt-4 p-2 bg-green-600 text-white rounded">Upload</button>
//     </div>
//   );
// };

// export default BulkRegister;

import { useState } from "react";
import { bulkRegisterUsers } from "../services/AdminApi";

const BulkRegister = () => {
  const [bulkFile, setBulkFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [registeredUsers, setRegisteredUsers] = useState([]);
  const [skippedUsers, setSkippedUsers] = useState([]);
  const [message, setMessage] = useState("");

  const handleBulkRegister = async () => {
    if (!bulkFile) {
      alert("Please select an Excel file");
      return;
    }

    setLoading(true);
    setMessage("");
    setRegisteredUsers([]);
    setSkippedUsers([]);

    try {
      const formData = new FormData();
      formData.append("file", bulkFile);

      const response = await bulkRegisterUsers(formData);

      console.log("API Response:", response); // Debugging

      if (response && typeof response === "object") {
        const { message, registeredUsers = [], skippedUsers = [] } = response;

        setMessage(message);
        setRegisteredUsers(registeredUsers.filter(name => name.trim() !== ""));
        setSkippedUsers(skippedUsers.filter(name => name.trim() !== ""));
      } else {
        setMessage("Error: Unexpected response from server.");
      }
    } catch (error) {
      console.error("Error:", error);
      setMessage("Failed to register users in bulk.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 max-w-2xl mx-auto  shadow-lg rounded-lg">
      <h1 className="text-2xl font-bold text-center mb-4">Bulk Register Users</h1>
      <p>
        <b className="text-red-500">Note:</b> The uploaded file must be in Excel format (.csv or .xls) and contain these columns:
      </p>
      <ul className="p-4 list-disc list-inside">
        <li>Name</li>
        <li>Email</li>
        <li>Password</li>
        <li>Role ID</li>
      </ul>

      <input type="file" onChange={(e) => setBulkFile(e.target.files[0])} className="border p-2 rounded w-full mt-2"/>
      <button 
        onClick={handleBulkRegister} 
        className={`mt-4 p-2 text-white rounded w-full ${loading ? 'bg-gray-400' : 'bg-green-600 hover:bg-green-700'}`}
        disabled={loading}
      >
        {loading ? "Uploading..." : "Upload"}
      </button>

      {message && <p className="mt-4 text-lg font-semibold text-center">{message}</p>}

      {registeredUsers.length > 0 && (
        <div className="mt-6">
          <h2 className="text-lg font-bold text-green-600">Registered Users:</h2>
          <ul className="list-disc list-inside">
            {registeredUsers.map((user, index) => (
              <li key={index}>{user}</li>
            ))}
          </ul>
        </div>
      )}

      {skippedUsers.length > 0 && (
        <div className="mt-6">
          <h2 className="text-lg font-bold text-red-600">Skipped Users (Already Registered):</h2>
          <ul className="list-disc list-inside">
            {skippedUsers.map((user, index) => (
              <li key={index}>{user}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default BulkRegister;

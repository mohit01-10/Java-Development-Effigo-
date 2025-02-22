// import { useEffect, useState} from "react";
// import api from "../services/api";
// import { logoutUser } from '../services/util';
// import { useNavigate } from 'react-router-dom';

// // eslint-disable-next-line react/prop-types
// const ViewDocuments = ({ userType }) => {
//   const [documents, setDocuments] = useState([]);
//   const [users, setUsers] = useState([]); // List of users (for admin)
//   const [selectedUser, setSelectedUser] = useState(""); // Admin-selected user ID
//   const [loading, setLoading] = useState(false); // Loading state
//   const [error, setError] = useState(""); // Error message
//   const [refreshToken, setRefreshToken] = useState(localStorage.getItem("refreshToken"));

//   const navigate = useNavigate();

//   useEffect(() => {
//     if (userType === "admin") {
//       api.get("/admin/users")
//         .then(response => setUsers(response.data))
//         .catch(error => setError("Error fetching users."+error));
//     } else {
//       fetchDocuments("/user/documents"); // Fetch documents for logged-in user
//     }
//   }, [userType]);

//   const fetchDocuments = (endpoint) => {
//     setLoading(true);
//     setError("");

//     api.get(endpoint)
//       .then(response => {
//         setDocuments(response.data);
//         if (response.data.length === 0) {
//           setError("No documents!");
//         }
//       })
//       .catch(error => {
//         console.error("Error fetching documents:", error.response?.data || error.message);

//         if (error.response?.status === 403) {
//             const errorMessage = error.response?.data?.error || "";

//             //  If user is INACTIVE, log them out
//             if (errorMessage.includes("User is INACTIVE")) {
//                 alert("Your account has been deactivated. You will be logged out.");
//                 logoutUser(refreshToken, setRefreshToken);
//                 navigate("/login");
//                 return;
//             }
//         }

//         setError("Error fetching documents.");
//       })
//       .finally(() => setLoading(false));
//   };

//   const handleViewDocuments = () => {
//     if (!selectedUser) {
//       setError("Please select a user.");
//       return;
//     }
//     fetchDocuments(`/admin/documents/${selectedUser}`);
//   };

//   return (
//     <div className=" rounded shadow"><br></br>
//       <h1 className="text-2xl font-bold ml-2 text-center mb-6">Financial Documents</h1><br></br>

//       {userType === "admin" && (
//         <>
//           {/* Dropdown for Admin to Select a User */}
//           <div className="mb-4">
  
//             <select
//               value={selectedUser}
//               onChange={(e) => setSelectedUser(e.target.value)}
//               className="border p-2 rounded w-full"
//             >
//               <option value="">-- Select User --</option>
//               {users.map(user => (
//                 <option key={user.uid} value={user.uid}>
//                   {user.name} 
//                 </option>
//               ))}
//             </select>
//           </div>

//           <button
//             onClick={handleViewDocuments}
//             className="bg-blue-600 text-white p-2 rounded hover:bg-blue-800"
//           >
//             View Documents
//           </button>
//         </>
//       )}

//       {/* Loading State */}
//       {loading && <p className="mt-4 text-gray-600">Loading documents...</p>}

//       {/* Error or No Documents */}
//       {error && !loading && <p className="mt-4 text-red-600">{error}</p>}

//       {/* Display Documents */}
//       {!error && documents.length > 0 && (
//         <ul className="mt-4">
//           {documents.map((doc, index) => (
//             <li key={doc.docId} className="border-b p-2">
//               {index + 1}.{" "}
//               <a href={doc.docUrl} target="_blank" rel="noopener noreferrer" className="ml-4 hover:text-blue-600 hover:underline">
//                 {doc.docName}
//               </a>
//             </li>
//           ))}
//         </ul>
//       )}
//     </div>
//   );
// };

// export default ViewDocuments;


import { useEffect, useState, useCallback } from "react";
import api from "../services/api";
import { logoutUser } from '../services/util';
import { useNavigate } from 'react-router-dom';

// eslint-disable-next-line react/prop-types
const ViewDocuments = ({ userType }) => {
  const [documents, setDocuments] = useState([]);
  const [users, setUsers] = useState([]); // List of users (for admin)
  const [selectedUser, setSelectedUser] = useState(""); // Admin-selected user ID
  const [loading, setLoading] = useState(false); // Loading state
  const [error, setError] = useState(""); // Error message
  const [refreshToken, setRefreshToken] = useState(localStorage.getItem("refreshToken"));
  
  const navigate = useNavigate();

  //  Define fetchDocuments using useCallback (fixes useEffect dependency issue)
  const fetchDocuments = useCallback((endpoint) => {
    setLoading(true);
    setError("");

    api.get(endpoint)
      .then(response => {
        setDocuments(response.data);
        if (response.data.length === 0) {
          setError("No documents!");
        }
      })
      .catch(error => {
        console.error("Error fetching documents:", error.response?.data || error.message);

        if (error.response?.status === 403) {
            const errorMessage = error.response?.data?.error || "";

            //  If user is INACTIVE, log them out
            if (errorMessage.includes("User is INACTIVE")) {
                alert("Your account has been deactivated. You will be logged out.");
                logoutUser(refreshToken, setRefreshToken);
                navigate("/login");
                return;
            }
        }

        setError("Error fetching documents.");
      })
      .finally(() => setLoading(false));
  }, [refreshToken, navigate, setRefreshToken]); // Dependencies for useCallback

  //  useEffect for Initial Data Fetching
  useEffect(() => {
    if (userType === "admin") {
      api.get("/admin/users")
        .then(response => setUsers(response.data))
        .catch(error => setError("Error fetching users: " + error));
    } else {
      fetchDocuments("/user/documents"); // Fetch documents for logged-in user
    }
  }, [userType, fetchDocuments]); //  Add fetchDocuments as a dependency (fix warning)

  //  Fetch Documents for Admin
  const handleViewDocuments = () => {
    if (!selectedUser) {
      setError("Please select a user.");
      return;
    }
    fetchDocuments(`/admin/documents/${selectedUser}`);
  };

  return (
    <div className="rounded shadow p-4">
      <h1 className="text-2xl font-bold text-center mb-6">Financial Documents</h1>

      {userType === "admin" && (
        <>
          {/* Dropdown for Admin to Select a User */}
          <div className="mb-4">
            <select
              value={selectedUser}
              onChange={(e) => setSelectedUser(e.target.value)}
              className="border p-2 rounded w-full"
            >
              <option value="">-- Select User --</option>
              {users.map(user => (
                <option key={user.uid} value={user.uid}>
                  {user.name} 
                </option>
              ))}
            </select>
          </div>

          <button
            onClick={handleViewDocuments}
            className="bg-blue-600 text-white p-2 rounded hover:bg-blue-800"
          >
            View Documents
          </button>
        </>
      )}

      {/* Loading State */}
      {loading && <p className="mt-4 text-gray-600">Loading documents...</p>}

      {/* Error or No Documents */}
      {error && !loading && <p className="mt-4 text-red-600">{error}</p>}

      {/* Display Documents */}
      {!error && documents.length > 0 && (
        <ul className="mt-4">
          {documents.map((doc, index) => (
            <li key={doc.docId} className="border-b p-2">
              {index + 1}.{" "}
              <a href={doc.docUrl} target="_blank" rel="noopener noreferrer" className="ml-4 hover:text-blue-600 hover:underline">
                {doc.docName}
              </a>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ViewDocuments;

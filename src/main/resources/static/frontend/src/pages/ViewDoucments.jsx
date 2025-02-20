import { useEffect, useState } from "react";
import api from "../services/api";

// eslint-disable-next-line react/prop-types
const ViewDocuments = ({ userType }) => {
  const [documents, setDocuments] = useState([]);
  const [users, setUsers] = useState([]); // List of users (for admin)
  const [selectedUser, setSelectedUser] = useState(""); // Admin-selected user ID
  const [loading, setLoading] = useState(false); // Loading state
  const [error, setError] = useState(""); // Error message

  useEffect(() => {
    if (userType === "admin") {
      api.get("/admin/users")
        .then(response => setUsers(response.data))
        .catch(error => setError("Error fetching users."+error));
    } else {
      fetchDocuments("/user/documents"); // Fetch documents for logged-in user
    }
  }, [userType]);

  const fetchDocuments = (endpoint) => {
    setLoading(true);
    setError("");

    api.get(endpoint)
      .then(response => {
        setDocuments(response.data);
        if (response.data.length === 0) {
          setError("No documents!");
        }
      })
      .catch(() => setError("Error fetching documents."))
      .finally(() => setLoading(false));
  };

  const handleViewDocuments = () => {
    if (!selectedUser) {
      setError("Please select a user.");
      return;
    }
    fetchDocuments(`/admin/documents/${selectedUser}`);
  };

  return (
    <div className=" rounded shadow"><br></br>
      <h1 className="text-2xl font-bold ml-2 text-center mb-6">Financial Documents</h1><br></br>

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

import { useEffect, useState } from "react";
import { getAllUsers, updateUser, deleteUser, uploadDocument, toggleUserStatus } from "../services/AdminApi";

const UserManagement = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [editFormData, setEditFormData] = useState({ name: "", email: "", phone: "", role: "", status: "" });
  const [uploadFile, setUploadFile] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [isEditing, setIsEditing] = useState(false);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await getAllUsers();
      setUsers(response);
    } catch (error) {
      alert("Failed to load users: " + error);
    }
  };

  const handleViewUser = (user) => {
    setSelectedUser(user);
    setEditFormData({ 
      name: user.name, 
      email: user.email, 
      phone: user.phone, 
      role: user.role, 
      status: user.status 
    });
    setShowModal(true);
    setIsEditing(false);
  };

  const handleEditChange = (e) => {
    setEditFormData({ ...editFormData, [e.target.name]: e.target.value });
  };

  const handleSaveChanges = async () => {
    try {
      await updateUser(selectedUser.uid, editFormData);
      fetchUsers();
      setShowModal(false);
      alert("User updated successfully!");
    } catch (error) {
      alert("Failed to update user: " + error);
    }
  };

  const handleDeleteUser = async () => {
    if (window.confirm("Are you sure you want to delete this user?")) {
      try {
        await deleteUser(selectedUser.uid);
        fetchUsers();
        setShowModal(false);
        alert("User deleted successfully!");
      } catch (error) {
        alert("Failed to delete user: " + error);
      }
    }
  };

  const handleFileChange = (e) => {
    setUploadFile(e.target.files[0]);
  };

  const handleUploadDocument = async () => {
    if (!uploadFile) {
      alert("Please select a file before uploading.");
      return;
    }
    try {
      const formData = new FormData();
      formData.append("file", uploadFile);
      await uploadDocument(selectedUser.uid, formData);
      alert("Document uploaded successfully!");
      setUploadFile(null);
    } catch (error) {
      alert("Failed to upload document: " + error);
    }
  };

  const handleChangeStatus = async () => {
    try {
      const response = await toggleUserStatus(selectedUser.uid); // Call the API function
      alert(response); // Display the updated status message
      fetchUsers(); // Refresh the user list
      setShowModal(false);
    } catch (error) {
      alert("Failed to update status: " + error);
    }
  };

  return (
    <div className="container mx-auto p-6">
        
      <h1 className="text-2xl font-bold ml-2 text-center mb-6">User Management</h1>

      <div className="overflow-x-auto">
        <table className="w-full border-collapse border border-gray-300 shadow-lg">
          <thead>
            <tr className="text-left bg-white text-black">
              <th className="p-3 border">S.No.</th>
              <th className="p-3 border">Name</th>
              <th className="p-3 border">Email</th>
              <th className="p-3 border">Phone</th>
              <th className="p-3 border">Role</th>
              <th className="p-3 border">Status</th>
              <th className="p-3 border">Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user, index) => (
              <tr key={user.uid} className="border-b">
                <td className="p-3">{index + 1}</td>
                <td className="p-3">{user.name}</td>
                <td className="p-3">{user.email}</td>
                <td className="p-3">{user.phone}</td>
                <td className="p-3">{user.role}</td>
                {/* <td className="p-3">{user.status}</td> */}
                <td>
                <span className={`px-3 py-1 rounded text-white text-sm ${user.status === "ACTIVE" ? "bg-green-500" : "bg-red-500"}`}>
                        {user.status}
                </span>
                </td>
                <td className="p-3">
                  <button 
                    onClick={() => handleViewUser(user)} 
                    className="px-3 py-1 bg-blue-500 text-white rounded"
                  >
                    View
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Modal Layover */}
      {showModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white text-black p-6 rounded shadow-lg w-96">
            <h2 className="text-xl font-semibold text-center mb-4">User Details</h2>

            <label className="block text-sm font-semibold">Name:</label>
            <input 
              type="text" 
              name="name" 
              value={editFormData.name} 
              onChange={handleEditChange} 
              disabled={!isEditing}
              className={`border p-2 rounded w-full mb-2 ${!isEditing ? "bg-gray-100" : "bg-white"}`}
            />

            <label className="block text-sm font-semibold">Email:</label>
            <input 
              type="email" 
              name="email" 
              value={editFormData.email} 
              readOnly 
              className="border p-2 rounded w-full bg-gray-100 mb-2 cursor-not-allowed"
            />

            <label className="block text-sm font-semibold">Phone:</label>
            <input 
              type="text" 
              name="phone" 
              value={editFormData.phone} 
              onChange={handleEditChange} 
              disabled={!isEditing}
              className={`border p-2 rounded w-full mb-2 ${!isEditing ? "bg-gray-100" : "bg-white"}`}
            />

            <label className="block text-sm font-semibold">Role:</label>
            <select 
              name="role" 
              value={editFormData.role} 
              onChange={handleEditChange} 
              disabled={!isEditing}
              className={`border p-2 rounded w-full mb-4 ${!isEditing ? "bg-gray-100" : "bg-white"}`}
            >
              <option value="1">Admin</option>
              <option value="2">User</option>
            </select>

            <label className="block text-sm font-semibold">Status:</label>
            <input 
              type="text" 
              name="status" 
              value={editFormData.status} 
              readOnly 
              className="border p-2 rounded w-full bg-gray-100 mb-4 cursor-not-allowed"
            />

            <div className="flex flex-col space-y-2">
              {!isEditing ? (
                <button onClick={() => setIsEditing(true)} className="px-4 py-2 bg-yellow-500 text-white rounded">
                  Edit
                </button>
              ) : (
                <button onClick={handleSaveChanges} className="px-4 py-2 bg-green-500 text-white rounded">
                  Save Changes
                </button>
              )}

              <button onClick={handleDeleteUser} className="px-4 py-2 bg-red-500 text-white rounded">
                Delete
              </button>

              <button onClick={handleChangeStatus} className="px-4 py-2 bg-purple-500 text-white rounded">
                Change Status
              </button>

              <input type="file" onChange={handleFileChange} className="border p-2 rounded w-full" />
              <button onClick={handleUploadDocument} className="px-4 py-2 bg-blue-600 text-white rounded">
                Upload Document
              </button>

              <button onClick={() => setShowModal(false)} className="px-4 py-2 bg-gray-500 text-white rounded">
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserManagement;

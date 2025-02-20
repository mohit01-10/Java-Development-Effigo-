// import { useEffect, useState } from "react";
// import { useNavigate, Link, Routes, Route } from "react-router-dom";
// import api from "../services/api";
// //import { getAllUsers, updateUser, deleteUser, uploadDocument, getLoginHistory, bulkRegisterUsers } from "../services/AdminApi";
// import UserManagement from "./UserManagement";
// import LoginHistory from "./LoginHistory";
// import BulkRegister from "./BulkRegister";
// import Messages from "./Messages";
// import Profile from "./Profile";
// import ViewDocuments from "./ViewDoucments";
// const AdminDashboard = () => {
//   const [user, setUser] = useState(null);
//   const navigate = useNavigate();

//   useEffect(() => {
//     const storedUser = localStorage.getItem("user");
//     if (storedUser) {
//       setUser(JSON.parse(storedUser));
//     } else {
//       api.get("/api/current-user")
//         .then((response) => {
//           if (response.data?.role === "superAdmin" || response.data?.role === "Admin" ) {
//             localStorage.setItem("user", JSON.stringify(response.data));
//             setUser(response.data);
//           } else {
//             navigate("/login");
//           }
//         })
//         .catch(() => navigate("/login"));
//     }
//   }, [navigate]);

//   const handleLogout = () => {
//     api.post("/logout");
//     localStorage.removeItem("user");
//     navigate("/login");
//   };
  

//   return (
//     <div className="min-h-screen flex">
//       {/* Sidebar */}
//       <div className="w-64 bg-blue-700 text-white p-4">
//         <h2 className="text-2xl font-bold">Admin Dashboard</h2>
//         <ul className="mt-4 space-y-2">
//           <li><Link to="/admin/profile" className="block p-2 hover:bg-blue-800 rounded">View Profile</Link></li>
//           <li><Link to="/admin/edit-users" className="block p-2 hover:bg-blue-800 rounded">View All Users</Link></li>
//           <li><Link to="/admin/login-history" className="block p-2 hover:bg-blue-800 rounded">Login History</Link></li>
//           <li><Link to="/admin/bulk-register" className="block p-2 hover:bg-blue-800 rounded">Bulk Register</Link></li>
//           <li><Link to="/admin/documents" className="block p-2 hover:bg-blue-800 rounded">View Documents</Link></li>
//           <li><Link to="/admin/messages" className="block p-2 hover:bg-blue-800 rounded">Messages</Link></li>
//           <li><button onClick={handleLogout} className="block w-full p-2 text-left hover:bg-red-600 rounded">Logout</button></li>
//         </ul>
//       </div>

//       {/* Main Content */}
//       <div className="flex-1 p-6">
//       <h1 className="text-3xl font-bold">
//         {user ? `Welcome ${user.name}!` : "Loading..."}
//       </h1>
//         {/* {user && (
//           <div className="mt-4  p-4 shadow rounded">
//             <h2 className="text-xl font-semibold">Admin Details</h2>
//             <p><strong>Name:</strong> {user.name}</p>
//             <p><strong>Email:</strong> {user.email}</p>
//           </div>
//         )} */}

//         {/* Routes for different admin sections */}
//         <Routes>
//           <Route path="profile" element={<Profile />} />
//           <Route path="edit-users" element={<UserManagement />} />
//           <Route path="login-history" element={<LoginHistory />} />
//           <Route path="bulk-register" element={<BulkRegister />} />
//           <Route path="documents" element={<ViewDocuments userType="admin"/>} />
//           <Route path="messages" element={<Messages />} />
//         </Routes>
//       </div>
//     </div>
//   );
// };

// export default AdminDashboard;

import { useEffect, useState } from "react";
import { useNavigate, Link, Routes, Route } from "react-router-dom";
import api from "../services/api";
import UserManagement from "./UserManagement";
import LoginHistory from "./LoginHistory";
import AddUser from "./AddUser";
import BulkRegister from "./BulkRegister";
import Messages from "./Messages";
import Profile from "./Profile";
import ViewDocuments from "./ViewDoucments";
import PropTypes from "prop-types";

const AdminDashboard = ({ refreshToken, setRefreshToken }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    } else {
      api.get("/api/current-user")
        .then((response) => {
          if (response.data?.role === "superAdmin" || response.data?.role === "Admin" ) {
            localStorage.setItem("user", JSON.stringify(response.data));
            setUser(response.data);
          } else {
            navigate("/login");
          }
        })
        .catch(() => navigate("/login"));
    }
  }, [navigate]);

  const handleLogout = async () => {
    try {
      if (!refreshToken) {
        console.warn(" No refresh token found, skipping logout API call.");
      } else {
        await api.post("/logout", { refreshToken }); //  Send refresh token in body
      }

      setRefreshToken(null); //  Clear refresh token from memory
      localStorage.removeItem("user");
      localStorage.removeItem("refreshToken");
      navigate("/login");
    } catch (error) {
      console.error(" Logout failed:", error);
    }
  };

  return (
    <div className="min-h-screen flex">
      {/* Sidebar */}
      <div className="w-64 bg-blue-700 text-white p-4">
        <h2 className="text-2xl font-bold">Admin Dashboard</h2>
        <ul className="mt-4 space-y-2">
          <li><Link to="/admin/profile" className="block p-2 hover:bg-blue-800 rounded">View Profile</Link></li>
          <li><Link to="/admin/edit-users" className="block p-2 hover:bg-blue-800 rounded">View All Users</Link></li>
          <li><Link to="/admin/login-history" className="block p-2 hover:bg-blue-800 rounded">Login History</Link></li>
          <li><Link to="/admin/add-user" className="block p-2 hover:bg-blue-800 rounded">Register User</Link></li>
          <li><Link to="/admin/bulk-register" className="block p-2 hover:bg-blue-800 rounded">Bulk Register</Link></li>
          <li><Link to="/admin/documents" className="block p-2 hover:bg-blue-800 rounded">View Documents</Link></li>
          <li><Link to="/admin/messages" className="block p-2 hover:bg-blue-800 rounded">Messages</Link></li>
          <li><button onClick={handleLogout} className="block w-full p-2 text-left hover:bg-red-600 rounded">Logout</button></li>
        </ul>
      </div>

      {/* Main Content */}
      <div className="flex-1 p-6">
        <h1 className="text-3xl font-bold">
          {user ? `Welcome ${user.name}!` : "Loading..."}
        </h1>

        {/* Routes for different admin sections */}
        <Routes>
          <Route path="profile" element={<Profile />} />
          <Route path="edit-users" element={<UserManagement />} />
          <Route path="login-history" element={<LoginHistory />} />
          <Route path="add-user" element={<AddUser />} />
          <Route path="bulk-register" element={<BulkRegister />} />
          <Route path="documents" element={<ViewDocuments userType="admin" />} />
          <Route path="messages" element={<Messages />} />
        </Routes>
      </div>
    </div>
  );
};

AdminDashboard.propTypes = {
    refreshToken: PropTypes.string,  
    setRefreshToken: PropTypes.func.isRequired, 
  };

export default AdminDashboard;

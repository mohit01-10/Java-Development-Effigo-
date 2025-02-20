import { useEffect, useState } from "react";
import { useNavigate, Link, Routes, Route } from "react-router-dom";
import api from "../services/api";
import Profile from "./Profile";
import EditProfile from "./EditProfile";
import Messages from "./Messages";
//import Notifications from "./Notifications";
import ViewDocuments from "./ViewDoucments";
import PropTypes from "prop-types";

const Dashboard = ({ refreshToken, setRefreshToken }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    } else {
      api.get("/api/current-user")
        .then((response) => {
          if (response.data?.role) {
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
    <div className="min-h-screen  flex">
      {/* Sidebar */}
      <div className="w-64 bg-blue-700 text-white p-4">
        <h2 className="text-2xl font-bold">Dashboard</h2>
        <ul className="mt-4 space-y-2">
        <li><Link to="/dashboard" className="block p-2 hover:bg-blue-800 rounded">Home</Link></li>
        <li><Link to="/dashboard/profile" className="block p-2 hover:bg-blue-800 rounded">View Profile</Link></li>
        <li><Link to="/dashboard/edit-profile" className="block p-2 hover:bg-blue-800 rounded">Edit Profile</Link></li>
        <li><Link to="/dashboard/documents" className="block p-2 hover:bg-blue-800 rounded">View Documents</Link></li>
        <li><Link to="/dashboard/messages" className="block p-2 hover:bg-blue-800 rounded">Messages</Link></li>
        {/* <li><Link to="/dashboard/notifications" className="block p-2 hover:bg-blue-800 rounded">Notifications</Link></li> */}
        <li><button onClick={handleLogout} className="block w-full p-2 text-left hover:bg-red-600 rounded">Logout</button></li>
        </ul>

      </div>

      {/* Main Content */}
      <div className="flex-1 p-6">
      <h1 className="text-3xl font-bold">
          {user ? `Welcome ${user.name}!` : "Loading..."}
        </h1>

        {/* Nested Routes Fix */}
        <Routes>
            <Route path="profile" element={<Profile />} />
            <Route path="edit-profile" element={<EditProfile />} />
            <Route path="documents" element={<ViewDocuments userType="user"/>} />
            <Route path="messages" element={<Messages />} />
            {/* <Route path="notifications" element={<Notifications />} /> */}
        </Routes>

      </div>
    </div>
  );
};


Dashboard.propTypes = {
    refreshToken: PropTypes.string,  // ✅ refreshToken must be a string
    setRefreshToken: PropTypes.func.isRequired,  // ✅ setRefreshToken must be a function
  };

export default Dashboard;

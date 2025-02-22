import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import { useState, useEffect } from "react";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import AdminDashboard from "./pages/AdminDashboard";
import Home from "./pages/Home";

function App() {
  const [refreshToken, setRefreshToken] = useState(localStorage.getItem("refreshToken"));
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      setUser(JSON.parse(storedUser));
    }
  }, []);

  return (
    <Router>
      <Routes>
      <Route path="/" element={<Home />} />
        <Route path="/home" element={<Home />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login setRefreshToken={(token) => {
          setRefreshToken(token);
          localStorage.setItem("refreshToken", token);  // ✅ Keep it in sync
        }} />} />

        {/* Role-based redirection */}
        <Route
          path="/dashboard/*"
          element={ <Dashboard refreshToken={refreshToken} setRefreshToken={setRefreshToken}/>}
        />
        <Route
          path="/admin/*"
          element={ <AdminDashboard refreshToken={refreshToken} setRefreshToken={setRefreshToken}/>}
        />

        {/* Default redirect if no route matches */}
        <Route path="*" element={<Navigate to={user?.role === "Admin" ? "/admin" : "/dashboard"} />} />
      </Routes>
    </Router>
  );
}

export default App;

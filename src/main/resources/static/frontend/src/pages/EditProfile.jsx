import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";
import { logoutUser } from '../services/util';

const EditProfile = () => {
    const [refreshToken, setRefreshToken] = useState(localStorage.getItem("refreshToken"));
  const [formData, setFormData] = useState({ name: "", phone: "" });
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

const handleSubmit = async (e) => {
    e.preventDefault();
    try {
        const response = await api.put("/user/profile", formData, {
            headers: { "Content-Type": "application/json" },
            withCredentials: true,
        });

        const updatedUser = response.data;
        localStorage.setItem("user", JSON.stringify(updatedUser));
        
        alert("Profile updated successfully!");
        navigate("/dashboard/profile");

    } catch (error) {
        console.error(" Error updating profile:", error.response?.data || error.message);

        if (error.response?.status === 403) {
            const errorMessage = error.response?.data?.error || "";

            //  If user is INACTIVE, log them out
            if (errorMessage.includes("User is INACTIVE")) {
                alert("Your account has been deactivated. You will be logged out.");
                logoutUser(refreshToken, setRefreshToken);
                navigate("/login")
                return;
            }
        }

        alert(" Error updating profile: " + (error.response?.data?.error || error.message));
    }
};

  return (
    <div className="p-4 rounded shadow">
      <h2 className="text-2xl font-bold ml-2 text-center mb-6">Edit Profile</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input type="text" name="name" placeholder="Name" className="w-full p-2 border" onChange={handleChange} required />
        <input type="text" name="phone" placeholder="Phone" className="w-full p-2 border" onChange={handleChange} required />
        <button type="submit" className="bg-blue-600 text-white p-2 rounded">Save Changes</button>
      </form>
    </div>
  );
};

export default EditProfile;

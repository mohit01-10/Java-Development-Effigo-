
import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import crypto from "crypto";
import HomeHeader from "../components/HomeHeader";

// eslint-disable-next-line react/prop-types
const Login = ({ setRefreshToken }) => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ email: "", password: "" });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // ✅ Hash password before sending
  const hashPassword = (password) => {
    return crypto.createHash("sha256").update(password).digest("hex");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const hashedPassword = hashPassword(formData.password); // SHA-256 Hash

    try {
      const response = await axios.post(
        "http://localhost:8080/login",
        { email: formData.email, password: hashedPassword },
        { withCredentials: true }
      );

      if (response.data?.role && response.data?.refreshToken) {
        setRefreshToken(response.data.refreshToken);
        localStorage.setItem("refreshToken", response.data.refreshToken); // ✅ Store refresh token in memory
        navigate(response.data.role === "User" ? "/dashboard" : "/admin");
      } else {
        alert("Invalid login response");
      }
    } catch (error) {
      alert(error.response?.data || "Login failed");
    }
  };
  return (
    <>
      <HomeHeader/>
      <div className="flex justify-center items-center min-h-screen ">
        <div className="bg-white p-6 rounded-lg shadow-lg w-96">
          <h2 className="text-2xl text-black font-bold mb-4 text-center">Welcome Back!</h2><br></br>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
                <div className="flex">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" className="size-5">
                        <path d="M1.5 8.67v8.58a3 3 0 0 0 3 3h15a3 3 0 0 0 3-3V8.67l-8.928 5.493a3 3 0 0 1-3.144 0L1.5 8.67Z" />
                        <path d="M22.5 6.908V6.75a3 3 0 0 0-3-3h-15a3 3 0 0 0-3 3v.158l9.714 5.978a1.5 1.5 0 0 0 1.572 0L22.5 6.908Z" />
                    </svg>

                    <label className="block text-sm ml-2 font-medium text-gray-700">Email</label>
                </div>
              <input type="email" name="email" placeholder="e.g. example@gmail.com"
                className="w-full p-2 border border-gray-300 rounded mt-1" onChange={handleChange} required />
            </div>

            <div>
                <div className="flex">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" className="size-5">
                        <path fillRule="evenodd" d="M15.75 1.5a6.75 6.75 0 0 0-6.651 7.906c.067.39-.032.717-.221.906l-6.5 6.499a3 3 0 0 0-.878 2.121v2.818c0 .414.336.75.75.75H6a.75.75 0 0 0 .75-.75v-1.5h1.5A.75.75 0 0 0 9 19.5V18h1.5a.75.75 0 0 0 .53-.22l2.658-2.658c.19-.189.517-.288.906-.22A6.75 6.75 0 1 0 15.75 1.5Zm0 3a.75.75 0 0 0 0 1.5A2.25 2.25 0 0 1 18 8.25a.75.75 0 0 0 1.5 0 3.75 3.75 0 0 0-3.75-3.75Z" clipRule="evenodd" />
                    </svg>

                    <label className="block text-sm ml-2 font-medium  text-gray-700">Password</label>
                </div>
              
              <input type="password" name="password" placeholder="Password"
                className="w-full p-2 border border-gray-300 rounded mt-1" onChange={handleChange} required />
            </div>
            <button type="submit" className="w-full bg-blue-600 text-white p-2 rounded hover:bg-blue-700">
              Login
            </button>
          </form>
        </div>
      </div>
    </>
  );
};

export default Login;

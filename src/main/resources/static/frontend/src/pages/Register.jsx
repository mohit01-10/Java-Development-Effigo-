// import { useState } from "react";
// import { useNavigate, Link } from "react-router-dom";
// import axios from "axios";
// import HomeHeader from "../components/HomeHeader";
// import crypto from "crypto-browserify"; // Ensure crypto package is installed

// const Register = () => {
//   const navigate = useNavigate();
//   const [formData, setFormData] = useState({
//     name: "",
//     email: "",
//     phone: "",
//     password: "",
//     roleId: 2,
//   });

//   const handleChange = (e) => {
//     setFormData({ ...formData, [e.target.name]: e.target.value });
//   };

//   const hashPassword = (password) => {
//     return crypto.createHash("sha256").update(password).digest("hex");
//   };

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     const hashedPassword = hashPassword(formData.password); // SHA-256 Hash
//     try {
//       await axios.post("http://localhost:8080/register", 
//         { ...formData, password: hashedPassword }
//       );
//       alert("Registration successful!");
//       navigate("/login");
//     } catch (error) {
//       alert("Error: " + (error.response?.data || "Registration failed"));
//     }
//   };

//   return (
//     <>
//       <HomeHeader/>
//       <div className="flex justify-center items-center min-h-screen ">
//         <div className="bg-white p-6 rounded-lg shadow-lg w-96">
//           <h2 className="text-2xl text-black font-bold mb-4 text-center">New Account</h2><br></br>
//           <form onSubmit={handleSubmit} className="space-y-4" autoComplete="off">

//                 {/* Hidden fake input to trick autofill */}
//                 <input type="hidden" name="fake-user" value="" />

//             <div>
//                 <div className="flex">
//                     <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" className="size-5">
//                          <path fillRule="evenodd" d="M7.5 6a4.5 4.5 0 1 1 9 0 4.5 4.5 0 0 1-9 0ZM3.751 20.105a8.25 8.25 0 0 1 16.498 0 .75.75 0 0 1-.437.695A18.683 18.683 0 0 1 12 22.5c-2.786 0-5.433-.608-7.812-1.7a.75.75 0 0 1-.437-.695Z" clipRule="evenodd" />
//                     </svg>

//                     <label className="block text-sm ml-2 font-medium text-gray-700">Name</label>
//                 </div>
//               <input type="text" className="w-full p-2 border border-gray-300 rounded mt-1"
//                 name="name" placeholder="Full Name" onChange={handleChange} required />
//             </div>

//             <div>
//                 <div className="flex">
//                     <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" className="size-5">
//                         <path d="M1.5 8.67v8.58a3 3 0 0 0 3 3h15a3 3 0 0 0 3-3V8.67l-8.928 5.493a3 3 0 0 1-3.144 0L1.5 8.67Z" />
//                         <path d="M22.5 6.908V6.75a3 3 0 0 0-3-3h-15a3 3 0 0 0-3 3v.158l9.714 5.978a1.5 1.5 0 0 0 1.572 0L22.5 6.908Z" />
//                     </svg>

//                     <label className="block text-sm ml-2 font-medium text-gray-700">Email</label>
//                 </div>
//               <input type="email" className="w-full p-2 border border-gray-300 rounded mt-1"
//                 name="email" placeholder="e.g. example@gmail.com" onChange={handleChange} required autoComplete="off" />
//             </div>

//             <div>
//                 <div className="flex">
//                     <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" className="size-6">
//                         <path d="M10.5 18.75a.75.75 0 0 0 0 1.5h3a.75.75 0 0 0 0-1.5h-3Z" /> <path fillRule="evenodd" d="M8.625.75A3.375 3.375 0 0 0 5.25 4.125v15.75a3.375 3.375 0 0 0 3.375 3.375h6.75a3.375 3.375 0 0 0 3.375-3.375V4.125A3.375 3.375 0 0 0 15.375.75h-6.75ZM7.5 4.125C7.5 3.504 8.004 3 8.625 3H9.75v.375c0 .621.504 1.125 1.125 1.125h2.25c.621 0 1.125-.504 1.125-1.125V3h1.125c.621 0 1.125.504 1.125 1.125v15.75c0 .621-.504 1.125-1.125 1.125h-6.75A1.125 1.125 0 0 1 7.5 19.875V4.125Z" clipRule="evenodd" />
//                     </svg>

//                     <label className="block text-sm ml-2 font-medium text-gray-700">Phone Number</label>
//                 </div>
//               <input type="text" className="w-full p-2 border border-gray-300 rounded mt-1"
//                 name="phone" placeholder="e.g. 91XXXXXX91" onChange={handleChange} required autoComplete="new-email" />
//             </div>

//             <div>
//                 <div className="flex">
//                     <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="black" className="size-5">
//                         <path fillRule="evenodd" d="M15.75 1.5a6.75 6.75 0 0 0-6.651 7.906c.067.39-.032.717-.221.906l-6.5 6.499a3 3 0 0 0-.878 2.121v2.818c0 .414.336.75.75.75H6a.75.75 0 0 0 .75-.75v-1.5h1.5A.75.75 0 0 0 9 19.5V18h1.5a.75.75 0 0 0 .53-.22l2.658-2.658c.19-.189.517-.288.906-.22A6.75 6.75 0 1 0 15.75 1.5Zm0 3a.75.75 0 0 0 0 1.5A2.25 2.25 0 0 1 18 8.25a.75.75 0 0 0 1.5 0 3.75 3.75 0 0 0-3.75-3.75Z" clipRule="evenodd" />
//                     </svg>

//                     <label className="block text-sm ml-2 font-medium  text-gray-700">Password</label>
//                 </div>
//                <input type="password" className="w-full p-2 border border-gray-300 rounded mt-1"
//                 name="password" placeholder="Password" onChange={handleChange} required autoComplete="new-password"/>
//             </div>

//             <button type="submit" className="w-full bg-blue-600 text-white p-2 rounded hover:bg-blue-700">
//               Register
//             </button>
//           </form>
//           <p className="text-sm text-center text-gray-600 mt-4">
//             Already have an account?{" "}
//             <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
//           </p>
//         </div>
//       </div>
//     </>
//   );
// };

// export default Register;

import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import HomeHeader from "../components/HomeHeader";
import crypto from "crypto-browserify"; // Ensure crypto package is installed

const Register = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
    roleId: 2,
  });

  const [errors, setErrors] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
  });

  const validateForm = () => {
    let newErrors = { name: "", email: "", phone: "", password: "" };
    let isValid = true;

    //  Name Validation (Only Letters, Min 3 Characters)
    if (!/^[a-zA-Z\s]{3,}$/.test(formData.name)) {
      newErrors.name = "Name must be at least 3 characters and only letters.";
      isValid = false;
    }
    //  Email Validation
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(formData.email)) {
      newErrors.email = "Enter a valid email address.";
      isValid = false;
    }

    //  Phone Number Validation (10 digits)
    if (!/^\d{10}$/.test(formData.phone)) {
      newErrors.phone = "Phone number must be 10 digits.";
      isValid = false;
    }

    //  Password Validation (Min 6 characters, at least one number & special character)
    if (!/^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,}$/.test(formData.password)) {
      newErrors.password = "Password must be at least 6 characters, with a number & special character.";
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });

    // Live validation on input change
    setErrors({ ...errors, [e.target.name]: "" });
  };

  const hashPassword = (password) => {
    return crypto.createHash("sha256").update(password).digest("hex");
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return; // Stop if validation fails

    const hashedPassword = hashPassword(formData.password); // SHA-256 Hash
    try {
      await axios.post("http://localhost:8080/register", { ...formData, password: hashedPassword });
      alert("Registration successful!");
      navigate("/login");
    } catch (error) {
      alert("Error: " + (error.response?.data || "Registration failed"));
    }
  };

  return (
    <>
      <HomeHeader />
      <div className="flex justify-center items-center min-h-screen">
        <div className="bg-white p-6 rounded-lg shadow-lg w-96">
          <h2 className="text-2xl text-black font-bold mb-4 text-center">New Account</h2>
          <br />

          <form onSubmit={handleSubmit} className="space-y-4" autoComplete="off">
            {/* Hidden fake input to trick autofill */}
            <input type="hidden" name="fake-user" value="" />

            {/*  Name Field */}
            <div>
              <label className="block text-sm font-medium text-gray-700">Name</label>
              <input
                type="text"
                className="w-full p-2 border border-gray-300 rounded mt-1"
                name="name"
                placeholder="Full Name"
                onChange={handleChange}
                required
              />
              {errors.name && <p className="text-red-500 text-xs">{errors.name}</p>}
            </div>

            {/*  Email Field */}
            <div>
              <label className="block text-sm font-medium text-gray-700">Email</label>
              <input
                type="email"
                className="w-full p-2 border border-gray-300 rounded mt-1"
                name="email"
                placeholder="e.g. example@gmail.com"
                onChange={handleChange}
                required
                autoComplete="off"
              />
              {errors.email && <p className="text-red-500 text-xs">{errors.email}</p>}
            </div>

            {/*  Phone Field */}
            <div>
              <label className="block text-sm font-medium text-gray-700">Phone Number</label>
              <input
                type="text"
                className="w-full p-2 border border-gray-300 rounded mt-1"
                name="phone"
                placeholder="e.g. 91XXXXXX91"
                onChange={handleChange}
                required
                autoComplete="new-email"
              />
              {errors.phone && <p className="text-red-500 text-xs">{errors.phone}</p>}
            </div>

            {/*  Password Field */}
            <div>
              <label className="block text-sm font-medium text-gray-700">Password</label>
              <input
                type="password"
                className="w-full p-2 border border-gray-300 rounded mt-1"
                name="password"
                placeholder="Password"
                onChange={handleChange}
                required
                autoComplete="new-password"
              />
              {errors.password && <p className="text-red-500 text-xs">{errors.password}</p>}
            </div>

            {/*  Submit Button (Disabled if errors exist) */}
            <button
              type="submit"
              className={`w-full p-2 rounded ${Object.values(errors).some((e) => e) ? "bg-gray-400 cursor-not-allowed" : "bg-blue-600 text-white hover:bg-blue-700"}`}
              disabled={Object.values(errors).some((e) => e)}
            >
              Register
            </button>
          </form>

          <p className="text-sm text-center text-gray-600 mt-4">
            Already have an account?{" "}
            <Link to="/login" className="text-blue-600 hover:underline">Login</Link>
          </p>
        </div>
      </div>
    </>
  );
};

export default Register;

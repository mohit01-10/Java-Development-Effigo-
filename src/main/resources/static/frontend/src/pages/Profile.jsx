// import { useEffect, useState } from "react";
// import api from "../services/api";
// import { useNavigate } from "react-router-dom";

// const Profile = () => {
//   const [user, setUser] = useState(null);
//   const navigate = useNavigate();

//   useEffect(() => {
//     const fetchProfile = async () => {
//       try {
//         const response = await api.get("/api/current-user", { withCredentials: true });
//         setUser(response.data);
//       } catch (error) {
//         console.error("Error fetching profile:", error);
//         if (error.response?.status === 403 || error.response?.status === 401) {
//           localStorage.removeItem("user");
//           navigate("/login");
//         }
//       }
//     };

//     fetchProfile();
//   }, [navigate]);

//   return (
//     <div><br></br>
//       <h1 className="text-xl font-bold">Your Profile</h1>
//       {user ? (
//         <div className="mt-4 p-4 shadow rounded">
//           <p><strong>Name:</strong> {user.name}</p>
//           <p><strong>Email:</strong> {user.email}</p>
//           <p><strong>Phone:</strong> {user.phone}</p>
//           <p><strong>Role:</strong> {user.role}</p>
//           <p><strong>Status:</strong> {user.status}</p>
//         </div>
//       ) : (
//         <p>Loading...</p>
//       )}
//     </div>
//   );
// };

// export default Profile;

import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

const Profile = () => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProfile = async () => {
        
    const storedUser = localStorage.getItem("user");
      
        if (storedUser) {
          try {
            const user = JSON.parse(storedUser);
            setUser(user)
          } catch (error) {
            console.error("Error parsing user data from localStorage", error);
            navigate("/login");
          }
        }

    else{
      try {
        const response = await api.get("/api/current-user", { withCredentials: true });
        setUser(response.data);
      } catch (error) {
        console.error("Error fetching profile:", error);
        if (error.response?.status === 403 || error.response?.status === 401) {
          localStorage.removeItem("user");
          navigate("/login");
        }
      }
    }
};

    fetchProfile();
  }, [navigate]);

  return (
    
    <div className="container mt-5  ">
      <div className="flex justify-center items-center">
       
            <div className="bg-white mt-6 shadow-md rounded-lg p-6 w-full max-w-md">
                <h1 className="text-2xl font-bold text-center text-gray-800">Your Profile</h1>

                {user ? (
                <div className="mt-6 space-y-4">
                    <div className="flex justify-between border-b pb-2">
                    <span className="text-gray-600 font-semibold">Name:</span>
                    <span className="text-gray-800">{user.name}</span>
                    </div>

                    <div className="flex justify-between border-b pb-2">
                    <span className="text-gray-600 font-semibold">Email:</span>
                    <span className="text-gray-800">{user.email}</span>
                    </div>

                    <div className="flex justify-between border-b pb-2">
                    <span className="text-gray-600 font-semibold">Phone:</span>
                    <span className="text-gray-800">{user.phone || "N/A"}</span>
                    </div>

                    <div className="flex justify-between border-b pb-2">
                    <span className="text-gray-600 font-semibold">Role:</span>
                    <span className="text-gray-800 capitalize">{user.role}</span>
                    </div>

                    <div className="flex justify-between">
                    <span className="text-gray-600 font-semibold">Status:</span>
                    <span className={`px-3 py-1 rounded text-white text-sm ${user.status === "ACTIVE" ? "bg-green-500" : "bg-red-500"}`}>
                        {user.status}
                    </span>
                    </div>
                </div>
                ) : (
                <p className="text-center text-gray-500 mt-4">Loading...</p>
                )}
            </div>
    
      </div>
    </div>

  );
};

export default Profile;

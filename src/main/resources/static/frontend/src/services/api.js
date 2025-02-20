// src/service/api.js
// import axios from "axios";

// const api = axios.create({
//   baseURL: "http://localhost:8080",
//   withCredentials: true, // Include JWT cookies in requests
// });

// export default api;


// api.interceptors.request.use((config) => {
//   const token = localStorage.getItem("token");
//   if (token) {
//     config.headers.Authorization = `Bearer ${token}`;
//   }
//   return config;
// });

// import axios from "axios";

// const api = axios.create({
//   baseURL: "http://localhost:8080",
//   withCredentials: true, // ✅ Ensure cookies are included
// });


// // ✅ Automatically refresh token when access token expires
// api.interceptors.response.use(
//   (response) => response, // ✅ If response is OK, return it
//   async (error) => {
//     const originalRequest = error.config;

//     if (error.response && error.response.status === 401 && !originalRequest._retry) {
//       originalRequest._retry = true; // ✅ Prevent infinite loops

//       try {
//         console.log("🔄 Access token expired. Refreshing...");

//         // ✅ Call the refresh token API
//         const refreshResponse = await axios.post(
//           "http://localhost:8080/refresh-token",
//           {},
//           { withCredentials: true }
//         );

//         if (refreshResponse.status === 200) {
//           console.log("✅ Access token refreshed successfully!");
//           return api(originalRequest); // ✅ Retry the original request
//         }
//       } catch (refreshError) {
//         console.error("⛔ Refresh token expired. Redirecting to login." + refreshError);
//         window.location.href = "/login"; // Redirect to login page
//       }
//     }

//     return Promise.reject(error);
//   }
// );
// export default api;
// // 
import axios from "axios";

const api = axios.create({
    baseURL: "http://localhost:8080",
    withCredentials: true,
  });
  
  api.interceptors.response.use(
    (response) => response,
    async (error) => {
      const originalRequest = error.config;
  
      if (error.response && error.response.status === 403 && !originalRequest._retry) {
        originalRequest._retry = true;
  
        try {
          console.log("🔄 Access token expired. Refreshing...");
  
          const refreshToken = localStorage.getItem("refreshToken");  // Read from localStorage
  
          if (!refreshToken) {
            console.warn(" No refresh token found. Redirecting to login.");
            window.location.href = "/login";
            return Promise.reject(error);
          }
  
          const refreshResponse = await axios.post(
            "http://localhost:8080/refresh-token",
            { refreshToken },
            { withCredentials: true }
          );
  
          if (refreshResponse.status === 200) {
            console.log(" Access token refreshed successfully!");
            return api(originalRequest);
          }
        } catch (refreshError) {
          console.error(" Refresh token expired. Redirecting to login.", refreshError);
          localStorage.removeItem("user");
          localStorage.removeItem("refreshToken");  //  Clear expired refresh token
          window.location.href = "/login";
        }
      }
  
      return Promise.reject(error);
    }
  );
  
  export default api;
  
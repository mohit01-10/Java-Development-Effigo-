import api from "./api";

export const logoutUser = async (refreshToken, setRefreshToken) => {
    
    try {
      if (!refreshToken) {
        console.warn(" No refresh token found, skipping logout API call.");
      } else {
        await api.post("/logout", { refreshToken }); //  Send refresh token in body
      }

      setRefreshToken(null); //  Clear refresh token from memory
      localStorage.removeItem("user");
      localStorage.removeItem("refreshToken");
    } catch (error) {
      console.error(" Logout failed:", error);
    }
  };

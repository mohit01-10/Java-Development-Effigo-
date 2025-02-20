import api from "./api";

export const getAdmins = async () => {
    try {
      const response = await api.get("/messages/getadmins");
      return response.data;
    } catch (error) {
      console.error("Error fetching users:", error.response || error);
      throw error;
    }
  };

  export const getUsers = async () => {
    try {
      const response = await api.get("/admin/users");
      return response.data;
    } catch (error) {
      console.error("Error fetching users:", error.response || error);
      throw error;
    }
  };
import api from "./api";

// Get All Users
export const getAllUsers = async () => {
  try {
    const response = await api.get("/admin/users", { withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("Error fetching users:", error.response || error);
    throw error;
  }
};

// Update User Details
export const updateUser = async (userId, userData) => {
  try {
    const response = await api.put(`/admin/users/${userId}`, userData, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" }
    });
    return response.data;
  } catch (error) {
    console.error("Error updating user:", error.response || error);
    throw error;
  }
};

//  Delete a User
export const deleteUser = async (userId) => {
  try {
    const response = await api.delete(`/admin/users/${userId}`, { withCredentials: true });
    return response.data;
  } catch (error) {
    console.error("Error deleting user:", error.response || error);
    throw error;
  }
};

// Upload Document for a User
export const uploadDocument = async (userId, formData) => {
  try {
    const response = await api.post(`/admin/upload-document/${userId}`, formData, {
      withCredentials: true,
      headers: { "Content-Type": "multipart/form-data" }
    });
    return response.data;
  } catch (error) {
    console.error("Error uploading document:", error.response || error);
    throw error;
  }
};

//  Get Login History
// export const getLoginHistory = async () => {
//   try {
//     const response = await api.get("/admin/logging", { withCredentials: true });
//     return response.data;
//   } catch (error) {
//     console.error("Error fetching login history:", error.response || error);
//     throw error;
//   }
// };

export const getLoginHistory = async (page = 0, size = 10) => {
    try {
      const response = await api.get(`/admin/logging?page=${page}&size=${size}`, { withCredentials: true });
      return response.data;
    } catch (error) {
      console.error("Error fetching login history:", error.response || error);
      throw error;
    }
  };


  
//  Bulk Register Users via Excel
export const bulkRegisterUsers = async (formData) => {
  try {
    const response = await api.post("/admin/bulk-register", formData, {
      withCredentials: true,
      headers: { "Content-Type": "multipart/form-data" }
    });
    return response.data;
  } catch (error) {
    console.error("Error bulk registering users:", error.response || error);
    throw error;
  }
};

//  Toggle User Status (ACTIVE ↔ INACTIVE, PENDING → ACTIVE)
export const toggleUserStatus = async (userId) => {
    try {
      const response = await api.put(`/admin/users/${userId}/status`, {}, { withCredentials: true });
      return response.data;
    } catch (error) {
      console.error("Error updating user status:", error.response || error);
      throw error;
    }
  };

//  Add User
export const addUser = async (userData) => {
  try {
    const response = await api.post("/admin/add-user", userData, {
      withCredentials: true,
      headers: { "Content-Type": "application/json" },
    });
    return response.data;
  } catch (error) {
    console.error("Error registering user:", error.response || error);
    throw error;
  }
};

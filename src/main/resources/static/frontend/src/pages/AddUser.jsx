import { useState } from "react";
import { addUser } from "../services/AdminApi";


const AddUser = () => {
  
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
    roleId: "2", // Default to User
  });

  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setErrorMessage("");
    setSuccessMessage("");

    try {
      const response = await addUser(formData);
      setSuccessMessage(response.message);
      setFormData({ name: "", email: "", phone: "", password: "", roleId: "2" }); // Reset form after success
    } catch (error) {
      setErrorMessage(error.response?.data?.error || "Failed to register user");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 max-w-lg mx-auto">
      <h2 className="text-2xl font-bold mb-4 text-center">Register User</h2>

      {errorMessage && <p className="text-red-500">{errorMessage}</p>}
      {successMessage && <p className="text-green-500">{successMessage}</p>}

      <form onSubmit={handleSubmit} className="space-y-4" autoComplete="off">

          {/* Hidden fake input to trick autofill */}
          <input type="hidden" name="fake-user" value="" />

        <div>
          <label className="block text-sm font-semibold">Name:</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} required autoComplete="off" className="border p-2 rounded w-full" />
        </div>

        <div>
          <label className="block text-sm font-semibold">Email:</label>
          <input type="email" name="email" value={formData.email} onChange={handleChange} required autoComplete="new-email" className="border p-2 rounded w-full" />
        </div>

        <div>
          <label className="block text-sm font-semibold">Phone:</label>
          <input type="text" name="phone" value={formData.phone} onChange={handleChange} required autoComplete="off" className="border p-2 rounded w-full" />
        </div>

        <div>
          <label className="block text-sm font-semibold">Password:</label>
          <input type="password" name="password" value={formData.password} onChange={handleChange} required autoComplete="new-password" className="border p-2 rounded w-full" />
        </div>

        <div>
          <label className="block text-sm font-semibold">Role:</label>
          <select name="roleId" value={formData.roleId} onChange={handleChange} required className="border p-2 rounded w-full">
            <option value="1">Admin</option>
            <option value="2">User</option>
          </select>
        </div>

        <button type="submit" className="px-4 py-2 bg-blue-600 text-white rounded w-full" disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>
      </form>
    </div>
  );
};

export default AddUser;

// src/components/Sidebar.jsx
import { Link } from "react-router-dom";

const Sidebar = () => {
  return (
    <aside className="bg-gray-900 text-white w-64 min-h-screen p-4">
      <h2 className="text-lg font-bold mb-4">EMS Dashboard</h2>
      <nav>
        <ul className="space-y-3">
          <li>
            <Link to="/dashboard" className="block p-2 hover:bg-gray-700 rounded">
              Dashboard
            </Link>
          </li>
          <li>
            <Link to="/users" className="block p-2 hover:bg-gray-700 rounded">
              Users
            </Link>
          </li>
          <li>
            <Link to="/profile" className="block p-2 hover:bg-gray-700 rounded">
              Profile
            </Link>
          </li>
          <li>
            <Link to="/settings" className="block p-2 hover:bg-gray-700 rounded">
              Settings
            </Link>
          </li>
          <li>
            <Link to="/logout" className="block p-2 hover:bg-red-600 rounded">
              Logout
            </Link>
          </li>
        </ul>
      </nav>
    </aside>
  );
};

export default Sidebar;

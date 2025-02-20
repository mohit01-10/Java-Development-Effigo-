import { Link } from "react-router-dom";
import logo from "../assets/effigo-logo.png"
const HomeHeader = () => {
  return (
    <header className="bg-blue-600 text-white p-4 flex justify-between items-center">
    
     <div className="flex gap-4" style={{cursor:'pointer'}} >
        <img src={logo} width={100} height={100} />
        <h1 className="text-xl font-bold">EMS</h1>
      </div>

      <nav>
        <ul className="flex gap-4">
          {/* <li>
            <Link to="/home" className="hover:underline">Home</Link>
          </li>
          <li>
            <Link to="/dashboard" className="hover:underline">Dashboard</Link>
          </li> */}
          {/* <li>
            <Link to="/profile" className="hover:underline">Profile</Link>
          </li> */}
              <li>
            <Link to="/register" className="hover:underline">Register</Link>
          </li>
          <li>
            <Link to="/login" className="hover:underline">Login</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default HomeHeader;
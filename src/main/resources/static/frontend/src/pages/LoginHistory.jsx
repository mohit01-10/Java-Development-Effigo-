import { useEffect, useState } from "react";
import { getLoginHistory } from "../services/AdminApi";

const LoginHistory = () => {
  const [loginHistory, setLoginHistory] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const entriesPerPage = 10;
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    const fetchLoginHistory = async () => {
      try {
        const response = await getLoginHistory(currentPage, entriesPerPage);
        setLoginHistory(response);
        setHasMore(response.length === entriesPerPage); // If response has less than 10 entries, no more pages exist
      } catch (error) {
        alert("Failed to load login history: " + error);
      }
    };

    fetchLoginHistory();
  }, [currentPage]); //  Only depends on currentPage

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold ml-2 text-center mb-6">Login History</h1>
      <table className="mt-4 w-full border text-center">
        <thead className="bg-gray-100 text-black">
          <tr>
            <th className="p-2">USER NAME</th>
            <th className="p-2">E-MAIL</th>
            <th className="p-2">LOGIN TIME</th>
          </tr>
        </thead>
        <tbody>
          {loginHistory.map((log, index) => (
            <tr key={index} className="border">
              <td className="p-2">{log.user_name}</td>
              <td className="p-2">{log.user_email}</td>
              <td className="p-2">{new Date(log.login_time).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {/* Pagination Controls */}
      <div className="flex justify-center items-center mt-4 space-x-4">
        <button 
          onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 0))}
          disabled={currentPage === 0}
          className={`px-3 py-1 rounded ${currentPage === 0 ? "bg-gray-300 cursor-not-allowed" : "bg-blue-500 text-white"}`}
        >
          {"<"}
        </button>

        <span className="text-lg font-semibold">{currentPage + 1}</span>

        <button 
          onClick={() => setCurrentPage((prev) => hasMore ? prev + 1 : prev)}
          disabled={!hasMore}
          className={`px-3 py-1 rounded ${!hasMore ? "bg-gray-300 cursor-not-allowed" : "bg-blue-500 text-white"}`}
        >
          {">"}
        </button>
      </div>
    </div>
  );
};

export default LoginHistory;

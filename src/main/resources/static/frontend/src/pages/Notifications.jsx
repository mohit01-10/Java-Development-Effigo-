import { useEffect, useState } from "react";
import api from "../services/api";

const Notifications = () => {
  const [notifications, setNotifications] = useState([]);

  useEffect(() => {
    api.get("/user/notifications")
      .then((response) => setNotifications(response.data))
      .catch((error) => console.error("Error fetching notifications:", error));
  }, []);

  return (
    <div className=" p-4 rounded shadow">
      <h2 className="text-xl font-bold">Notifications</h2>
      <ul>
        {notifications.map((notif, index) => (
          <li key={index} className="border-b p-2">{notif.message}</li>
        ))}
      </ul>
    </div>
  );
};

export default Notifications;

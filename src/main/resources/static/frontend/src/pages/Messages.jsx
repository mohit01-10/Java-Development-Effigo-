

import { useState, useEffect, navigate, useCallback } from "react";
import { getAdmins, getUsers } from "../services/MessageApi";
import api from "../services/api";

const Messages = () => {
  const [message, setMessage] = useState("");
  const [users, setUsers] = useState([]);
  const [admins, setAdmins] = useState([]);
  const [receiverId, setReceiverId] = useState("");
  const [receivedMessages, setReceivedMessages] = useState([]);
  const [sentMessages, setSentMessages] = useState([]);
  const [currentUserId, setCurrentUserId] = useState("");
  const [currentUserRole, setCurrentUserRole] = useState("");
  const [replyMessage, setReplyMessage] = useState("");
  const [replyReceiver, setReplyReceiver] = useState(null);
  const [showReplyPopup, setShowReplyPopup] = useState(false);
  
  // Collapsible state for Received and Sent Messages
  const [showReceived, setShowReceived] = useState(false);
  const [showSent, setShowSent] = useState(false);

  const fetchReceivedMessages = useCallback(async () => {
    if (!currentUserId) return;
    const response = await api.get(`/messages/received/${currentUserId}`);
    setReceivedMessages(response.data.reverse());
  }, [currentUserId]);

  const fetchSentMessages = useCallback(async () => {
    if (!currentUserId) return;
    const response = await api.get(`/messages/sent/${currentUserId}`);
    setSentMessages(response.data.reverse());
  }, [currentUserId]);

//   useEffect(() => {
//     fetchUserId();
//     fetchUsers();
//     fetchAdmins();
//   }, []); // Runs only once on mount
  
useEffect(() => {
    fetchUserId();
  }, []);

  useEffect(() => {
    if (currentUserRole) {
      if (currentUserRole === "User") {
        fetchAdmins(); // Fetch only admins for users
      } else {
        fetchUsers(); // Fetch only users for admins
      }
    }
  }, [currentUserRole]);

  useEffect(() => {
    if (currentUserId) {
      fetchReceivedMessages();
      fetchSentMessages();
    }
  }, [currentUserId, fetchReceivedMessages, fetchSentMessages]); 
  

  const fetchAdmins = async () => {
    try {
      const response = await getAdmins();
      setAdmins(response);
    } catch (error) {
      alert("Failed to load admins: " + error);
    }
  };

  const fetchUsers = async () => {
    try {
      const response = await getUsers();
      setUsers(response);
    } catch (error) {
      alert("Failed to load users: " + error);
    }
  };

const fetchUserId = () => {
    const storedUser = localStorage.getItem("user");
  
    if (storedUser) {
      try {
        const user = JSON.parse(storedUser);
        setCurrentUserId(user.id);
        setCurrentUserRole(user.role);
      } catch (error) {
        console.error("Error parsing user data from localStorage", error);
        navigate("/login");
      }
    } else {
      navigate("/login");
    }
  };
  
  const handleSendMessage = async () => {
    if (!message.trim() || !receiverId) {
      alert("Please write a message and select a recipient before sending.");
      return;
    }

    const messageData = { senderId: currentUserId, receiverId, messageBody: message };
    await api.post('/messages/send', messageData);
    alert("Message sent successfully!");
    setMessage("");
    fetchSentMessages();
  };

  const handleSendReply = async () => {
    if (!replyMessage.trim()) {
      alert("Please write a reply before sending.");
      return;
    }

    const messageData = { senderId: currentUserId, receiverId: replyReceiver.uid, messageBody: replyMessage };
    await api.post('/messages/send', messageData);
    alert(`Reply sent to ${replyReceiver.name}`);
    setReplyMessage("");
    setShowReplyPopup(false);
    fetchSentMessages();
  };

  const openReplyPopup = (sender) => {
    setReplyReceiver(sender);
    setShowReplyPopup(true);
    setReplyMessage(""); 
  };

  return (
    <div className="rounded shadow p-6">
      <h1 className="text-2xl font-bold ml-2 text-center mb-6">Messages</h1>
      <br />

      {/* General Message Sending Section */}
      <textarea
        className="w-full p-2 border rounded"
        rows="4"
        placeholder="Write a message..."
        value={message}
        onChange={(e) => setMessage(e.target.value)}
      />
      
      <select
        value={receiverId}
        onChange={(e) => setReceiverId(e.target.value)}
        className="w-full p-2 mt-2 border rounded"
      >
        <option value="">Select a receiver</option>
        {currentUserRole === "User"
          ? admins.map((admin) => (
              <option key={admin.uid} value={admin.uid}>
                {admin.name}
              </option>
            ))
          : users.map((user) => (
              <option key={user.uid} value={user.uid}>
                {user.name}
              </option>
            ))}
      </select>

      <button className="bg-blue-600 text-white p-2 rounded mt-2" onClick={handleSendMessage}>
        Send
      </button>

      {/* Received Messages Section */}
      <div className="mt-6">
        <h3 
          className="text-lg font-bold border-b p-2 flex justify-between items-center cursor-pointer"
          onClick={() => setShowReceived(!showReceived)}
        >
          Received Messages {showReceived ? "↑" : "↓"}
        </h3>

        {showReceived && (
          <ul className="space-y-2 mt-2">
            {receivedMessages.map((msg) => (
              <div key={msg.msgId} className="bg-white p-4 rounded shadow-sm flex flex-col justify-between">
                <div>
                  <p className="text-sm text-black"><b>At:</b> {new Date(msg.time).toLocaleString()} &nbsp; <b>From:</b> {msg.sender.name}</p>
                  <p className="text-black mt-2">{msg.messageBody}</p>
                </div>
                <button
                  className="mt-2 px-3 py-1 bg-blue-600 text-white rounded self-end"
                  onClick={() => openReplyPopup(msg.sender)}
                >
                  Reply
                </button>
              </div>
            ))}
          </ul>
        )}
      </div>

      {/* Sent Messages Section */}
      <div className="mt-6">
        <h3 
          className="text-lg font-bold border-b p-2 flex justify-between items-center cursor-pointer"
          onClick={() => setShowSent(!showSent)}
        >
          Sent Messages {showSent ? "↑" : "↓"}
        </h3>

        {showSent && (
          <ul className="space-y-2 mt-2">
            {sentMessages.map((msg) => (
              <li key={msg.msgId} className="p-4 bg-gray-50 rounded shadow-sm">
                <p className="text-sm text-black"><b>At:</b> {new Date(msg.time).toLocaleString()} &nbsp; <b>To:</b> {msg.receiver.name}</p>
                <p className="text-black mt-2">{msg.messageBody}</p>
              </li>
            ))}
          </ul>
        )}
      </div>

      {/* Reply Popup Layover */}
      {showReplyPopup && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-start pt-20">
          <div className="bg-white p-6 rounded shadow-lg w-96">
            <h2 className="text-lg text-black font-bold mb-2">Reply to {replyReceiver.name}</h2>
            <textarea
              className="w-full bg-white text-black  p-2 border rounded"
              rows="3"
              placeholder="Write your reply..."
              value={replyMessage}
              onChange={(e) => setReplyMessage(e.target.value)}
            />
            <div className="flex justify-end mt-2">
              <button className="bg-gray-500 text-white px-3 py-1 rounded mr-2" onClick={() => setShowReplyPopup(false)}>
                Cancel
              </button>
              <button className="bg-blue-600 text-white px-3 py-1 rounded" onClick={handleSendReply}>
                Send
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Messages;


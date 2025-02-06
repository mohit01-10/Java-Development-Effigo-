const backendURL = "http://localhost:8080/auth"; // Remove trailing slash

function login() {
    const name = document.getElementById("name").value;
    const password = document.getElementById("password").value;

    fetch(`${backendURL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, password })
    })
    .then(response => response.json())
    .then(data => {
        console.log("Login response:", data); // Debugging
        if (data.status === "success") {
            document.getElementById("centerdiv").style.display = "none";
            document.getElementById("chat-container").style.display = "block";
            localStorage.setItem("jwt_token", data.token); // Store token for future requests
        } else {
            alert("Login failed: " + data.error);
        }
    })
    .catch(error => alert("Error: " + error));
}

function register() {
    const name = document.getElementById("name").value;
    const password = document.getElementById("password").value;

    fetch(`${backendURL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ name, password }) // Using name instead of email
    })
    .then(response => response.json())
    .then(data => {
        console.log("Register response:", data); // Debugging
        if (data.status === "success") {
            alert("Registration successful! Please log in.");
        } else {
            alert("Registration failed: " + data.error);
        }
    })
    .catch(error => alert("Error: " + error));
}

function logout() {
    const token = localStorage.getItem("jwt_token");

    if (token) {
        fetch("http://localhost:8080/auth/logout", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        }).finally(() => {
            localStorage.removeItem("jwt_token");
            window.location.href = "http://127.0.0.1:5500/frontend/index.html"; // Correct URL
        });
    } else {
        window.location.href = "http://127.0.0.1:5500/frontend/index.html"; // Redirect even if no token
    }
}


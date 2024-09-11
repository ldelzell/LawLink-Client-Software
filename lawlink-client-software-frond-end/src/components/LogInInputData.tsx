import React, { useState } from "react";
import { Link } from "react-router-dom";
import '../styles/Components-CSS/LogInInputData.css'

interface LoginFormProps {
    onSubmit: (credentials: { username: string, password: string }) => void;
}

const LogInInputData: React.FC<LoginFormProps> = ({ onSubmit }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit({ username, password });
    };

    return (
        <div className="login-content">
            <form className="login-form" onSubmit={handleSubmit}>
                <input 
                    name="username"
                    type="text"
                    className="login-input"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    name="password"
                    type="password"
                    className="login-input"
                    placeholder="Password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button className="login-button" type="submit">Login</button>
            </form>
        </div>
    );
}

export default LogInInputData;

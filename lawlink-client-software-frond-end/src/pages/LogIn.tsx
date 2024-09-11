import React from 'react';
import '../styles/Pages-CSS/LogIn.css';
import LogInInputData from '../components/LogInInputData';
import loginApi from '../api/loginApi';
import { useNavigate } from 'react-router-dom';

const Login: React.FC = () => {
    const navigate = useNavigate();

    const handleLogin = async (credentials: { username: string, password: string }) => {
        try {
            const data = await loginApi.login(credentials);
            const { accessToken, refreshToken, userId, userRoles} = data;
            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('id', userId);
            localStorage.setItem('userRoles',userRoles);
            localStorage.setItem('refreshToken', refreshToken)

            console.log('Login successful:', data);
            
            navigate('/'); 
        } catch (error) {
            console.error('Error in Logging In', error);
        }
    };

   
    return (
        <div className="login-container">
        <div className="login-content">
            <h1>Welcome Back</h1>
            <div className="login-form-container">
                <LogInInputData onSubmit={handleLogin} />
            </div>
        </div>
    </div>
    );
};

export default Login;

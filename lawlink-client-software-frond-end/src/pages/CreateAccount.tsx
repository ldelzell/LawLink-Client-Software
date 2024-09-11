import React, { useState } from "react";
import InputDataCreateAccount from "../components/InputDataCreateAccount";
import '../styles/Pages-CSS/CreateAccount.css';
import accountApi from "../api/accountApi";
import NavBar from "../components/NavBar";
import attorneyApi from "../api/attorneyApi";

const CreateAccount: React.FC = () => {
    const [notification, setNotification] = useState<string | null>(null);

    const createAccount = async (
        firstName: string, 
        lastName: string, 
        email: string, 
        password: string, 
        role: string,  
        attorneyId?: number
    ): Promise<void> => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            console.log("Creating account with data:", { firstName, lastName, email, password, role, attorneyId });
            if (role === "CLIENT") {
                await accountApi.createClient({ firstName, lastName, email, password,  attorneyId, role }, config);
            } else if (role === "ATTORNEY") {
                await attorneyApi.createAttorney({ firstName, lastName, email, password, role }, config);
            } else if (role === "ADMIN") {
                await attorneyApi.createAttorney({ firstName, lastName, email, password, role }, config);
            }
            console.log("Account created successfully");
            setNotification("Account created successfully!");
        } catch (error) {
            console.error('Error creating account:', error);
            setNotification("Error creating account. Please try again.");
        }
    };

    return (
        <div className="container">
            <NavBar />
            <div className="inner">
                <h1 className="title">Create Account Page</h1>
                {notification && (
                    <div className="notification">
                        {notification}
                    </div>
                )}
                <InputDataCreateAccount onSubmit={createAccount} />
            </div>
        </div>
    );
}

export default CreateAccount;

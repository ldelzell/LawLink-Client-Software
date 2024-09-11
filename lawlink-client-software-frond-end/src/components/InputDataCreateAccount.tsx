import React, { useState } from "react";
import '../styles/Components-CSS/InputDataCreateAccount.css';

interface InputDataCreateAccountProps {
    onSubmit: (
        firstName: string,
        lastName: string,
        email: string,
        password: string,
        role: string,
        attorneyId?: number
    ) => void;
}

const InputDataCreateAccount: React.FC<InputDataCreateAccountProps> = ({ onSubmit }) => {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        attorneyId: "",
        role: "CLIENT"
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        const { firstName, lastName, email, password, role,  attorneyId } = formData;
        if (role === "CLIENT") {
            onSubmit(firstName, lastName, email, password, role, attorneyId ? parseInt(attorneyId, 10) : undefined);
        } else if (role === "ATTORNEY") {
            onSubmit(firstName, lastName, email, password, role);
        }
        console.log("inputData", firstName, lastName, email, password, role, attorneyId);
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    return (
        <form className="form-container" onSubmit={handleSubmit}>
            <select
                name="role"
                className="input-select"
                value={formData.role}
                onChange={handleChange}
            >
                <option value="CLIENT">Client</option>
                <option value="ATTORNEY">Attorney</option>
                <option value="ADMIN">Admin</option>
            </select>
            <input
                type="text"
                name="firstName"
                className="input-text"
                placeholder="First Name"
                value={formData.firstName}
                onChange={handleChange}
            />
            <input
                type="text"
                name="lastName"
                className="input-text"
                placeholder="Last Name"
                value={formData.lastName}
                onChange={handleChange}
            />
            <input
                type="email"
                name="email"
                className="input-text"
                placeholder="Email"
                value={formData.email}
                onChange={handleChange}
            />
            <input
                type="password"
                name="password"
                className="input-text"
                placeholder="Password"
                value={formData.password}
                onChange={handleChange}
            />
            {formData.role === "CLIENT" && (
                <>
                    {/* <input
                        type="text"
                        name="caseType"
                        className="input-text"
                        placeholder="Case Type"
                        value={formData.caseType}
                        onChange={handleChange}
                    /> */}
                    <input
                        type="number"
                        name="attorneyId"
                        className="input-text"
                        placeholder="Attorney ID"
                        value={formData.attorneyId}
                        onChange={handleChange}
                    />
                </>
            )}
            <button type="submit" className="input-submit">Submit</button>
        </form>
    );
}

export default InputDataCreateAccount;

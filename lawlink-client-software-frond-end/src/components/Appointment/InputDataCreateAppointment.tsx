import React, { useState } from "react";

interface InputDataCreateAppointmentProps {
    onSubmit: (name: string, reason: string, attorney: number, dateTime: Date | null) => void;
}

const InputDataCreateAppointment: React.FC<InputDataCreateAppointmentProps> = ({ onSubmit }) => {
    const [formData, setFormData] = useState({
        name: "",
        reason: "",
        attorney: -1,
        dateTime: null as Date | null
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(formData.name, formData.reason, formData.attorney, formData.dateTime);
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: name === "attorney" ? parseInt(value) : value // Convert attorney to number
        }));
    };

    return (
        <form className="form-container" onSubmit={handleSubmit}>
            <input
                type="text"
                name="name"
                className="input-text"
                placeholder="Name"
                value={formData.name}
                onChange={handleChange}
            />
            <input
                type="text"
                name="reason"
                className="input-text"
                placeholder="Reason"
                value={formData.reason}
                onChange={handleChange}
            />
            <select
                name="attorney"
                className="input-select"
                value={formData.attorney}
                onChange={handleChange}
            >
                <option value={-1}>Select Attorney</option>
                <option value={1}>Attorney 1</option>
                <option value={2}>Attorney 2</option>
            </select>
            <input
                type="datetime-local"
                name="dateTime"
                className="input-text"
                placeholder="Date & Time"
                value={formData.dateTime ? formData.dateTime.toISOString().slice(0, 16) : ''}
                onChange={handleChange}
            />
            <button type="submit" className="input-submit">Submit</button>
        </form>
    );
}

export default InputDataCreateAppointment;

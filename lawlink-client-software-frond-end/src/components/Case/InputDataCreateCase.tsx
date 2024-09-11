import React, { useState } from "react";

interface InputDataCreateCaseProps {
    onSubmit: (
        type: string,
        startingDate: string,
        information: string,
        status: boolean,
        isCaseWon: boolean,
        attorneyId: number
    ) => void;
    attorneyId: number;
}

const InputDataCreateCase: React.FC<InputDataCreateCaseProps> = ({ onSubmit, attorneyId }) => {
    const [formData, setFormData] = useState({
        type: "",
        startingDate: "",
        information: "",
        status: false,
        isCaseWon: false,
    });

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        onSubmit(
            formData.type,
            formData.startingDate,
            formData.information,
            formData.status,
            formData.isCaseWon,
            attorneyId
        );
        console.log(formData.type,formData.startingDate);
        
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
        const { name, value, type } = e.target;
        if (type === 'checkbox') {
            const { checked } = e.target as HTMLInputElement;
            setFormData(prevState => ({
                ...prevState,
                [name]: checked
            }));
        } else {
            setFormData(prevState => ({
                ...prevState,
                [name]: value
            }));
        }
    };

    return (
        <form className="form-container" onSubmit={handleSubmit}>
            <label htmlFor="caseType">Case Type:</label>
            <select
                name="type"
                className="input-select"
                value={formData.type}
                onChange={handleChange}
            >
                <option value="">Select Case Type</option>
                <option value="Criminal">Criminal</option>
                <option value="Civil">Civil</option>
                <option value="Family">Family</option>
                <option value="Corporate">Corporate</option>
            </select>
            <label htmlFor="startingDate">Starting Date:</label>
            <input
                type="date"
                name="startingDate"
                className="input-date"
                value={formData.startingDate}
                onChange={handleChange}
            />
            <label htmlFor="information">Information:</label>
            <textarea
                name="information"
                className="input-textarea"
                placeholder="Case Information"
                value={formData.information}
                onChange={handleChange}
            />
            <label htmlFor="status">
                <input
                    type="checkbox"
                    name="status"
                    className="input-checkbox"
                    checked={formData.status}
                    onChange={handleChange}
                />
                Status
            </label>
            <label htmlFor="isCaseWon">
                <input
                    type="checkbox"
                    name="isCaseWon"
                    className="input-checkbox"
                    checked={formData.isCaseWon}
                    onChange={handleChange}
                />
                Is Case Won
            </label>
            <button type="submit" className="input-submit">Create Case</button>
        </form>
    );
};

export default InputDataCreateCase;

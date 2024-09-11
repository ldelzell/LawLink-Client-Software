import React, { useEffect, useState } from 'react';
import caseApi from '../api/caseApi';

interface CaseDetailsProps {
    caseId: string;
}

interface CaseDetails {
    id: string;
    type: string;
    information: string;
    status: boolean;
    isCaseWon: boolean;
}

const CaseAttorneyDetails: React.FC<CaseDetailsProps> = ({ caseId }) => {
    const [caseDetails, setCaseDetails] = useState<CaseDetails | null>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        type: '',
        information: '',
        status: false,
        isCaseWon: false
    });

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        if (caseId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            const fetchCaseDetails = async () => {
                try {
                    const case_ = await caseApi.getCase(caseId, config);
                    setCaseDetails(case_);
                    setFormData({
                        type: case_.type,
                        information: case_.information,
                        status: case_.status,
                        isCaseWon: case_.isCaseWon
                    });
                } catch (error) {
                    console.error('Error fetching case details:', error);
                }
            };

            fetchCaseDetails();
        }
    }, [caseId]);

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleUpdateClick = () => {
        const accessToken = localStorage.getItem('accessToken');
        if (caseId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            caseApi.updateCase(caseId, { ...formData }, config)
                .then(response => {
                    console.log("Case updated successfully:", response);
                    setIsEditing(false);
                    setCaseDetails(response);
                })
                .catch(error => {
                    console.error('Error updating case:', error);
                });
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, type, value, checked } = e.target;

        if (type === 'checkbox') {
            if (name === 'status' && !checked) {
                setFormData(prevState => ({
                    ...prevState,
                    status: checked,
                    isCaseWon: false 
                }));
            } else if (name === 'isCaseWon') {
                setFormData(prevState => ({
                    ...prevState,
                    isCaseWon: checked
                }));
            } else {
                setFormData(prevState => ({
                    ...prevState,
                    [name]: checked
                }));
            }
        } else {
            setFormData(prevState => ({
                ...prevState,
                [name]: value
            }));
        }
    };

    return (
        <div className="main-container-case-details">
            {caseDetails && (
                <div className="container-case-data">
                    <h1>Case Details: {caseDetails.id}</h1>
                    <div>
                        <label>Type of Case:</label>
                        {isEditing ? (
                            <input type="text" name="type" value={formData.type} onChange={handleChange} />
                        ) : (
                            <p>{caseDetails.type}</p>
                        )}
                    </div>
                    <div>
                        <label>Case Information:</label>
                        {isEditing ? (
                            <input type="text" name="information" value={formData.information} onChange={handleChange} />
                        ) : (
                            <p>{caseDetails.information}</p>
                        )}
                    </div>
                    <div>
                        <label>Status:</label>
                        {isEditing ? (
                            <input 
                                type="checkbox" 
                                name="status" 
                                checked={formData.status} 
                                onChange={handleChange} 
                            />
                        ) : (
                            <p>{caseDetails.status ? 'Finished' : 'Not Finished'}</p>
                        )}
                    </div>
                    <div>
                        <label>Case Won:</label>
                        {isEditing ? (
                            <input 
                                type="checkbox" 
                                name="isCaseWon"
                                checked={formData.isCaseWon}
                                onChange={handleChange} 
                                disabled={!formData.status}
                            />
                        ) : (
                            <p>{caseDetails.isCaseWon ? 'Yes' : 'No'}</p>
                        )}
                    </div>
                    {isEditing ? (
                        <button className="btn btn-primary" onClick={handleUpdateClick}>Update</button>
                    ) : (
                        <button className="btn btn-primary" onClick={handleEditClick}>Edit</button>
                    )}
                </div>
            )}
        </div>
    );
};

export default CaseAttorneyDetails;

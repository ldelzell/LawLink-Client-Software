import React from 'react';
import caseApi from '../api/caseApi';
import InputDataCreateCase from './Case/InputDataCreateCase';

interface CaseCreateFormProps {
    onClose: () => void;
    clientId: number;
}

const CaseCreateForm: React.FC<CaseCreateFormProps> = ({ onClose, clientId }) => {
    const attorneyId = localStorage.getItem('attorneyId'); // Retrieve the attorneyId from local storage

    const createCase = async (
        type: string,
        startingDate: string,
        information: string,
        status: boolean,
        isCaseWon: boolean,
        attorneyId: number 
    ): Promise<void> => {
        try {
            const accessToken = localStorage.getItem('accessToken');
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };
            await caseApi.createCase({ type, startingDate, information, status, isCaseWon, clientId, attorneyId }, config); 
            console.log("Case created successfully");
            onClose(); 
        } catch (error) {
            console.error('Error creating case:', error);
        }
    };

    const numericAttorneyId = attorneyId ? Number(attorneyId) : null;

    if (numericAttorneyId === null) {
        console.error('Invalid attorney ID');
        return null;
    }

    return (
        <div className="form-container">
            <h1>Create Case</h1>
            <InputDataCreateCase onSubmit={createCase} attorneyId={numericAttorneyId} />
        </div>
    );
};

export default CaseCreateForm;

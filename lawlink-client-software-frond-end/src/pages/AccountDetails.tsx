import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import accountApi from '../api/accountApi';
import caseApi from '../api/caseApi';
import NavBar from '../components/NavBar';
import CaseCreateForm from '../components/CaseCreateForm';
import '../styles/Pages-CSS/AccountsDetails.css'

const AccountDetails: React.FC = () => {
    const { accountId } = useParams<{ accountId: string }>();
    const [account, setAccount] = useState<any>(null);
    const [case_, setCase] = useState<any>(null);
    const [showCaseCreation, setShowCaseCreation] = useState(false);

    const toggleCaseCreation = () => {
        setShowCaseCreation(!showCaseCreation);
    };

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');

        if (accountId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            const fetchAccountDetails = async () => {
                try {
                    const account = await accountApi.getAccount(accountId, config);
                    setAccount(account);

                    if (account.caseId) {
                        const case_ = await caseApi.getCaseByUserId(account.id, config);
                        setCase(case_);
                    }
                } catch (error) {
                    console.error('Error fetching account details:', error);
                }
            };

            fetchAccountDetails();
        }
    }, [accountId]);

    return (
        <div className="account-details-container">
            <NavBar />
            <div className="account-details-content">
                {account && (
                    <div className="account-details">
                        <h1 className="account-details-title">Account Details: {account.firstName}</h1>
                        <div className="account-info">
                            <p><strong>First Name:</strong> {account.firstName}</p>
                            <p><strong>Last Name:</strong> {account.lastName}</p>
                            <p><strong>ID:</strong> {account.id}</p>
                            <p><strong>Email:</strong> {account.email}</p>
                            {!account.caseId && 
                                <button className="create-case-button" onClick={toggleCaseCreation}>Create Case</button>
                            }
                            {showCaseCreation && (
                                <div className="modal">
                                    <div className="modal-content">
                                        <span className="close" onClick={toggleCaseCreation}>&times;</span>
                                        <CaseCreateForm onClose={toggleCaseCreation} clientId={account.id} /> 
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                )}
                {case_ ? (
                    <div className="case-details">
                        <h2>Case Details</h2>
                        <p><strong>Case ID:</strong> {account.caseId}</p>
                        <p><strong>Type:</strong> {case_.type}</p>
                        <p><strong>Date:</strong> {case_.startingDate}</p>
                        <p><strong>Information:</strong> {case_.information}</p>
                    </div>
                ) : (
                    <p>No case assigned</p>
                )}
            </div>
        </div>
    );
};

export default AccountDetails;

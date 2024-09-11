import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import caseApi from '../api/caseApi';
import NavBar from '../components/NavBar';
import accountApi from '../api/accountApi';
import '../styles/Pages-CSS/CasePage.css'




const CasePage: React.FC = () => {
    const { accountId } = useParams<{ accountId: string }>();
    const [case_, setCase] = useState<any>(null);
    const [account, setAccount] = useState<any>(null);

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        const userId = localStorage.getItem('id'); 

        if (userId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };
            
            const fetchCaseDetails = async () => {
                try {
                    //I AM CONVERTING THE USER ID INTOCLIENT ID AND THEN WHEN IT TAKES IT IT CONVERTS IT AGAIN? BECAUSE IT IS  -1
                    //Look in the backedn as well the mistake might be everywhere
                    const account = await accountApi.getProfile(userId, config);
                    setAccount(account);   
                    const case_ = await caseApi.getCaseByUserId(account.id, config);
                    setCase(case_);
                    
                    

                } catch (error) {
                    
                    console.error('Error fetching case details:', error);
                }
            };

            fetchCaseDetails();
        }
    }, [accountId]);

    return (
        <div>
            <NavBar />
            <div className="main-container-case">
                <div className="container-case-data">
                    {case_ ? (
                        <div className="container-data">
                            <h1>Case Details</h1>
                            <p>Case ID: {case_.id}</p>
                            <p>Type : {case_.type}</p>
                            <p>Case Details: {case_.information}</p>
                            <p>Starting Date: {case_.startingDate}</p>

                        </div>
                    ) : (
                        <p>No case found for this account</p>
                    )}
                </div>
            </div>
        </div>
    );
};

export default CasePage;

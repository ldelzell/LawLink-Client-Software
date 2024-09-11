import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import attorneyApi from '../api/attorneyApi'; 
import NavBar from '../components/NavBar';
import '../styles/Pages-CSS/Attourney.css'

const AttorneyPage: React.FC = () => {
    const { accountId } = useParams<{ accountId: string }>();
    const [attorney, setAttorney] = useState<any>(null);

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        const clientId = localStorage.getItem('id');


        if (clientId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };
            
            const fetchAttorneyDetails = async () => {
                try {
                    const attorneyData = await attorneyApi.getAttorneyByClient(clientId, config);
                    setAttorney(attorneyData);
                } catch (error) {
                    console.error('Error fetching attorney details:', error);
                }
            };

            fetchAttorneyDetails();
        }
    }, [accountId]);

    return (
        <div>
    <NavBar />
    <div className="main-container-attorney">
        <div className="container-attorney-data">
            {attorney ? (
                <div className="container-data">
                    <h1 className="profile-title">Attorney Profile</h1>
                    <div className="profile-info">
                        <p><strong>ID:</strong> {attorney.id}</p>
                        <p><strong>First Name:</strong> {attorney.firstName}</p>
                        <p><strong>Last Name:</strong> {attorney.lastName}</p>
                        <p><strong>Email:</strong> {attorney.email}</p>
                    </div>
                </div>
            ) : (
                <p className="no-info-message">No attorney information available for this client</p>
            )}
        </div>
    </div>
</div>

    );
};

export default AttorneyPage;

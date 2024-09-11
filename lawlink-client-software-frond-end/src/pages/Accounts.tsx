import React, { useState, useEffect } from 'react';
import ListOfAccounts from '../components/ListOfAccounts';
import '../styles/Pages-CSS/Accounts.css';
import accountApi from '../api/accountApi';
import NavBar from '../components/NavBar';
import attorneyApi from '../api/attorneyApi';
import AccountDetails from './AccountDetails';

const AccountPage = () => {
    const [accounts, setAccounts] = useState([]);
    const [clients, setClients] = useState([]);

    const accessToken = localStorage.getItem('accessToken');
    const attorneyId = localStorage.getItem('attorneyId') as string;

    useEffect(() => {
        if (attorneyId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };
            fetchClientsByAttorney(config);
        } else {
            console.log("Authorization token or attorney ID is missing");
        }
    }, [attorneyId, accessToken]);

    const fetchClientsByAttorney = async (config: any) => {
        try {
            const data = await attorneyApi.getClientsByAttorney(attorneyId, config);
            setClients(data.clients); 
            console.log('Fetched clients:', data.clients);
        } catch (error) {
            console.error('Error fetching clients by attorney:', error);
        }
    };

    const handleDelete = async (accountId: string) => {
        try {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };
            await accountApi.deleteAccount(accountId, config);
            console.log('Account deleted successfully');
        } catch (error) {
            console.error('Error deleting the account:', error);
        }
    };

   return (
    <div className="container">
      <NavBar />
      <div className="container-main">
        <h1 className="title">Accounts Page</h1>
        <ListOfAccounts accounts={clients} onDelete={handleDelete} />
        <AccountDetails />
      </div>
    </div>
  );
};

export default AccountPage;

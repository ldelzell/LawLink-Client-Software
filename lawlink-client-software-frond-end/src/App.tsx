// App.tsx
import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import LogIn from './pages/LogIn';
import HomePage from './pages/HomePage';
import CreateAccount from './pages/CreateAccount';
import Accounts from './pages/Accounts';
import './App.css';
import CreateAppointment from './pages/CreateAppointment';
import LawLink from './pages/LawLink';
import Attourney from './pages/Attourney';
import Chat from './pages/Chat';
import UploadFiles from './pages/UploadFiles';
import Payments from './pages/Payments';
import Profile from './pages/Profile';
import LogOut from './pages/LogOut';
import AccountDetails from './pages/AccountDetails';
import CasePage from './pages/CasePage';
import CaseAttorney from './pages/CasesAttorney';
import AttorneysStats from './pages/AttorneyStats';

import { checkAccessToken } from './utils/tokenRefresh';

const App: React.FC = () => {

  const [timeLeft, setTimeLeft] = useState(0);

  const [timeLeftMinutes, setTimeLeftMinutes] = useState(0);

  useEffect(() => {
    const tokenRefreshInterval = setInterval(() => {
      checkAccessToken();
      const accessToken = localStorage.getItem('accessToken');
            const decodedToken = accessToken ? parseJwt(accessToken) : null;

            if (decodedToken) {
                const expiryTime = decodedToken.exp * 1000;
                const remainingMinutes = Math.max(0, (expiryTime - Date.now()) / (1000 * 60));

                setTimeLeftMinutes(Math.floor(remainingMinutes));
            }
          }, 1000); 

          return () => {
            clearInterval(tokenRefreshInterval);
          };
        }, []);

    const parseJwt = (token: string) => {
        try {
            return JSON.parse(atob(token.split('.')[1]));
        } catch (error) {
            console.error(error);
            return null;
        }
    };

const isAuthenticated = localStorage.getItem('accessToken') != null;

  return (

    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={isAuthenticated ? <HomePage /> : <Navigate to="/login" />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/create-account" element={isAuthenticated ? <CreateAccount /> : <Navigate to="/login" />} />
          <Route path="/accounts" element={isAuthenticated ? <Accounts /> : <Navigate to="/login" />} />
          <Route path="/accounts/:accountId" element={isAuthenticated ? <AccountDetails /> : <Navigate to="/login" />} />
          <Route path="/create-appointment" element={isAuthenticated ? <CreateAppointment /> : <Navigate to="/login" />} />
          <Route path="/lawlink" element={isAuthenticated ? <LawLink /> : <Navigate to="/login" />} />
          <Route path="/attourney" element={isAuthenticated ? <Attourney /> : <Navigate to="/login" />} />
          <Route path="/chat" element={isAuthenticated ? <Chat /> : <Navigate to="/login" />} />
          <Route path="/upload-files" element={isAuthenticated ? <UploadFiles /> : <Navigate to="/login" />} />
          <Route path="/payments" element={isAuthenticated ? <Payments /> : <Navigate to="/login" />} />
          <Route path="/logout" element={isAuthenticated ? <LogOut /> : <Navigate to="/login" />} />
          <Route path="/profile" element={isAuthenticated ? <Profile /> : <Navigate to="/login" />} />
          <Route path="/case" element={isAuthenticated ? <CasePage /> : <Navigate to="/login" />} />
          <Route path="/caseAttorney" element={isAuthenticated ? <CaseAttorney /> : <Navigate to="/login" />} />
          <Route path="/attorneysStats" element={isAuthenticated ? <AttorneysStats /> : <Navigate to="/login" />} />


        </Routes>
      </div>
    </Router>
  );
};

export default App;

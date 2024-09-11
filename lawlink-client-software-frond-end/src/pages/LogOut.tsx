// import React, { useEffect, useState } from 'react';
// import { Navigate } from 'react-router-dom';
// import axios from 'axios';
// import loginApi from '../api/loginApi';

// const LogOut: React.FC = () => {
//     const [isLoggedOut, setIsLoggedOut] = useState(false);

//     useEffect(() => {
//         const logout = async () => {
//             const accessToken = localStorage.getItem('accessToken');
//             const userId = localStorage.getItem('id');
        
//             if (userId && accessToken) {
                     
//                 const handleDelete = async (userId: string) => {
//                     try {
//                         const accessToken = localStorage.getItem('accessToken');
                
//                         if (!accessToken) {
//                             console.error('Access token is missing');
//                             return;
//                         }
                
//                         const config = {
//                             headers: {
//                                 Authorization: `Bearer ${accessToken}`
//                             }
//                         };
                
//                         await loginApi.logout(userId, config);
//                         console.log('Account deleted successfully');
//                     } catch (error) {
//                         console.error('Error deleting the account:', error);
//                     }
//                 };
                
//                 handleDelete(userId);
//                 localStorage.removeItem('accessToken');
//                 localStorage.removeItem('refreshToken');
//                 localStorage.removeItem('id');
//                 localStorage.removeItem('attorneyId');
//                 localStorage.removeItem('userRoles');
//                 setIsLoggedOut(true);
//             }
//         };

//         logout();
//     }, []);

//     return isLoggedOut ? <Navigate to="/login" /> : <div>Logging out...</div>;  
// };

// export default LogOut;





import React, { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import loginApi from '../api/loginApi';

const LogOut: React.FC = () => {
    const [isLoggedOut, setIsLoggedOut] = useState(false);

    useEffect(() => {
        const logout = async () => {
            const accessToken = localStorage.getItem('accessToken');
            const userId = localStorage.getItem('id');

            if (userId && accessToken) {
                const handleDelete = async (userId: string) => {
                    try {
                        const accessToken = localStorage.getItem('accessToken');

                        if (!accessToken) {
                            console.error('Access token is missing');
                            return;
                        }

                        const config = {
                            headers: {
                                Authorization: `Bearer ${accessToken}`
                            }
                        };

                        await loginApi.logout(userId, config);
                        console.log('Logged out successfully');
                    } catch (error) {
                        console.error('Error logging out:', error);
                    }
                };

                await handleDelete(userId);
                localStorage.clear();
                setIsLoggedOut(true);
            }
        };

        logout();
    }, []);

    return isLoggedOut ? <Navigate to="/login" /> : <div>Logging out...</div>;
};

export default LogOut;

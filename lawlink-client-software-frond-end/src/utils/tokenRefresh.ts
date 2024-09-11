// import axios from 'axios';

// let tokenRefreshInterval: any; 

// const TOKEN_REFRESH_MARGIN = 60;
// let refreshingToken = false;

// export const checkAccessToken = async () => {
//     if (refreshingToken) {
//         return;
//     }

//     refreshingToken = true;

//     const accessToken = localStorage.getItem('accessToken');
//     const refreshToken = localStorage.getItem('refreshToken');


//     if (!accessToken) {
//         refreshingToken = false;
//         return;
//     }

//     const decodedToken = parseJwt(accessToken);

//     if (!decodedToken) {
//         refreshingToken = false;
//         return;
//     }

//     const expiryTime = decodedToken.exp * 1000;
//     const currentTime = Date.now();
//     const remainingTime = Math.max(0, (expiryTime - currentTime) / 1000);

//     console.log(`Token expires in ${remainingTime} seconds`);

//     if (remainingTime <= TOKEN_REFRESH_MARGIN) {
//         try {

//             const response = await axios.post('http://localhost:8080/tokens/refresh', {
//                 refreshToken: refreshToken 
//             }, {
//                 headers: {
//                     'Authorization': `Bearer ${accessToken}`,
//                     'Content-Type': 'application/json'
//                 }
//             });
            
            

//             const newAccessToken = response.data.accessToken;
//             const newRefreshToken = response.data.refreshToken;

//             localStorage.setItem('accessToken', newAccessToken);
//             localStorage.setItem('refreshToken', newRefreshToken);

//             clearInterval(tokenRefreshInterval);

//             tokenRefreshInterval = setInterval(() => checkAccessToken(), 1000);

//         } catch (error) {
//             console.error('Error refreshing access token:', error);
//         }
//     }

//     refreshingToken = false;
// };

// const parseJwt = (token: string) => {
//     try {
//         return JSON.parse(atob(token.split('.')[1]));
//     } catch (e) {
//         return null;
//     }
// };



import axios from 'axios';

let tokenRefreshInterval: any;

const TOKEN_REFRESH_MARGIN = 60;
let refreshingToken = false;

export const checkAccessToken = async () => {
    if (refreshingToken) {
        return;
    }

    refreshingToken = true;

    const accessToken = localStorage.getItem('accessToken');
    const refreshToken = localStorage.getItem('refreshToken');

    if (!accessToken) {
        refreshingToken = false;
        return;
    }

    const decodedToken = parseJwt(accessToken);

    if (!decodedToken) {
        refreshingToken = false;
        return;
    }

    const expiryTime = decodedToken.exp * 1000;
    const currentTime = Date.now();
    const remainingTime = Math.max(0, (expiryTime - currentTime) / 1000);

    console.log(`Token expires in ${remainingTime} seconds`);

    if (remainingTime <= TOKEN_REFRESH_MARGIN) {
        try {
            const response = await axios.post('http://localhost:8080/tokens/refresh', {
                refreshToken: refreshToken 
            }, {
                headers: {
                    'Authorization': `Bearer ${accessToken}`,
                    'Content-Type': 'application/json'
                }
            });

            const newAccessToken = response.data.accessToken;
            const newRefreshToken = response.data.refreshToken;

            localStorage.setItem('accessToken', newAccessToken);
            localStorage.setItem('refreshToken', newRefreshToken);

            clearInterval(tokenRefreshInterval);

            tokenRefreshInterval = setInterval(() => checkAccessToken(), 1000);

        } catch (error) {
            console.error('Error refreshing access token:', error);
            handleLogout();
        }
    }

    refreshingToken = false;
};

const parseJwt = (token: string) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        return null;
    }
};

const handleLogout = () => {
    localStorage.clear();
    window.location.href = '/logout';
};

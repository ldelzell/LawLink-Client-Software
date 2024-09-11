import axios from 'axios';

const loginApi = {
    login: (credentials: { username: string, password: string }) => {
        return axios.post('http://localhost:8080/tokens', credentials)
            .then(response => response.data);
    },
    logout: (userId: string, config: any) => {
        return axios.delete(`http://localhost:8080/tokens/${userId}`, config);
    },
    refresh: (accessToken: string, config: any) => {
        return axios.post('http://localhost:8080/token/refresh', accessToken, config);
    }
    
};

export default loginApi;


// import axiosInstance from '../api/RefreshToken/axiosInstance';

// const loginApi = {
//   login: (credentials: { username: string, password: string }) => {
//     return axiosInstance.post('/tokens', credentials)
//       .then(response => {
//         const { accessToken, refreshToken } = response.data;
//         localStorage.setItem('accessToken', accessToken);
//         localStorage.setItem('refreshToken', refreshToken);
//         return response.data;
//       });
//   }
// };

// export default loginApi;

// import axios, { AxiosRequestConfig, AxiosResponse, AxiosError } from 'axios';

// // Create an Axios instance
// const axiosInstance = axios.create({
//   baseURL: 'http://localhost:8080', // Adjust your API base URL accordingly
// });

// let isRefreshing = false;
// let refreshSubscribers: ((token: string) => void)[] = [];

// function subscribeTokenRefresh(cb: (token: string) => void) {
//   refreshSubscribers.push(cb);
// }

// function onRefreshed(token: string) {
//   refreshSubscribers.forEach(cb => cb(token));
//   refreshSubscribers = [];
// }

// // Request interceptor to add the Authorization header
// axiosInstance.interceptors.request.use((config: AxiosRequestConfig) => {
//   const token = localStorage.getItem('accessToken');
//   if (token) {
//     config.headers['Authorization'] = `Bearer ${token}`;
//   }
//   return config;
// }, (error: AxiosError) => {
//   return Promise.reject(error);
// });

// // Response interceptor to handle token refresh logic
// axiosInstance.interceptors.response.use(
//   (response: AxiosResponse) => response,
//   async (error: AxiosError) => {
//     const { config, response } = error;
//     const originalRequest = config;

//     if (response?.status === 401 && !originalRequest._retry) {
//       if (isRefreshing) {
//         return new Promise((resolve) => {
//           subscribeTokenRefresh((token: string) => {
//             originalRequest.headers['Authorization'] = `Bearer ${token}`;
//             resolve(axiosInstance(originalRequest));
//           });
//         });
//       }

//       originalRequest._retry = true;
//       isRefreshing = true;

//       const refreshToken = localStorage.getItem('refreshToken');
//       if (!refreshToken) {
//         // Handle logout here if needed
//         return Promise.reject(error);
//       }

//       try {
//         const response = await axiosInstance.post('/tokens/refresh', { refreshToken });
//         const { accessToken, refreshToken: newRefreshToken } = response.data;

//         localStorage.setItem('accessToken', accessToken);
//         localStorage.setItem('refreshToken', newRefreshToken);

//         axiosInstance.defaults.headers['Authorization'] = `Bearer ${accessToken}`;
//         onRefreshed(accessToken);
//         isRefreshing = false;

//         return axiosInstance(originalRequest);
//       } catch (err) {
//         isRefreshing = false;
//         // Handle logout here if needed
//         return Promise.reject(err);
//       }
//     }
    
//     return Promise.reject(error);
//   }
// );

// export default axiosInstance;

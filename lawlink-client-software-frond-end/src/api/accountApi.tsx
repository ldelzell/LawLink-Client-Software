import axios from "axios";

const accountApi = {
    getAccounts: (config: any) => axios.get('http://localhost:8080/accounts', config).then(response => response.data),
    createClient: (newAccount: any, config: any) => axios.post('http://localhost:8080/accounts', newAccount, config),
    deleteAccount: (accountId: string, config: any) => axios.delete(`http://localhost:8080/accounts/${accountId}`, config),
    getAccount: (accountId: string, config: any) => axios.get(`http://localhost:8080/accounts/${accountId}`, config).then(response => response.data),
    getProfile: (accountId: string, config: any) => axios.get(`http://localhost:8080/profile/${accountId}`, config).then(response => response.data),
    updateProfile: (accountId: string, updatedData: any, config: any) => axios.put(`http://localhost:8080/accounts/${accountId}`, updatedData, config).then(response => response.data) // Use PUT for update
};

export default accountApi;

import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

const caseApi = {
    createCase: (newCase: any, config: any) => axios.post(`${BASE_URL}/cases`, newCase, config),
    getCase: (accountId: string, config: any) => axios.get(`${BASE_URL}/cases/${accountId}`, config)
    .then(response => response.data),
    getCaseByUserId: (accountId: string, config: any) => axios.get(`${BASE_URL}/cases/user/${accountId}`, config)
    .then(response => response.data),
    updateCase: (caseId: string, updatedData: any, config: any) => axios.put(`${BASE_URL}/cases/${caseId}`, updatedData, config)
        .then(response => response.data) 
};

export default caseApi;

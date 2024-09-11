import axios from 'axios';

const BASE_URL = 'http://localhost:8080';

const attorneyApi = {
    createAttorney: (newAccountAttorney: any, config: any) => axios.post(`${BASE_URL}/attorneys`, newAccountAttorney, config),
    getAccount: (accountId: string, config: any) => axios.get(`${BASE_URL}/attorneys/${accountId}`, config)
        .then(response => response.data),
    getClientsByAttorney: (attorneyId: string, config: any) => axios.get(`${BASE_URL}/attorneys/${attorneyId}/clients`, config)
        .then(response => response.data),
    getCasesByAttorneyId: (attorneyId: string, config: any) => axios.get(`${BASE_URL}/attorneys/${attorneyId}/cases`, config)
        .then(response => response.data),
    updateProfile: (accountId: string, updatedData: any, config: any) => axios.put(`${BASE_URL}/attorneys/${accountId}`, updatedData, config)
        .then(response => response.data),
    getWinRateForAttorney: (attorneyId: string, config: any) : Promise<number> => axios.get(`${BASE_URL}/attorneys/${attorneyId}/winrate`, config)
        .then(response => response.data),
    getAttorneyByClient: (accountId: string, config: any) => axios.get(`${BASE_URL}/attorneys/client/${accountId}`, config)
        .then(response => response.data),
};

export default attorneyApi;

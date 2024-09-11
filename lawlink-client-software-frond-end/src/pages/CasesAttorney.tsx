import React, { useState, useEffect } from 'react';
import NavBar from '../components/NavBar';
import ListOfCases from '../components/Case/ListOfCases';
import attorneyApi from '../api/attorneyApi';
import { Case } from '../types/Case';
import CaseAttorneyDetails from './CasesAttorneyDetails';
import '../styles/Pages-CSS/CasesAttorney.css';
import Modal from '../components/Modal';

const CaseAttorney = () => {
    const [cases, setCases] = useState<Case[]>([]);
    const [filteredCases, setFilteredCases] = useState<Case[]>([]);
    const [selectedType, setSelectedType] = useState<string>('');
    const [selectedCaseId, setSelectedCaseId] = useState<string | null>(null);
    const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
    const accessToken = localStorage.getItem('accessToken');
    const attorneyId = localStorage.getItem('attorneyId') as string;
    
    useEffect(() => {
        if (attorneyId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };
            fetchCases(config);
        } else {
            console.log("Authorization token or attorney ID is missing");
        }
    }, [attorneyId, accessToken]);

    const fetchCases = async (config: any) => {
        try {
            const response = await attorneyApi.getCasesByAttorneyId(attorneyId, config);
            console.log('Full response:', response);

            if (response.cases && Array.isArray(response.cases)) {
                setCases(response.cases);
                setFilteredCases(response.cases);
                console.log('Fetched cases:', response.cases);
            } else {
                console.error('Unexpected response structure:', response);
                setCases([]);
                setFilteredCases([]);
            }
        } catch (error) {
            console.error('Error fetching cases:', error);
            setCases([]);
            setFilteredCases([]);
        }
    };

    const handleTypeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        const selectedType = event.target.value;
        console.log(`Selected type: ${selectedType}`);
        setSelectedType(selectedType);
        filterCases(selectedType);
    };

    const filterCases = (type: string) => {
        if (type === '') {
            setFilteredCases(cases);
        } else {
            const filtered = cases.filter(singleCase => singleCase.type === type);
            console.log(`Filtered cases:`, filtered);
            setFilteredCases(filtered);
        }
    };

    const handleInspect = (caseId: string) => {
        setSelectedCaseId(caseId);
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setSelectedCaseId(null);
    };

    return (
        <div className='container'>
            <NavBar />
            <div className='container-main'>
                <h1 className='title'>Case Page</h1>
                <div className='filter-container'>
                    <label htmlFor='caseTypeFilter'>Filter by Case Type:</label>
                    <select id='caseTypeFilter' value={selectedType} onChange={handleTypeChange}>
                        <option value=''>All</option>
                        <option value='Civil'>Civil</option>
                        <option value='Corporate'>Corporate</option>
                        <option value='Criminal'>Criminal</option>
                    </select>
                </div>
                <ListOfCases cases={filteredCases} onInspect={handleInspect} />
                {selectedCaseId && (
                    <Modal show={isModalOpen} onClose={handleCloseModal}>
                        <CaseAttorneyDetails caseId={selectedCaseId} />
                    </Modal>
                )}
            </div>
        </div>
    );
};

export default CaseAttorney;

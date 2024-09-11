import axios from "axios";
import { useEffect, useState } from "react";
import styled from "styled-components";
import NavBar from "../components/NavBar";
import attorneyApi from "../api/attorneyApi";

interface AttorneyWinRate {
    attorneyId: number;
    firstName: string;
    lastName: string;
    totalWins: number;
}

interface AttorneyWithWinRate extends AttorneyWinRate {
    winRate: number | null;
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 2rem;
   background: linear-gradient(135deg,#34324a, rgb(107, 87, 181), #e0eafc, #cfdef3);
  min-height: 100vh;
`;

const Title = styled.h1`
  color: white;
  margin-bottom: 2rem;
  margin-top: 130px;
`;

const Table = styled.table`
  width: 80%;
  border-collapse: collapse;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const TableHead = styled.thead`
  background-color: #4CAF50;
  color: white;
`;

const TableHeader = styled.th`
  padding: 1rem;
  text-align: left;
`;

const TableBody = styled.tbody`
  background-color: #fff;
`;

const TableRow = styled.tr`
  &:nth-child(even) {
    background-color: #f2f2f2;
  }
`;

const TableCell = styled.td`
  padding: 1rem;
  border-bottom: 1px solid #ddd;
`;

const ErrorMessage = styled.div`
  color: red;
  margin-top: 1rem;
`;

const AttorneysStats: React.FC = () => {
    const [attorneys, setAttorneys] = useState<AttorneyWithWinRate[]>([]);
    const [error, setError] = useState<string | null>(null);
    const accessToken = localStorage.getItem('accessToken');

    useEffect(() => {
        if (accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            axios.get<AttorneyWinRate[]>('http://localhost:8080/attorneys/stats', config)
                .then(response => {
                    const attorneysData = response.data;
                    const fetchWinRates = async () => {
                        const updatedAttorneys = await Promise.all(attorneysData.map(async (attorney) => {
                            try {
                                const winRate = await attorneyApi.getWinRateForAttorney(attorney.attorneyId.toString(), config);
                                return { ...attorney, winRate };
                            } catch (error) {
                                console.error('Error fetching win rate:', error);
                                return { ...attorney, winRate: null };
                            }
                        }));
                        setAttorneys(updatedAttorneys);
                    };
                    fetchWinRates();
                })
                .catch(error => {
                    setError('Failed to fetch data');
                });
        } else {
            console.log("Authorization token is missing");
        }
    }, [accessToken]);

    return (
        <div className="main-container-stats">
            <NavBar/>
            <Container>
                <Title>Attorney Win Rates</Title>
                {error && <ErrorMessage>{error}</ErrorMessage>}
                <Table>
                    <TableHead>
                        <tr>
                            <TableHeader>Attorney ID</TableHeader>
                            <TableHeader>First Name</TableHeader>
                            <TableHeader>Last Name</TableHeader>
                            <TableHeader>Total Wins</TableHeader>
                            <TableHeader>Win Rate</TableHeader>
                        </tr>
                    </TableHead>
                    <TableBody>
                        {attorneys.map((attorney) => (
                            <TableRow key={attorney.attorneyId}>
                                <TableCell>{attorney.attorneyId}</TableCell>
                                <TableCell>{attorney.firstName}</TableCell>
                                <TableCell>{attorney.lastName}</TableCell>
                                <TableCell>{attorney.totalWins}</TableCell>
                                <TableCell>{typeof attorney.winRate === 'number' ? `${attorney.winRate.toFixed(2)}%` : 'N/A'}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </Container>
        </div>
    );
};

export default AttorneysStats;

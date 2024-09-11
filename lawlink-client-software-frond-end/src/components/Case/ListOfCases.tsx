import React from 'react';
import SingleCase from './SingleCase';
import { Case } from '../../types/Case';
import '../../styles/Components-CSS/Case/ListOfCases.css'


interface ListOfCasesProps {
    cases: Case[];
    onInspect: (caseId: string) => void;
}

const ListOfCases: React.FC<ListOfCasesProps> = ({ cases, onInspect }) => {
    console.log("Cases:", cases);

    if (!Array.isArray(cases) || cases.length === 0) {
        return <div>No Cases Available</div>;
    }

    return (
        <div className="cases-list-container">
          <ul className="list-of-cases">
            {cases.map(singleCase => (
              <li key={singleCase.id} className="case-item">
                <SingleCase singleCase={singleCase} onInspect={onInspect} />
              </li>
            ))}
          </ul>
        </div>
      );
}

export default ListOfCases;

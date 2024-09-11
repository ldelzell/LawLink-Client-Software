import React from 'react';
import moment from 'moment';
import '../../styles/Components-CSS/Case/SingleCase.css'


interface SingleCaseProps {
    singleCase: {
        id: string;
        type: string;
        startingDate: string; 
        information: string;
        status: boolean;
        isCaseWon: boolean;
    };
    onInspect: (caseId: string) => void;
}

const SingleCase: React.FC<SingleCaseProps> = ({ singleCase, onInspect }) => {
    const formattedStartingDate = moment(singleCase.startingDate).format('MMMM Do YYYY');

    return (
        <div className="single-case-container">
          <span className="case-id">Id: {singleCase.id}</span>
          <span className="case-type">Type: {singleCase.type}</span>
          <span className="case-starting-date">Starting Date: {formattedStartingDate}</span>
          <span className="case-information">Information: {singleCase.information}</span>
          <span className="case-status">Status: {singleCase.status ? 'Active' : 'Inactive'}</span>
          <span className="case-is-case-won">Case Won: {singleCase.isCaseWon ? 'Yes' : 'No'}</span>
          <button className="inspect-button" onClick={() => onInspect(singleCase.id)}>Inspect</button>
        </div>
      );
};

export default SingleCase;

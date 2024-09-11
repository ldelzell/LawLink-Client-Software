import React from 'react';
import '../styles/Components-CSS/SingleAccount.css'
interface AccountItemProps {
    account: {
        id: string;
        firstName: string;
        lastName: string;
        email: string;
        password: string;
        caseType: string;
        attorneyId: number;
    };
    onDelete: (accountId: string) => void;
}

const SingleAccount: React.FC<AccountItemProps> = ({ account, onDelete }) => {
    const handleDelete = () => {
        onDelete(account.id);
    };

    return (
        <div className="single-account-container">
          <span className="account-name">Name: {account.firstName} {account.lastName}</span>
          <span className="account-email">Email: {account.email}</span>
          <button className="delete-button" onClick={handleDelete}>Delete</button>
          <a className="view-details-link" href={`/accounts/${account.id}`}>View Details</a>
        </div>
      );
};

export default SingleAccount;

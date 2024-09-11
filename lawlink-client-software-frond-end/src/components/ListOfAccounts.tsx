import React from "react";
import { Account } from "../types/Account";
import SingleAccount from "./SingleAccount";
import '../styles/Components-CSS/listOfAccounts.css';

interface ListOfAccountsProps {
    accounts: Account[];
    onDelete: (accountId: string) => void;
}

const ListOfAccounts: React.FC<ListOfAccountsProps> = ({ accounts, onDelete }) => {
    console.log("Accounts:", accounts);

    if (accounts.length === 0) {
        return <div>No Accounts Available</div>
    }

    return (
      <div className="accounts-list-container">
        <ul className="list-of-accounts">
          {accounts.map(account => (
            <li key={account.id} className="account-item">
              <SingleAccount account={account} onDelete={onDelete} />
            </li>
          ))}
        </ul>
      </div>
    );
}

export default ListOfAccounts;

import React from "react";
import { Account } from "../../types/Account";
import '../../styles/Components-CSS/Chat/SingleAccount.css'

interface SingleClientProps {
  account: Account;
  onChatSelection: (account: Account) => void;
}

const SingleClient: React.FC<SingleClientProps> = ({ account, onChatSelection }) => {
  return (
    <div className="client-container">
      <div className="client" onClick={() => onChatSelection(account)}>
        <div className="client-avatar">
          <img src="../public/img/profile.jpg" alt={account.firstName} className="avatar" />
        </div>
        <div className="client-details">
          <div className="client-name">{account.firstName}</div>
        </div>
      </div>
    </div>
  );
};

export default SingleClient;
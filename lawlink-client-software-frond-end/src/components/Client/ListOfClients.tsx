import React from "react";
import SingleClient from "./SingleClient";
import { Account } from "../../types/Account";

interface ListOfClientsProps {
  accounts: Account[];
  onChatSelection: (chatUser: Account) => void;
}

const ListOfClients: React.FC<ListOfClientsProps> = ({ accounts, onChatSelection }) => {
  return (
    <div>
      {accounts.map((account) => (
        <SingleClient key={account.id} account={account} onChatSelection={onChatSelection} />
      ))}
    </div>
  );
};

export default ListOfClients;

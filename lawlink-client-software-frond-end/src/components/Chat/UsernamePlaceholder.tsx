import React from 'react';

interface UsernamePlaceholderProps {
  username: string | null;
  onUsernameInformed: (username: string) => void;
}

const UsernamePlaceholder: React.FC<UsernamePlaceholderProps> = ({ username, onUsernameInformed }) => {
  if (username) {
    return <h2>Username: {username}</h2>;
  }

  return (
    <>
      <label htmlFor='username'>Username:</label>
      <input id='username' type='text' onBlur={(event) => onUsernameInformed(event.target.value)} />
    </>
  );
};

export default UsernamePlaceholder;

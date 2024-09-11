import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import accountApi from '../api/accountApi';

const EditProfile: React.FC = () => {
    const { userId } = useParams<{ userId: string }>();
    const [user, setUser] = useState<any>(null);
    const [isEditing, setIsEditing] = useState(false);
    const [formData, setFormData] = useState({
        firstName: '',
        lastName: '',
        email: '',
        password: ''
    });
    const navigate = useNavigate();

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');

        if (userId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            accountApi.getAccount(userId, config)
                .then(account => {
                    setUser(account);
                    setFormData({
                        firstName: account.firstName,
                        lastName: account.lastName,
                        email: account.email,
                        password: '' 
                    });
                })
                .catch(error => {
                    console.error('Error fetching account details:', error);
                });
        }
    }, [userId]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleEditClick = () => {
        setIsEditing(true);
    };

    const handleUpdateClick = () => {
        const accessToken = localStorage.getItem('accessToken');

        if (userId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            accountApi.updateProfile(userId, formData, config)
                .then(() => {
                    setIsEditing(false);
                    navigate(`/profile`);
                })
                .catch(error => {
                    console.error('Error updating profile:', error);
                });
        }
    };

    if (!user) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h2>Edit Profile</h2>
            <div>
                <label>First Name:</label>
                <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    disabled={!isEditing}
                />
            </div>
            <div>
                <label>Last Name:</label>
                <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    disabled={!isEditing}
                />
            </div>
            <div>
                <label>Email:</label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    disabled={!isEditing}
                />
            </div>
            <div>
                <label>Password:</label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    disabled={!isEditing}
                />
            </div>
            {isEditing ? (
                <button className="btn btn-primary" onClick={handleUpdateClick}>Update</button>
            ) : (
                <button className="btn btn-primary" onClick={handleEditClick}>Edit</button>
            )}
        </div>
    );
};

export default EditProfile;

import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import accountApi from '../api/accountApi';
import attorneyApi from '../api/attorneyApi';
import NavBar from '../components/NavBar';
import '../styles/Pages-CSS/Profile.css';

const Profile: React.FC = () => {
    const [user, setUser] = useState<any>(null);
    const [isEditing, setIsEditing] = useState<boolean>(false);
    const [editedUser, setEditedUser] = useState<any>({});
    const navigate = useNavigate();
    const role = localStorage.getItem('userRoles');
    const [winRate, setWinRate] = useState<number | null>(null);

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        const userId = localStorage.getItem('id');

        if (userId && accessToken) {
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            const fetchAccountDetails = async () => {
                try {
                    if (role === "ATTORNEY") {
                        const account = await attorneyApi.getAccount(userId, config);
                        setUser(account);
                        setEditedUser(account);
                        localStorage.setItem('attorneyId', account.id);
                    } else {
                        const profile = await accountApi.getProfile(userId, config);
                        setUser(profile);
                        setEditedUser(profile);
                    }
                } catch (error) {
                    console.error('Error fetching account/profile details:', error);
                }
            };

            fetchAccountDetails();
        }
    }, [role]);

    const handleEditClick = async () => {
        if (isEditing) {
            const accessToken = localStorage.getItem('accessToken');
            const config = {
                headers: {
                    Authorization: `Bearer ${accessToken}`
                }
            };

            try {
                const updateProfile = role === "ATTORNEY"
                    ? attorneyApi.updateProfile
                    : accountApi.updateProfile;

                const updatedProfile = await updateProfile(user.id, editedUser, config);
                setUser(updatedProfile);
                setEditedUser(updatedProfile); 
                setIsEditing(false);
                console.log('Profile updated successfully:', updatedProfile);
            } catch (error) {
                console.error('Error updating profile:', error);
            }
        } else {
            setIsEditing(true);
        }
    };

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setEditedUser((prev: any) => ({ ...prev, [name]: value }));
    };

    const navigateToCasePage = () => {
        navigate('/case');
    };

    return (
        <div>
            <NavBar />
            <div className="main-container-profile">
                <div className="container-profile-all-data">
                    <div className='container-profile-data'>
                        <div className="container-image">
                            <img src="../public/img/profile.jpg" alt="Profile" />
                        </div>
                        {user && (
                            <div className="container-data">
                                {isEditing ? (
                                    <>
                                        <label>
                                            First Name:
                                            <input
                                                type="text"
                                                name="firstName"
                                                value={editedUser.firstName || ''}
                                                onChange={handleChange}
                                            />
                                        </label>
                                        <label>
                                            Last Name:
                                            <input
                                                type="text"
                                                name="lastName"
                                                value={editedUser.lastName || ''}
                                                onChange={handleChange}
                                            />
                                        </label>
                                        <label>
                                            Email:
                                            <input
                                                type="text"
                                                name="email"
                                                value={editedUser.email || ''}
                                                onChange={handleChange}
                                            />
                                        </label>
                                    </>
                                ) : (
                                    <>
                                        <p>First Name: {user.firstName}</p>
                                        <p>Last Name: {user.lastName}</p>
                                        <p>ID: {user.id}</p>
                                        <p>Email: {user.email}</p>
                                    </>
                                )}
                                <button className="btn btn-primary" onClick={handleEditClick}>
                                    {isEditing ? 'Save' : 'Edit'}
                                </button>
                            </div>
                        )}
                    </div>
                    {role !== "ATTORNEY" && (
                        <button className="btn btn-primary" onClick={navigateToCasePage}>View Case</button>
                    )}
                </div>
            </div>
        </div>
    );
};

export default Profile;

import React from 'react';
import { Link } from 'react-router-dom';
import '../styles//Pages-CSS/HomePage.css';
import NavBar from '../components/NavBar';

const Home: React.FC = () => {

  const role = localStorage.getItem('userRoles');

  return (
    <div>
      <NavBar/>
      <div className="homepage-container">
        <div className="widget-container">
          {role !== "ATTORNEY" && (
            <Link to="/attourney" className="widget widget-large">
              <div className='widget-text'>
                <h2>Attorney</h2>
                <p>Check the latest updates on your case!</p>
              </div>
            </Link>
          )}
          {role !== "CLIENT" && (
            <Link to="/attorneysStats" className="widget widget-large">
              <div className='widget-text'>
                <h2>Stats</h2>
                <p>See who is the best!</p>
              </div>
            </Link>
          )}
          <Link to="/chat" className="widget widget-large">
            <div className='widget-text'>
              <h2>Chat</h2>
              <p>Hello, how areyou?</p>
            </div>
          </Link>
          <Link to="/create-appointment" className="widget widget-large">
            <div className='widget-text'>
              <h2>Set Up A Meeting</h2>
              <p>Need to talk? Set up a meeting!</p>
            </div>
          </Link>
          <Link to="/upload-files" className="widget widget-large">
            <div className='widget-text'>
              <h2>Upload Files</h2>
              <p>Upload your paper work here</p>
            </div>
          </Link>
          <Link to="/payments" className="widget widget-large">
            <div className='widget-text'>
              <h2>Paymets</h2>
              <p>Pay now?</p>
            </div>
          </Link>
          <Link to="/page6" className="widget widget-large">
            <div className='widget-text'>
              <h2>Transform files</h2>
              <p id='alex'>Make the data into file</p>
            </div>
          </Link>
        </div>
      </div>
    </div>

  );
};

export default Home;

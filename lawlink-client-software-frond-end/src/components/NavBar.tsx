import { useState, useEffect } from "react";
import { Link } from 'react-router-dom';
import '../styles//Components-CSS/navbar.css';
import '../../src/App.css';

function NavBar() {
    const isLoggedIn = localStorage.getItem('accessToken') != null;
    const role = localStorage.getItem('userRoles');

    const [navActive, setNavActive] = useState(false);

    const toggleNav = () => {
        setNavActive(!navActive);
    }

    const closeMenu = () => {
        setNavActive(false);
    }

    useEffect(() => {
        const handleResize = () => {
            if (window.innerWidth <= 500) {
                closeMenu();
            }
        }
        window.addEventListener("resize", handleResize);
        return () => {
            window.removeEventListener("resize", handleResize);
        }
    }, []);

    useEffect(() => {
        if (window.innerWidth <= 1200) {
            closeMenu();
        }
    }, []);

    return (
        <nav className={`navbar ${navActive ? "active" : ""}`}>
            <div>
                <a className={`nav__hamburger ${navActive ? "active" : ""}`} onClick={toggleNav}>
                    <span className="nav__hamburger__line">
                    </span>
                    <span className="nav__hamburger__line">
                    </span>
                    <span className="nav__hamburger__line">
                    </span>
                </a>
                <div className={`navbar--items ${navActive ? "active" : ""}`}>
                    <ul>
                        <li>
                            <Link
                                onClick={closeMenu}
                                to="/"
                                className="navbar--content"
                            >
                                Home
                            </Link>
                        </li>
                        {role !== "ATTORNEY" && (
                        <li>
                            <Link
                                onClick={closeMenu}
                                to="/case"
                                className="navbar--content"
                            >
                                Case
                            </Link>
                        </li>
                        )}
                        {role !== "CLIENT" && (
                            <li>
                                <Link
                                    onClick={closeMenu}
                                    to="/accounts"
                                    className="navbar--content"
                                >
                                    Accounts
                                </Link>
                            </li>
                        )}
                        {role !== "CLIENT" && (
                            <li>
                                <Link
                                    onClick={closeMenu}
                                    to="/create-account"
                                    className="navbar--content"
                                >
                                    Create Account
                                </Link>
                            </li>
                        )}
                        {role !== "CLIENT" && (
                            <li>
                                <Link
                                    onClick={closeMenu}
                                    to="/caseAttorney"
                                    className="navbar--content"
                                >
                                    Cases
                                </Link>
                            </li>
                        )}
                        {!isLoggedIn && (
                            <li>
                                <Link
                                    onClick={closeMenu}
                                    to="/login"
                                    className="navbar--content"
                                >
                                    Log In
                                </Link>
                            </li>
                        )}
                        {isLoggedIn && (
                            <li>
                                <Link
                                    onClick={closeMenu}
                                    to="/logout"
                                    className="navbar--content"
                                >
                                    Logout
                                </Link>
                            </li>
                        )}
                    </ul>
                </div>
            </div>
            <Link
                onClick={closeMenu}
                to="/profile"
                className="btn btn-outline-primary"
            >
                Profile
            </Link>
        </nav>
    );
}

export default NavBar;

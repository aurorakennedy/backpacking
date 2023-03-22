import React, { useState } from "react";
import "./navBarStyle.css";
import "./darkMode.css";
import profilIcon from "../img/profilIcon.png";
import { Link } from "react-router-dom";
import { LoggedInUser } from "./types";
import DarkMode from "./DarkMode";


type NavBarProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
};

const Nav = ({ setLoggedInUser }: NavBarProps) => {
    /**
     * Deletes the info about the logged in user in the browser when the user logges out.
     */
    const logOut: React.MouseEventHandler<HTMLParagraphElement> | undefined = () => {
        localStorage.setItem("loggedInUser", "null");
        window.location.reload();
        window.location.replace("/logIn");
    };

    return (
        <div id="navContainer">
            <nav id="nav">
                
                <DarkMode/>
                <Link to="/createItinerary">
                    <button id="addRoute"> + Add new itinerary</button>
                </Link>
                <h1 id="logo">BP-Advisor</h1>
                {/* <button id="profilIconButton">
                <img src={profilIcon} id='profilIcon' />
            </button> */}
                <Link to={`/SearchPage`}>
                    <button id="switchPages"> Search Page</button>
                </Link>
                <Link to="/homePage">
                    <button id="switchPages"> Home Page</button>
                </Link>
                <p id="logOutButton" onClick={logOut}>
                    {" "}
                    Log out{" "}
                </p>{" "}
                {/* Temporary logout button */}
            </nav>
        </div>
    );
};

export default Nav;

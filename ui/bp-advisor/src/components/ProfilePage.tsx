import React from "react";
import NavBar from "./NavBar";
import "./profilePageStyle.css";
import { LoggedInUser, User } from "./types";
import ItineraryListBox from "./ItineraryListBox";

type ProfilePageProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
};

/* const profilAvatar = require('../img/avatar_placeholder.png'); */


const ProfilePage = ({ setLoggedInUser, loggedInUser }: ProfilePageProps) => {
    return (
        <>
        <NavBar setLoggedInUser={setLoggedInUser} />
        <div id="profilePage">

            <div id='userInfo'  >
                <img id='profilePicture' src={require('../img/avatar_placeholder.png')}alt="Avatar" />
                <h2>{loggedInUser.username}'s Profile</h2>
                <p>Email: {loggedInUser.email}</p>

            </div>


            <div id="userItineraries">
                <ItineraryListBox
                    idOfWrappingDiv={"userItineraries"}
                    itinerariesBasedOn={"Your itineraries"}
                    loggedInUser={loggedInUser} />
            </div>

            <div id="likedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"likedItineraries"}
                        itinerariesBasedOn={"Liked itineraries"}
                        loggedInUser={loggedInUser}
                    />
                </div>
        </div></>
    ) 
};

export default ProfilePage;
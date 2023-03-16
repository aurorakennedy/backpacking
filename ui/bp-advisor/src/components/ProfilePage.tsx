import React from "react";
import NavBar from "./NavBar";
import { LoggedInUser } from "./types";
import ItineraryListBox from "./ItineraryListBox";

type ProfilePageProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
};

const ProfilePage = ({ setLoggedInUser, loggedInUser }: ProfilePageProps) => {
    return (
        <><NavBar setLoggedInUser={setLoggedInUser} />
        <div id="profilePage">

            <br></br>
            <br></br>
            <br></br>

            <div id="userItineraries">
                <ItineraryListBox
                    idOfWrappingDiv={"userItineraries"}
                    itinerariesBasedOn={"Your itineraries"}
                    loggedInUser={loggedInUser} />
            </div>
        </div></>
    ) 
};

export default ProfilePage;
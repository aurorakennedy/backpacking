import React from "react";
import "./homePageStyle.css";
import ItineraryListBox from "./ItineraryListBox";
import NavBar from "./NavBar";
import { LoggedInUser } from "./types";

type HomePageProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
};

const HomePage = ({ setLoggedInUser, loggedInUser }: HomePageProps) => {
    return (
        <>
            <NavBar setLoggedInUser={setLoggedInUser} />
            {/*  <ViewItineraryBox destinations={["Trondheim", "Bergen"]} /> */}
            <div id="homePage">
                {/* <div id="search">
                    <input
                        id="searchBar"
                        type="text"
                        placeholder="Where do you want to travel?" onChange={}
                    />
                </div> */}

                <br></br>
                <br></br>
                <br></br>

                <div id="userItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"userItineraries"}
                        itinerariesBasedOn={"Your itineraries"}
                        loggedInUser={loggedInUser}
                        
                        //what if i want nothing
                        keyword={""}
                    />
                </div>
                <div id="recommendedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"recommendedItineraries"}
                        itinerariesBasedOn={"Recommended itineraries"}
                        loggedInUser={loggedInUser}

                        //added this
                        keyword={""}
                    />
                </div>
                <div id="likedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"likedItineraries"}
                        itinerariesBasedOn={"Liked itineraries"}
                        loggedInUser={loggedInUser}

                        //added this
                        keyword={""}
                    />
                </div>
            </div>
        </>
    );
};

export default HomePage;

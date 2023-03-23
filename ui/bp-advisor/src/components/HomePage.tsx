import React from "react";
import { Link } from "react-router-dom";
import "./homePageStyle.css";
import ItineraryListBox from "./ItineraryListBox";
import NavBar from "./NavBar";
import { LoggedInUser } from "./types";
import SearchPage from "./SearchPage";

type HomePageProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
};

function goToSearchPageWithSearch() {
    let keyword = (document.getElementById("searchBar") as HTMLInputElement).value;
    console.log(keyword);
    window.location.replace(`/searchPage/${keyword}`);
}

const HomePage = ({ setLoggedInUser, loggedInUser }: HomePageProps) => {
    const handleKeyDown = (event: { key: string }) => {
        // Check if the "Enter" key was pressed
        if (event.key === "Enter") {
            goToSearchPageWithSearch();
        }
    };

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

                <div id="search">
                    <input
                        id="searchBar"
                        type="text"
                        placeholder="Type here to search for an itinerary" /* onChange={} */
                        onKeyDown={handleKeyDown}
                    />
                    <button id="searchButton" onClick={goToSearchPageWithSearch} type="button">
                        Search
                    </button>
                </div>

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
                <div id="topListEurope">
                    <ItineraryListBox
                        idOfWrappingDiv={"topListEurope"}
                        itinerariesBasedOn={"Europe"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>
                <div id="topListAsia">
                    <ItineraryListBox
                        idOfWrappingDiv={"topListAsia"}
                        itinerariesBasedOn={"Asia"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>
                <div id="topListNorthAmerica">
                    <ItineraryListBox
                        idOfWrappingDiv={"topListNorthAmerica"}
                        itinerariesBasedOn={"North America"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>
                <div id="topListSouthAmerica">
                    <ItineraryListBox
                        idOfWrappingDiv={"topListSouthAmerica"}
                        itinerariesBasedOn={"South America"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>
                <div id="topListAfrica">
                    <ItineraryListBox
                        idOfWrappingDiv={"topListAfrica"}
                        itinerariesBasedOn={"Africa"}
                        loggedInUser={loggedInUser}
                        keyword={""}
                    />
                </div>
                <div id="topListOceania">
                    <ItineraryListBox
                        idOfWrappingDiv={"topListOceania"}
                        itinerariesBasedOn={"Oceania"}
                        loggedInUser={loggedInUser}
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
                <div id="ratedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"ratedItineraries"}
                        itinerariesBasedOn={"Rated itineraries"}
                        loggedInUser={loggedInUser}
                        keyword={""}
           
                    />
                </div>
                <div id="allItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"allItineraries"}
                        itinerariesBasedOn={"All itineraries"}
                        loggedInUser={loggedInUser}
                        keyword={""}
             
                    />
                </div>
            </div>
        </>
    );
};

export default HomePage;

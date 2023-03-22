import React, { FormEvent, MouseEventHandler, useEffect, useLayoutEffect, useState } from "react";
import NavBar from "./NavBar";
import { Link, useParams } from "react-router-dom";
import { Itinerary, LoggedInUser, User } from "./types";
import httpRequests from "./httpRequests";
import Header from "./Header";
import ItineraryListBox from "./ItineraryListBox";
import "./homePageStyle.css";
import { createRoot } from "react-dom/client";
import "./navBarStyle.css";

type searchPage = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
    
    //set keyword somehow
    //keywordInputValue:
};

function goToSearchPageWithSearch() {
    let keyword = (document.getElementById("searchBar") as HTMLInputElement).value;
    console.log(keyword);
    window.location.replace(`/searchPage/${keyword}`);
}

const SearchPageBox = ({ loggedInUser, setLoggedInUser }: searchPage) => {
    const { keyword } = useParams();

    const handleKeyDown = (event: { key: string; preventDefault: () => void }) => {
        // Check if the "Enter" key was pressed
        if (event.key === 'Enter') {
            goToSearchPageWithSearch();
            event.preventDefault();
        }
    };

    return (
        <>
            <NavBar setLoggedInUser={setLoggedInUser} />
                <div id="searchpage">
                    <br></br>
                    <br></br>
                    <br></br>
                    <div id="search">
                        <input id="searchBar" type="text" 
                            defaultValue={keyword}
                            placeholder={"Type here to search for an itinerary"} /* onChange={} */ 
                            onKeyDown={handleKeyDown}/>
                        <button
                            id="searchButton"
                            onClick={goToSearchPageWithSearch}
                            type="button"
                        >
                            Search
                        </button>
                    </div>
                    <h2>Your search results: </h2>
                    <div id="searchItinerariesWrapped"></div>
                    <div id="searchedItineraries">
                        {keyword != undefined && (
                            <ItineraryListBox
                                idOfWrappingDiv={"searchedItineraries"}
                                itinerariesBasedOn={"Searched itineraries"}
                                loggedInUser={loggedInUser}
                                keyword={keyword as string}
                            />
                        )}
                    </div>
                </div>
        </>
    );
};


export default SearchPageBox;
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

                    <div className="dropdown">
                        <button className="dropbtn">Continent</button>
                        <div className="dropdown-content">
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Europe')} type="button">Europe</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Asia')} type="button">Asia</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Africa')} type="button">Africa</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'North America')} type="button">N. America</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'South America')} type="button">S. America</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Oceania')} type="button">Oceania</button>
                        </div>
                    </div>

                    <div className="dropdown">
                        <button className="dropbtn">Price</button>
                        <div className="dropdown-content">
                            <button id="button" onClick={() => enterKeywordPrice(loggedInUser, '(cost >= 0 AND cost <= 500)')} type="button">Low</button>
                            <button id="button" onClick={() => enterKeywordPrice(loggedInUser, '(cost >= 501 AND cost <= 2000)')} type="button">Medium</button>
                            <button id="button" onClick={() => enterKeywordPrice(loggedInUser, '(cost >= 2001 AND cost <= 10000)')} type="button">High</button>
                        </div>
                    </div>

                    <div className="dropdown">
                        <button className="dropbtn">Duration</button>
                        <div className="dropdown-content">
                            <button id="button" onClick={() => enterKeywordPrice(loggedInUser, '(estimated_time >= 0 AND estimated_time <= 8)')} type="button">Under a Week</button>
                            <button id="button" onClick={() => enterKeywordPrice(loggedInUser, '(estimated_time >= 9 AND estimated_time <= 30)')} type="button">Under a Month</button>
                            <button id="button" onClick={() => enterKeywordPrice(loggedInUser, '(estimated_time >= 31 AND estimated_time <= 100)')} type="button">Over a Month</button>
                        </div>
                    </div>

                
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
                        {keyword != undefined  && (
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

async function enterKeywordInfo(loggedInUser: LoggedInUser) {
    console.log("Function called");
    const keywordInputValue: string = (document.getElementById("searchBar") as HTMLInputElement)
        .value;

    if (keywordInputValue.includes(".") && keywordInputValue.includes("@")) {
        alert("The keyword is invalid.");
        return;
    }
    //window.location.reload();
    document.getElementById("searchedItineraries")?.remove();
    try {
        console.log(keywordInputValue);
        const promise: Promise<Itinerary[]> = httpRequests.searchByKeyword(keywordInputValue);
        promise.then((itineraries: Itinerary[]) => {
            let searchItinerariesWrappeddiv: HTMLDivElement = document.getElementById(
                "searchItinerariesWrapped"
            ) as HTMLDivElement;
            let searchedItinerariesdiv = document.createElement("div");
            searchedItinerariesdiv.id = "searchedItineraries";
            let searchedItinerariesListBox = (
                <ItineraryListBox
                    idOfWrappingDiv={"searchedItineraries"}
                    itinerariesBasedOn={"Searched itineraries"}
                    loggedInUser={loggedInUser}
                    keyword={keywordInputValue}

        
                />
            );
            createRoot(searchedItinerariesdiv).render(searchedItinerariesListBox);
            searchItinerariesWrappeddiv.appendChild(searchedItinerariesdiv);
        });
    } catch (error) {
        //TODO: Error handling
        alert("There was an error when searching for an itinerary, please try again.");
    }
}
async function filter_advancedSearch(loggedInUser: LoggedInUser, keywordInputValue: string) {
    console.log("Function called");
   // const buttonElement = document.querySelector(`button[name="buttonTest"]`);

   // if (buttonElement instanceof HTMLInputElement) {
   //     const keywordInputValue = buttonElement.value;
    //window.location.reload();
        document.getElementById("searchedItineraries")?.remove();
        try {
            console.log(keywordInputValue);
            const promise: Promise<Itinerary[]> = httpRequests.searchByKeyword(keywordInputValue);
            promise.then((itineraries: Itinerary[]) => {
                let searchItinerariesWrappeddiv: HTMLDivElement = document.getElementById(
                    "searchItinerariesWrapped"
                ) as HTMLDivElement;
                let searchedItinerariesdiv = document.createElement("div");
                searchedItinerariesdiv.id = "searchedItineraries";
                let searchedItinerariesListBox = (
                    <ItineraryListBox
                        idOfWrappingDiv={"searchedItineraries"}
                        itinerariesBasedOn={"Searched itineraries"}
                        loggedInUser={loggedInUser}
                        keyword={keywordInputValue}
                    />
                );
                createRoot(searchedItinerariesdiv).render(searchedItinerariesListBox);
                searchItinerariesWrappeddiv.appendChild(searchedItinerariesdiv);
            });
        } catch (error) {
            //TODO: Error handling
            alert("There was an error when searching for an itinerary, please try again.");
        }
    }

    async function enterKeywordPrice(loggedInUser: LoggedInUser, keywordInputValue: string) {
        console.log("Function called");
        document.getElementById("searchedItineraries")?.remove();
        try {
            console.log(keywordInputValue);
            const promise: Promise<Itinerary[]> = httpRequests.searchByPrice(keywordInputValue);
            promise.then((itineraries: Itinerary[]) => {
                
                let searchItinerariesWrappeddiv: HTMLDivElement = document.getElementById(
                    "searchItinerariesWrapped"
                ) as HTMLDivElement;
                let searchedItinerariesdiv = document.createElement("div");
                searchedItinerariesdiv.id = "searchedItineraries";
                
                let searchedItinerariesListBox = (
                    <ItineraryListBox
                        idOfWrappingDiv={"searchedItineraries"}
                        itinerariesBasedOn={"Searched itineraries Price"}
                        loggedInUser={loggedInUser}
                        keyword={keywordInputValue}
                    />
                );
                createRoot(searchedItinerariesdiv).render(searchedItinerariesListBox);
                searchItinerariesWrappeddiv.appendChild(searchedItinerariesdiv);
                
            });
        } catch (error) {
            alert("There was an error when searching for an itinerary, please try again.");
        }}


export default SearchPageBox;
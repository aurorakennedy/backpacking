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

const SearchPageBox = ({ loggedInUser, setLoggedInUser }: searchPage) => {
    const { keyword } = useParams();
    return (
        <>
            <NavBar setLoggedInUser={setLoggedInUser} />
            <form>
                <div id="searchpage">
                    <br></br>
                    <br></br>
                    <br></br>
                    <div id="search">
                        <input
                            id="searchBar"
                            type="text"
                            placeholder="Type here to search for an itinerary"
                        />
                    </div>

                    <div className="dropdown">
                        <button className="dropbtn">Continent</button>
                        <div className="dropdown-content">
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Europe')} type="button">Europe</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Asia')} type="button">Asia</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Africa')} type="button">Africa</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'North America')} type="button">N. America</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'South America')} type="button">S. America</button>
                            <button id="button" onClick={() => filter_advancedSearch(loggedInUser, 'Australia')} type="button">Australia</button>
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

                
                    <button
                        id="searchButton"
                        onClick={(async) => enterKeywordInfo(loggedInUser)}
                        type="button"
                    >
                        Search
                    </button>
                    
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
            </form>
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

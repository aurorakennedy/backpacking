import React, { FormEvent, useEffect, useState } from 'react';
import NavBar from './NavBar';
import { Link } from 'react-router-dom';
import { Itinerary, LoggedInUser, User } from './types';
import httpRequests from './httpRequests';
import Header from './Header';

import ItineraryListBox from "./ItineraryListBox";


type searchPage = {
  setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
  loggedInUser: LoggedInUser;
}


const SearchPageBox = ({ 
    loggedInUser,
    setLoggedInUser, }: searchPage) => {

    return (
    <>
        <NavBar setLoggedInUser={setLoggedInUser} />
        <form onSubmit={enterKeywordInfo}>
        <div id='searchpage'>
        

            <h2>Search for an itinerary</h2>
            <div id='search'>
                    <input id='searchBar' type='text' placeholder='Type here to search for an itinerary' /* onChange={} */ />
            </div>
            <button id='searchButton' onClick={enterKeywordInfo} type='button'> Search now</button>
            <h2>Your search results: </h2>

            <div id="searchedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"searchedItineraries"}
                        itinerariesBasedOn={"Searched itineraries"}
                        loggedInUser={loggedInUser}
                    />
              </div>

        </div>
        </form>
    </>
)

    }

async function enterKeywordInfo(): Promise<React.MouseEventHandler<HTMLButtonElement> | any> {
  const keywordInputValue: string = (document.getElementById('keywordInput') as HTMLInputElement).value;

  if ((keywordInputValue.includes(".") && keywordInputValue.includes("@"))) {
      alert('The keyword is invalid.')
      return;
  }
   
    try {
      console.log(keywordInputValue);
      const promise: Promise<Itinerary[]> = httpRequests.searchByKeyword(keywordInputValue);
      promise.then((itineraries: Itinerary[]) => {
      ItineraryListBox.displayItineraries(itinerariesOfUser, itinerariesBasedOn);
  });
    } catch (error) {
    //TODO: Error handling
    }
  
  }


export default SearchPageBox;
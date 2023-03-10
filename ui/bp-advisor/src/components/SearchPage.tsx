import React, { FormEvent, MouseEventHandler, useEffect, useLayoutEffect, useState } from 'react';
import NavBar from './NavBar';
import { Link } from 'react-router-dom';
import { Itinerary, LoggedInUser, User } from './types';
import httpRequests from './httpRequests';
import Header from './Header';
import ItineraryListBox from "./ItineraryListBox";
import "./homePageStyle.css";
import { createRoot } from 'react-dom/client';



type searchPage = {
  setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
  loggedInUser: LoggedInUser;

  //set keyword somehow
  //keywordInputValue: 
}



const SearchPageBox = ({ 
    loggedInUser,
    setLoggedInUser, }: searchPage) => {

    return (
    <>
        <NavBar setLoggedInUser={setLoggedInUser} />
        <form>
        <div id='searchpage'>
        

            <h2>Search for an itinerary</h2>
            <div id='search'>
                    <input id='searchBar' type='text' placeholder='Type here to search for an itinerary' /* onChange={} */ />
            </div>
            <button id='searchButton' onClick={enterKeywordInfo(loggedInUser)} type='button'> Search now</button>
            <h2>Your search results: </h2>

            {/* <div id="searchedItineraries">
                    <ItineraryListBox
                        idOfWrappingDiv={"searchedItineraries"}
                        itinerariesBasedOn={"Searched itineraries"}
                        loggedInUser={loggedInUser}

                        //keyword ={"string"}
                        keyword={"keywordInputValue"}
                    />
            </div> */}
            <div id='searchItinerariesWrapped'>

              </div>

        </div>
        </form>
    </>
)

    }

async function enterKeywordInfo(loggedInUser: LoggedInUser): MouseEventHandler<HTMLButtonElement> | Promise<any> {
  const keywordInputValue: string = (document.getElementById('keywordInput') as HTMLInputElement).value;

  if ((keywordInputValue.includes(".") && keywordInputValue.includes("@"))) {
      alert('The keyword is invalid.')
      return;
  }
   
    try {
      console.log(keywordInputValue);
      const promise: Promise<Itinerary[]> = httpRequests.searchByKeyword(keywordInputValue);
      promise.then((itineraries: Itinerary[]) => {

        let searchItinerariesWrappeddiv: HTMLDivElement = document.getElementById('searchItinerariesWrapped') as HTMLDivElement;
        let searchedItinerariesdiv = document.createElement('div');
        searchedItinerariesdiv.id = 'searchedItineraries';
        let searchedItinerariesListBox = (<ItineraryListBox idOfWrappingDiv={'searchedItineraries'} itinerariesBasedOn={'Searched itineraries'} loggedInUser={loggedInUser} keyword={keywordInputValue}/>);
        createRoot(searchedItinerariesdiv).render(searchedItinerariesListBox);
        searchItinerariesWrappeddiv.appendChild(searchedItinerariesdiv);
      
        //ItineraryListBox.displayItineraries(itinerariesOfUser, itinerariesBasedOn);
  });
    } catch (error) {
    //TODO: Error handling
    }
  
  }


export default SearchPageBox;
import React from 'react';
import NavBar from './NavBar';
import { Link } from 'react-router-dom';
import { LoggedInUser, User } from './types';
import httpRequests from './httpRequests';
import Header from './Header';

type searchPage = {
  setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>
}

const SearchPageBox = ({ setLoggedInUser }: searchPage) => {
  return (
    <>
        <NavBar setLoggedInUser={setLoggedInUser} />
        <div id='userfind'>
            <h2>Testing</h2>
            <div id='search'>
                    <input id='searchBar' type='text' placeholder='Where do you want to travel?' /* onChange={} */ />
            </div>
            <h2>Your search results: </h2>
        </div>
    </>
)

}

export default SearchPageBox;
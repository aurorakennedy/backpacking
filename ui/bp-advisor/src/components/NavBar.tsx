import React, { useState } from 'react';
import './navBarStyle.css';
import profilIcon from '../img/profilIcon.png';
import CreateNewItineraryForm from './CreateNewItineraryForm';
import { Link } from 'react-router-dom';


const Nav: React.FC<{}> = () => {

    return (
        <nav id='nav'>
            <h1 id='logo'>BP-Advisor</h1>

            <Link to='/createItinerary'>
                <button id='addRoute'> + Add  new itinerary</button>
            </Link>

            <button id="profilIconButton">
                <img src={profilIcon} id='profilIcon' />
            </button>

        </nav>
    );
}

export default Nav;
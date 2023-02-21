import React from 'react';
import './navBarStyle.css';
import profilIcon from '../img/profilIcon.png';


const Nav: React.FC<{}> = () => {
    return (
        <nav id='nav'>
            <h1 id='logo'>BP-Advisor</h1>

            <button id ='addRoute'> + Add route</button>

            <button id = "profilIconButton">
                <img src={profilIcon} id='profilIcon' />
            </button>
        </nav>
    );
}

export default Nav;
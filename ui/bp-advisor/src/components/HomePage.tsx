import React from 'react';
import './homePageStyle.css'
import ItineraryListBox from './ItineraryListBox';
import NavBar from './NavBar';
import { LoggedInUser } from './types';

type HomePageProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>
}

const HomePage = ({ setLoggedInUser }: HomePageProps) => {

    return (
        <>
            <NavBar setLoggedInUser={setLoggedInUser} />
            {/*  <ViewItineraryBox destinations={["Trondheim", "Bergen"]} /> */}
            <div id='homePage'>

                <div id='search'>
                    <input id='searchBar' type='text' placeholder='Where do you want to travel?' /* onChange={} */ />
                </div>

                <h2>Your routes</h2>

                <ItineraryListBox />

            </div>
        </>
    )


}

export default HomePage;
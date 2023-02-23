import React from 'react';
import './homePageStyle.css'
import NavBar from './NavBar';
import TravelRoute from './TravelRoute';

const HomePage = () => {

    return (
        <>
            <NavBar />

            <div id='homePage'>

                <div id='search'>
                    <input id='searchBar' type='text' placeholder='Where do you want to travel?' /* onChange={} */ />
                </div>

                <h2>Your routes</h2>

                <div id='travelRouteDiv'>

                    <a href="">
                        <TravelRoute
                            title='Backpacking South America'
                            description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                            duration='40 days'
                            price='$3200' /* image={''}      */ />
                    </a>

                    <a href="">
                        <TravelRoute
                            title='Interrail in northern Europe'
                            description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                            duration='30 days'
                            price='$2700' /* image={''}   */ />
                    </a>
                    <a href="">
                        <TravelRoute
                            title='Bikepacking in Africa'
                            description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                            duration='40 days'
                            price='$1900' /* image={''}      */ />
                    </a>

                    <a href="">
                        <TravelRoute
                            title='America coast to coast'
                            description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                            duration='50 days'
                            price='$3300' /* image={''}   */ />
                    </a>



                </div>

            </div>
        </>
    )


}

export default HomePage;
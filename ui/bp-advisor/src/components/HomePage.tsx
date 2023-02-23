import React from 'react';
import './homePageStyle.css'
import NavBar from './NavBar';
import TravelRoute from './TravelRoute';

const HomePage = () => {

    return(
        <>
        <NavBar/>

        <div id= 'homePage'>

            <div id='search'>
                <input id='searchBar' type='text' placeholder='Where do you want to travel?' /* onChange={} */ />
            </div>
            
            <h2>New Routes</h2>

            <div id='travelRouteDiv'>

                <a href="">
                    <TravelRoute
                            title='Route 1'
                            description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                            duration='5 days'
                            price='$500' /* image={''}      */       />
                </a>

                <a href="">
                    <TravelRoute
                            title='Route 2'
                            description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                            duration='7 days'
                            price='$700' /* image={''}   */          />
                </a>
                <a href="">
                    <TravelRoute
                            title='Route 1'
                            description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                            duration='5 days'
                            price='$500' /* image={''}      */       />
                </a>

                <a href="">
                    <TravelRoute
                            title='Route 2'
                            description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                            duration='7 days'
                            price='$700' /* image={''}   */          />
                </a>
            
            
        
            </div>

            <h2>Recomende Routes</h2>

            <div id='travelRouteDiv'>

                <a href="">
                    <TravelRoute
                            title='Route 1'
                            description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                            duration='5 days'
                            price='$500' /* image={''}      */       />
                </a>

                <a href="">
                    <TravelRoute
                            title='Route 2'
                            description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                            duration='7 days'
                            price='$700' /* image={''}   */          />
                </a>
                <a href="">
                    <TravelRoute
                            title='Route 1'
                            description='Lorem ipsum dolor sit amet, consectetur adipiscing elit.'
                            duration='5 days'
                            price='$500' /* image={''}      */       />
                </a>

                <a href="">
                    <TravelRoute
                            title='Route 2'
                            description='Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium.'
                            duration='7 days'
                            price='$700' /* image={''}   */          />
                </a>
            
            
        
            </div>  

        </div>
        </>
    )


}

export default HomePage;
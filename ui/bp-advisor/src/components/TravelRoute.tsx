import React, { FC } from 'react';
import './travelRouteStyle.css'

interface TravelRouteProps {
    title: string;
    description: string;
    /* image: string; */
    duration: string;
    price: string;
  }
  
  const TravelRoute: FC<TravelRouteProps> = ({ title, description, /* image, */ duration, price }) => {
    return (
      <div id="travelRoute">
        {/* <img src={image} alt={title} /> */}
        <h2 id='routeName'>{title}</h2>
        <p id='routeDescription'>{description}</p>
        <p id='routeDuration'>{duration}</p>
        <p id='routePrice'>{price}</p>

      </div>
    );
  };
  
export default TravelRoute;

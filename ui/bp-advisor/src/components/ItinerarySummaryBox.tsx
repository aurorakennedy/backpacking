import React, { FC } from "react";
import "./itinerarySummaryBoxStyle.css";

interface TravelRouteProps {
    title: string;
    description: string;
    estimatedTime: string;
    cost: string;
}

const ItinerarySummaryBox: FC<TravelRouteProps> = ({
    title,
    description,
    estimatedTime,
    cost,
}) => {
    return (
        <div id="itinerarySummaryBoxDiv">
            {/* <img src={image} alt={title} /> */}
            <h2 id="routeName">{title}</h2>
            <div id="routeDescription">{description}</div>
            <p id="routeDuration">
                <b>{estimatedTime} days</b>
            </p>
            <p id="routePrice">
                $ <b>{cost}</b>
            </p>
        </div>
    );
};

export default ItinerarySummaryBox;

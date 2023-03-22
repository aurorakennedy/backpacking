import React, { FC } from "react";
import "./itinerarySummaryBoxStyle.css";

interface TravelRouteProps {
  title: string;
  image: HTMLImageElement | null;
  description: string;
  estimatedTime: string;
  cost: string;
}

const ItinerarySummaryBox: FC<TravelRouteProps> = ({
  title,
  image,
  description,
  estimatedTime,
  cost,
}) => {
  return (
    <div className="itinerarySummaryBox">
      <h2 className="itinerarySummaryBox__title">{title}</h2>
      <div className="itinerarySummaryBox__image-container">
        {image ? (
          <img
            className="itinerarySummaryBox__image"
            src={image.src}
            alt={title}
          />
        ) : (
          <div className="itinerarySummaryBox__description">{description}</div>
        )}
      </div>
      <div className="itinerarySummaryBox__details">
        <p className="itinerarySummaryBox__duration">
          <b>{estimatedTime} days</b>
        </p>
        <p className="itinerarySummaryBox__price">
          $<b>{cost}</b>
        </p>
      </div>
    </div>
  );
};

export default ItinerarySummaryBox;

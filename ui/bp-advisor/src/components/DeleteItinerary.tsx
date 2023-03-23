import React, { useState } from "react";
import httpRequests from "./httpRequests";
import "./itineraryListBoxStyle.css";


  interface deleteItineraryProps {
    itineraryID: number;
  };
  
/**
 * Component to delete an itinerary, deltes on det itineraryID 
 * @param itineraryID takes in the itineraryID as a number 
 * @returns a delete button, that on click deletes a itinarary
 */
 const DeleteItinerary = ({ itineraryID }: deleteItineraryProps) => {
    const [loading , setLoading ] = useState<boolean>(false);
  
    const handleDelete = async () => {
      
      const confirmed = window.confirm("Are you sure you want to delete this itinerary?");
      if(confirmed) {
        setLoading(true);
        try {
            // Send a request to delete the itinerary
            await httpRequests.deleteItinerary(itineraryID);
            // Reloads window after deletion 
            window.location.replace(`/homePage`); 
            } catch (error) {
            console.error(error);
            alert("Deletion unsuccessfull"); }
        setLoading(false);
      }
    };
  
    return (
      <div>
          <button id='itineraryDeleteButton' onClick={handleDelete} disabled={loading}>
          {loading ? "Deleting..." : "Delete"}
        </button>
      </div>
    );
  };
  
  export default DeleteItinerary;


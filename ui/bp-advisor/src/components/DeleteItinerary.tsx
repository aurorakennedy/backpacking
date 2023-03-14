import React, { useState } from "react";
import httpRequests from "./httpRequests";


  interface deleteItineraryProps {
    emailOfAuthor: string;
    titleOfItinerary: string;
  };
  
/**
 * generel linje 
 * @param itinerary hvilken type - hva det er 
 * @returns hva den retunerer 
 */
 const DeleteItinerary = ({ emailOfAuthor, titleOfItinerary }: deleteItineraryProps) => {
    const [loading , setLoading ] = useState<boolean>(false);
  
    const handleDelete = async () => {
      /* console.log("Deletion initioated"); */
      const confirmed = window.confirm("Are you sure you want to delete this itinerary?");
      if(confirmed) {
        setLoading(true);
        try {
            // Send a request to delete the itinerary
            await httpRequests.deleteItinerary(emailOfAuthor, titleOfItinerary);
            // Call the onDelete callback to update the UI
            // Navigate back to the homepage
            console.log("Delete works in ITINERARY")
            window.location.reload(); 
            } catch (error) {
            console.error(error);
            alert("Deletion unsuccessfull"); }
        setLoading(false);
      }
    };
  
    return (
      <div>
{/*         <p>Are you sure you want to delete this itinerary?</p>
 */}        <button id='slettKnapp' onClick={handleDelete} disabled={loading}>
          {loading ? "Deleting..." : "Delete"}
        </button>
      </div>
    );
  };
  
  export default DeleteItinerary;


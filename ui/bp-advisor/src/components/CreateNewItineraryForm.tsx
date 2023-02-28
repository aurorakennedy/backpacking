import './createNewItineraryFormStyle.css'
import { Destination, ItineraryDestinationJoined, LoggedInUser } from './types';
import httpRequests from './httpRequests';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import Nav from './NavBar';


type CreateNewItineraryFormProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>
}


/**
 * Component for BP-Advisor to create new itineraries, including relevant input fields, 
 * a submit button, and functions for sending the data to the back end.
 * 
 * @returns HTML-code for a BP-Advisor login box and functions to support login.
 */
const CreateNewItineraryForm = ({ setLoggedInUser }: CreateNewItineraryFormProps) => {

    // Saves a list of the destinations added
    const [destinations] = useState<Destination[]>([]);

    // Adds a destination to the state
    function addDestinationToState(destinationName: string, countryName: string): void {
        destinations.push({ id: 0, name: destinationName, area: "", country: countryName });
    }

    /**
     * Adds a destination to the state and to the DOM
     * @returns void, if destination inputs are not provided
     */
    function handleAddDestination(): React.MouseEventHandler<HTMLButtonElement> | void {

        let destinationsAsHTML = document.getElementById("destinationsAsHTML") as HTMLDivElement;
        let destinationName: string = (document.getElementById('destinationNameInput') as HTMLInputElement).value;
        let countryName: string = (document.getElementById('countryInput') as HTMLInputElement).value;

        if (destinationName.length < 1 || countryName.length < 1) {
            alert("The destination needs both a name and a country");
            return;
        }

        addDestinationToState(destinationName, countryName);

        let p = document.createElement('p');
        p.textContent = destinationName + " - " + countryName;
        destinationsAsHTML.appendChild(p);

        console.log(destinations);
    }

    /**
     * Gets the information form the input fields and sends it as http-requests to the backend for saving
     * in the database.
     */
    function submitDestinationInfo(): React.MouseEventHandler<HTMLButtonElement> | any {

        const titleInputValue: string = (document.getElementById('titleInput') as HTMLInputElement).value;
        const timeInputValue: string = (document.getElementById('estimatedTimeInput') as HTMLInputElement).value;
        const priceInputValue: string = (document.getElementById('estimatedPriceInput') as HTMLInputElement).value;
        const descriptionInputValue: string = (document.getElementById('descriptionInput') as HTMLInputElement).value;

        if (titleInputValue.length < 1 || timeInputValue.length < 1 || priceInputValue.length < 1 || descriptionInputValue.length < 1) {
            alert('All fields must be filled out')
            return;
        }

        if (destinations.length < 1) {
            alert('You must have added at least one destination')
            return;
        }

        const createdId: number = Math.random();

        const itineraryDestinations: ItineraryDestinationJoined[] = [];

        let orderOfDestination = 0;

        destinations.forEach(destination => {

            itineraryDestinations.push({
                itineraryID: createdId,
                destinationID: 0, // Must be set in backend when checked with database
                name: destination.name,
                area: "",
                country: destination.country,
                continent: "",
                order: orderOfDestination
            });
            orderOfDestination++;
        });

        try {
            httpRequests.addItinerary({
                id: createdId,
                name: titleInputValue,
                cost: +priceInputValue,
                distance: 0,
                duration: +timeInputValue,
                description: descriptionInputValue
            });
            httpRequests.addItineraryDestinationsJoined(itineraryDestinations);
            window.location.reload();
            window.location.replace('/homePage');
        } catch (error) {
            alert("There was an error when trying to add the route, please try again.");
        }

    }

    return (
        <>
            <Nav setLoggedInUser={setLoggedInUser} />
            <div id='newRouteBox'>
                <form>
                    <h2> Add a backpacking itinerary</h2>
                    <Link id='cancelButton' to='/homePage'>
                        Cancel
                    </Link>
                    <br></br>
                    <label className='newRouteLabel' id='titleInputLabel'>Title</label>
                    <input className='newRouteInput' id='titleInput' type="input" placeholder='   ...'></input>
                    <br></br>
                    <label className='newRouteLabel' id='estimatedTimeInputLabel'>Estimated time (in days)</label>
                    <input className='newRouteInput' id='estimatedTimeInput' type='input' placeholder='   ...'></input>
                    <br></br>
                    <label className='newRouteLabel' id='estimatedPriceInputLabel'>Estimated price (in dollars)</label>
                    <input className='newRouteInput' id='estimatedPriceInput' type='input' placeholder='   ...'></input>
                    <br></br>
                    <label className='newRouteLabel' id='descriptionInputLabel'> Description </label>
                    <textarea className='newRouteInput' id='descriptionInput' placeholder='   ...'></textarea>
                    <br></br>
                    <br></br>
                    <label className='newRouteLabel' id='destinationInputLabel'>Add destination to itinerary</label>
                    <br></br>
                    <div id='destinationInputBox'>
                        <div>
                            <label className='newRouteLabel' id='destinationNameInputLabel'>Destination name</label>
                            <input className='newRouteInput' id='destinationNameInput' type='input' placeholder='   ...'></input>
                        </div>
                        <div>
                            <label className='newRouteLabel' id='countryInputLabel'>Country of destination</label>
                            <input className='newRouteInput' id='countryInput' type='input' placeholder='   ...'></input>
                        </div>
                    </div>
                    <br></br>
                    <button id='addDestinationButton' onClick={handleAddDestination} type='button'> Add destination</button>
                    <br></br>
                    <br></br>
                    <br></br>
                    <div id='destinationsAsHTML'>
                        <p id='destinationsAsHTMLTitle'>Destinations added:</p>
                        <br></br>
                        <br></br>

                    </div>
                    <button id='submitItineraryButton' onClick={submitDestinationInfo} type='button'> Add route</button>
                </form>
            </div>
        </>)
}

export default CreateNewItineraryForm;

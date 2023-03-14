import "./ItineraryFormStyle.css";
import { Destination, LoggedInUser } from "./types";
import httpRequests from "./httpRequests";
import React, { useState } from "react";
import { Link, useParams } from "react-router-dom";
import Nav from "./NavBar";
import { count } from "console";

type ItineraryFormProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
};

/**
 * Component for BP-Advisor to create new itineraries, including relevant input fields,
 * a submit button, and functions for sending the data to the back end.
 *
 * @returns HTML-code for a BP-Advisor login box and functions to support login.
 */
const ItineraryForm = ({ loggedInUser, setLoggedInUser }: ItineraryFormProps) => {
    const { itineraryId } = useParams();
    const { title } = useParams();
    const { time } = useParams();
    const { cost } = useParams();
    const { desc } = useParams();

    const [editMode] = useState(title !== undefined);

    // Saves a list of the destinations added
    const [destinations] = useState<Destination[]>([]);

    /**
     * Adds a destination type from the state
     *
     * @param destinationName string, the name of the destination to be removed
     * @param countryName string, the name of the country of the destination to be removed
     */
    function addDestinationToState(destinationName: string, countryName: string): void {
        destinations.push({
            destinationName: destinationName,
            country: countryName,
            description: "",
        });
    }

    /**
     * Removes a destination type from the state
     *
     * @param destinationName string, the name of the destination to be removed
     * @param countryName string, the name of the country of the destination to be removed
     */
    function removeDestinationFromState(destinationName: string, countryName: string): void {
        const index = destinations.findIndex(
            (destination) =>
                destination.destinationName === destinationName &&
                destination.country === countryName
        );
        if (index !== -1) {
            destinations.splice(index, 1);
        }
    }

    /**
     * Adds a destination to the state and to the DOM
     * @returns void, if destination inputs are not provided
     */
    function handleAddDestination(): React.MouseEventHandler<HTMLButtonElement> | void {
        let destinationsAsHTML = document.getElementById("destinationsAsHTML") as HTMLDivElement;
        let destinationName: string = (
            document.getElementById("destinationNameInput") as HTMLInputElement
        ).value;
        let countryName: string = (document.getElementById("countryInput") as HTMLInputElement)
            .value;

        if (destinationName.length < 1 || countryName.length < 1) {
            alert("The destination needs both a name and a country");
            return;
        }

        addDestinationToState(destinationName, countryName);

        let div = document.createElement("div");
        div.classList.add("destinationCountryPair");

        let p = document.createElement("p");
        p.textContent = destinationName + " - " + countryName;

        let removeButton = document.createElement("button");
        removeButton.innerHTML = "X";
        removeButton.type = "button";
        removeButton.addEventListener("click", (event) => {
            removeDestinationFromState(destinationName, countryName);
            const button = event.target as HTMLElement;
            const parentDiv = button.closest("div");
            if (parentDiv) {
                parentDiv.remove();
            }
        });

        div.appendChild(p);
        div.appendChild(removeButton);

        destinationsAsHTML.appendChild(div);

        console.log(destinations);
    }

    /**
     * Gets the information form the input fields and sends it as http-requests to the backend for saving
     * in the database.
     */
    function submitDestinationInfo(): React.MouseEventHandler<HTMLButtonElement> | any {
        const titleInputValue: string = (document.getElementById("titleInput") as HTMLInputElement)
            .value;
        const timeInputValue: string = (
            document.getElementById("estimatedTimeInput") as HTMLInputElement
        ).value;
        const priceInputValue: string = (
            document.getElementById("estimatedPriceInput") as HTMLInputElement
        ).value;
        const descriptionInputValue: string = (
            document.getElementById("descriptionInput") as HTMLInputElement
        ).value;

        if (
            titleInputValue.length < 1 ||
            timeInputValue.length < 1 ||
            priceInputValue.length < 1 ||
            descriptionInputValue.length < 1
        ) {
            alert("All fields must be filled out");
            return;
        }

        if (titleInputValue.length > 30) {
            alert("Title can not be longer than 30 characters");
            return;
        }

        if (!/^\d+$/.test(timeInputValue) || parseInt(timeInputValue) < 1) {
            alert("Estimated duration must be a number greater than 0");
            return;
        }

        if (!/^\d+$/.test(priceInputValue) || parseInt(priceInputValue) < 1) {
            alert("Estimated cost must be a number greater than 0");
            return;
        }

        if (!editMode && destinations.length < 1) {
            alert("You must have added at least one destination");
            return;
        }

        if (!editMode) {
            try {
                httpRequests.addItineraryAndDestinations({
                    itinerary: {
                        id: -1,
                        writerEmail: loggedInUser?.email,
                        writtenDate: "",
                        title: titleInputValue,
                        cost: +priceInputValue,
                        estimatedTime: +timeInputValue,
                        description: descriptionInputValue,
                        image: "",
                    },
                    destinations: destinations,
                });
                alert("Your itinerary was added successfully!");
                window.location.reload();
                window.location.replace("/homePage");
            } catch (error) {
                alert("There was an error when trying to add the route, please try again.");
            }
        } else if (itineraryId !== undefined) {
            try {
                httpRequests.updateItinerary({
                    id: parseInt(itineraryId),
                    writerEmail: "",
                    writtenDate: "",
                    title: titleInputValue,
                    cost: +priceInputValue,
                    estimatedTime: +timeInputValue,
                    description: descriptionInputValue,
                    image: "",
                });
                alert("Your itinerary has been updated!");
                window.location.reload();
                window.location.replace("/homePage");
            } catch (error) {
                alert("There was an error when trying to update the route, please try again.");
            }
        }
    }

    return (
        <>
            <Nav setLoggedInUser={setLoggedInUser} />
            <div id="newRouteBox">
                <form>
                    {editMode ? <h2> Edit itinerary</h2> : <h2> Add a backpacking itinerary</h2>}
                    <Link id="cancelButton" to="/homePage">
                        Cancel
                    </Link>
                    <br></br>
                    <label className="newRouteLabel" id="titleInputLabel">
                        Title
                    </label>
                    <input
                        className="newRouteInput"
                        id="titleInput"
                        type="input"
                        placeholder="   ..."
                        defaultValue={editMode ? title : ""}
                    ></input>
                    <br></br>
                    <label className="newRouteLabel" id="estimatedTimeInputLabel">
                        Estimated duration (in days)
                    </label>
                    <input
                        className="newRouteInput"
                        id="estimatedTimeInput"
                        type="input"
                        placeholder="   ..."
                        defaultValue={editMode ? time : ""}
                    ></input>
                    <br></br>
                    <label className="newRouteLabel" id="estimatedPriceInputLabel">
                        Estimated cost (in dollars)
                    </label>
                    <input
                        className="newRouteInput"
                        id="estimatedPriceInput"
                        type="input"
                        placeholder="   ..."
                        defaultValue={editMode ? cost : ""}
                    ></input>
                    <br></br>
                    <label className="newRouteLabel" id="descriptionInputLabel">
                        {" "}
                        Description{" "}
                    </label>
                    <textarea
                        className="newRouteInput"
                        id="descriptionInput"
                        placeholder="   ..."
                        defaultValue={editMode ? desc : ""}
                    ></textarea>
                    <br></br>
                    <br></br>
                    {editMode ? null : (
                        <div>
                            <label className="newRouteLabel" id="destinationInputLabel">
                                Add destination to itinerary
                            </label>
                            <br></br>
                            <div id="destinationInputBox">
                                <div>
                                    <label className="newRouteLabel" id="destinationNameInputLabel">
                                        Destination name
                                    </label>
                                    <input
                                        className="newRouteInput"
                                        id="destinationNameInput"
                                        type="input"
                                        placeholder="   ..."
                                    ></input>
                                </div>
                                <div>
                                    <label className="newRouteLabel" id="countryInputLabel">
                                        Country of destination
                                    </label>
                                    <input
                                        className="newRouteInput"
                                        id="countryInput"
                                        type="input"
                                        placeholder="   ..."
                                    ></input>
                                </div>
                            </div>
                            <br></br>
                            <button
                                id="addDestinationButton"
                                onClick={handleAddDestination}
                                type="button"
                            >
                                {" "}
                                Add destination
                            </button>
                            <br></br>
                            <br></br>
                            <br></br>
                            <p id="destinationsAsHTMLTitle">Destinations added:</p>
                            <div id="destinationsAsHTML">
                                <br></br>
                                <br></br>
                            </div>
                        </div>
                    )}
                    {editMode ? (
                        <button
                            id="submitItineraryButton"
                            onClick={submitDestinationInfo}
                            type="button"
                        >
                            {" "}
                            Update itinerary
                        </button>
                    ) : (
                        <button
                            id="submitItineraryButton"
                            onClick={submitDestinationInfo}
                            type="button"
                        >
                            {" "}
                            Add route
                        </button>
                    )}
                </form>
            </div>
        </>
    );
};

export default ItineraryForm;

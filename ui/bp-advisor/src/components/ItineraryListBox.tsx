import { useEffect, useState } from "react";
import ItinerarySummaryBox from "./ItinerarySummaryBox";
import "./itineraryListBoxStyle.css";
import {
    Itinerary,
    ItineraryAndDestinations,
    LoggedInUser,
    User,
} from "./types";
import httpRequests from "./httpRequests";
import { createRoot } from "react-dom/client";

type CreateNewItineraryFormProps = {
    loggedInUser: LoggedInUser;
};

const ItineraryListBox = ({ loggedInUser }: CreateNewItineraryFormProps) => {
    const [itineraryBoxExpanded, setitineraryBoxExpanded] =
        useState<Boolean>(false);

    async function updateExpandableItineraryContainerDiv(): Promise<
        React.MouseEventHandler<HTMLButtonElement> | any
    > {
        try {
            const promise: Promise<ItineraryAndDestinations[]> =
                httpRequests.getItinerariesByUserEmail(loggedInUser.email);
            promise.then((itinerariesOfUser: ItineraryAndDestinations[]) =>
                itinerariesOfUser
                    .reverse()
                    .forEach((itineraryAndDestinations) => {
                        if (
                            !document.getElementById(
                                itineraryAndDestinations.itinerary.id.toString()
                            )
                        ) {
                            let expandableItineraryContainerDiv: HTMLDivElement =
                                document.getElementById(
                                    "expandableItineraryContainer"
                                ) as HTMLDivElement;
                            let itinerarySummaryDiv: HTMLDivElement =
                                document.createElement("div");
                            itinerarySummaryDiv.classList.add(
                                "itinerarySummaryDiv"
                            );

                            itinerarySummaryDiv.id =
                                itineraryAndDestinations.itinerary.id.toString();
                            itinerarySummaryDiv.addEventListener(
                                "click",
                                function (event: MouseEvent) {
                                    const itinerarySummaryDiv = (
                                        event.target as HTMLElement
                                    ).closest(
                                        ".itinerarySummaryDiv"
                                    ) as HTMLDivElement;
                                    if (itinerarySummaryDiv) {
                                        handleExpansionOpen(
                                            event,
                                            itinerarySummaryDiv.id
                                        );
                                    }
                                }
                            );

                            let description: string = "";
                            if (
                                itineraryAndDestinations.itinerary.description
                                    .length > 150
                            ) {
                                description =
                                    itineraryAndDestinations.itinerary.description.substring(
                                        0,
                                        150
                                    ) + " ...";
                            } else {
                                description =
                                    itineraryAndDestinations.itinerary
                                        .description;
                            }

                            let itinerarySummaryBox = (
                                <ItinerarySummaryBox
                                    title={
                                        itineraryAndDestinations.itinerary.title
                                    }
                                    description={description}
                                    estimatedTime={itineraryAndDestinations.itinerary.estimatedTime.toString()}
                                    cost={itineraryAndDestinations.itinerary.cost.toString()}
                                />
                            );

                            createRoot(itinerarySummaryDiv).render(
                                itinerarySummaryBox
                            );
                            expandableItineraryContainerDiv.appendChild(
                                itinerarySummaryDiv
                            );

                            console.log(
                                itineraryAndDestinations.itinerary.id.toString()
                            );
                        }
                    })
            );
        } catch (error) {
            alert("Could not load itineraries. Please refresh the page");
        }
    }

    async function handleExpansionOpen(
        event: MouseEvent,
        itineraryId: string
    ): Promise<React.MouseEventHandler<HTMLElement> | any> {
        try {
            const promise: Promise<ItineraryAndDestinations> =
                httpRequests.getItineraryAndDestinationsById(
                    parseInt(itineraryId)
                );
            promise.then(
                (itineraryAndDestinations: ItineraryAndDestinations) => {
                    let title: HTMLElement = document.getElementById(
                        "itineraryBoxTitle"
                    ) as HTMLElement;
                    title.innerHTML = itineraryAndDestinations.itinerary.title;

                    let author: HTMLElement = document.getElementById(
                        "itineraryDetailsAuthor"
                    ) as HTMLElement;

                    /* try {
                        const userPromise: Promise<User> = httpRequests.getUser(
                            itineraryAndDestinations.itinerary.writerEmail
                        );
                        userPromise.then((user: User) => {
                            console.log(username);
                            username = user.username;
                        });
                    } catch (error) {
                        //TODO: Error handling
                    } */

                    author.innerHTML =
                        "Author: " +
                        itineraryAndDestinations.itinerary.writerEmail;

                    let duration: HTMLElement = document.getElementById(
                        "itineraryDetailsDuration"
                    ) as HTMLElement;

                    duration.innerHTML =
                        "Duration: " +
                        itineraryAndDestinations.itinerary.estimatedTime +
                        " days";

                    let cost: HTMLElement = document.getElementById(
                        "itineraryDetailsCost"
                    ) as HTMLElement;

                    cost.innerHTML =
                        "Cost: $ " + itineraryAndDestinations.itinerary.cost;

                    let itineraryBoxDescription: HTMLElement =
                        document.getElementById(
                            "itineraryBoxDescription"
                        ) as HTMLElement;

                    itineraryBoxDescription.innerHTML =
                        itineraryAndDestinations.itinerary.description;

                    let itineraryDestinationBox: HTMLDivElement =
                        document.getElementById(
                            "itineraryDestinationBox"
                        ) as HTMLDivElement;

                    let counterOfDestinations: number = 0;
                    itineraryAndDestinations.destinations.forEach(
                        (destination) => {
                            let p = document.createElement("p");
                            p.innerHTML = destination.destinationName;
                            itineraryDestinationBox.appendChild(p);

                            counterOfDestinations++;

                            if (
                                counterOfDestinations !==
                                itineraryAndDestinations.destinations.length
                            ) {
                                let div1 = document.createElement("div");
                                div1.style.width = "4px";
                                div1.style.height = "4px";
                                div1.style.backgroundColor = "#d65745";
                                div1.style.borderRadius = "50%";
                                div1.style.margin = "auto";
                                div1.style.marginTop = "4px";
                                div1.style.marginBottom = "4px";
                                itineraryDestinationBox.appendChild(div1);

                                let div2 = document.createElement("div");
                                div2.style.width = "7px";
                                div2.style.height = "7px";
                                div2.style.backgroundColor = "#d65745";
                                div2.style.borderRadius = "50%";
                                div2.style.margin = "auto";
                                div2.style.marginTop = "4px";
                                div2.style.marginBottom = "4px";
                                itineraryDestinationBox.appendChild(div2);

                                let div3 = document.createElement("div");
                                div3.style.width = "4px";
                                div3.style.height = "4px";
                                div3.style.backgroundColor = "#d65745";
                                div3.style.borderRadius = "50%";
                                div3.style.margin = "auto";
                                div3.style.marginTop = "4px";
                                div3.style.marginBottom = "4px";
                                itineraryDestinationBox.appendChild(div3);
                            }
                        }
                    );
                }
            );
        } catch (error) {}

        setitineraryBoxExpanded(true);
    }

    const handleExpansionClose = () => {
        setitineraryBoxExpanded(false);
    };

    useEffect(() => {
        updateExpandableItineraryContainerDiv();
    }, []);

    /* const destinationsHTMLElement: HTMLDivElement = document.getElementById('itineraryDestinationBox') as HTMLDivElement;

    const setDestinations = (destinations: string[]) => {
        destinations.forEach(destination => {
            let p = document.createElement('p');
            p.textContent = destination as string;
            destinationsHTMLElement.appendChild(p);
        });
    } */

    return (
        <>
            <div id="expandableItineraryContainer"></div>
            {!itineraryBoxExpanded ? (
                <></>
            ) : (
                <>
                    <div id="itineraryBox">
                        <div id="itineraryDestinationBox">
                            <p id="itineraryDestinationBoxTitle">
                                Destinations:
                            </p>
                        </div>
                        <div id="itineraryColumnFlexBox">
                            <h2 id="itineraryBoxTitle"></h2>
                            <div id="itineraryDetailsFlexBox">
                                <p
                                    id="itineraryDetailsAuthor"
                                    className="itineraryDetailElement"
                                ></p>
                                <p
                                    id="itineraryDetailsDuration"
                                    className="itineraryDetailElement"
                                ></p>
                                <p
                                    id="itineraryDetailsCost"
                                    className="itineraryDetailElement"
                                ></p>
                            </div>
                            <p id="itineraryBoxDescription"></p>
                        </div>
                        <p
                            id="itineraryBoxCloseButton"
                            onClick={handleExpansionClose}
                        >
                            Close
                        </p>
                    </div>
                </>
            )}
        </>
    );
};

export default ItineraryListBox;

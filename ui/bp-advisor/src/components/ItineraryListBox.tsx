import { useEffect, useState } from "react";
import ItinerarySummaryBox from "./ItinerarySummaryBox";
import "./itineraryListBoxStyle.css";
import { ItineraryAndDestinations, LoggedInUser } from "./types";
import httpRequests from "./httpRequests";
import { createRoot } from "react-dom/client";
import LikeButton from "./LikeButton";

type ItineraryListBoxProps = {
    itinerariesBasedOn: string;
    loggedInUser: LoggedInUser;
};

/**
 * Component for a BP-Advisor list of itineraries. Creates a list of itineraries as HTML based on the input.
 * Can as of now only create a list of the logged in users itineraries.
 *
 * @param itinerariesBasedOn String that defines what the itineraries in the list should be based on. Only
 * recognizes the string "loggedInUser" as of now, which creates a list of all the itineraries the logged
 * in user has created.
 * @param loggedInUser A user object, the logged in user.
 *
 * @returns HTML-code for a BP-Advisor list of itineraries.
 */
const ItineraryListBox = ({
    itinerariesBasedOn,
    loggedInUser,
}: ItineraryListBoxProps) => {
    const [itineraryBoxExpanded, setitineraryBoxExpanded] =
        useState<Boolean>(false);
    const [buttonLiked, setButtonLiked] =
        useState(false);

    async function updateExpandableItineraryContainerDiv(): Promise<
        React.MouseEventHandler<HTMLButtonElement> | any
    > {
        if (itinerariesBasedOn === "loggedInUser") {
            // Checks whether the itineraries should be based on
            // the logged in user. Should check for more strings
            // in the future, for example "recentItineraries", or
            // "favorites".
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
                                    // Only creates the itinerary in the list
                                    // if it isn't created before. Stops duplicates.
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
                                                itinerarySummaryDiv.id
                                            );
                                        }
                                    }
                                );

                                let description: string = "";
                                if (
                                    itineraryAndDestinations.itinerary
                                        .description.length > 150
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
                                            itineraryAndDestinations.itinerary
                                                .title
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

                            }
                        })
                );
            } catch (error) {
                alert("Could not load itineraries. Please refresh the page");
            }
        }
    }

    /**
     * Function for expanding the view of one itinerary.
     *
     * @param itineraryId A string, the id of the itinerary to show
     */
    async function handleExpansionOpen(
        itineraryId: string
    ): Promise<React.MouseEventHandler<HTMLElement> | any> {
        try {
            const promise: Promise<ItineraryAndDestinations> =
                httpRequests.getItineraryAndDestinationsById(
                    parseInt(itineraryId)
                );
            promise.then(
                (itineraryAndDestinations: ItineraryAndDestinations) => {
                    console.log(itineraryAndDestinations);
                    let title: HTMLElement = document.getElementById(
                        "itineraryBoxTitle"
                    ) as HTMLElement;
                    title.innerHTML = itineraryAndDestinations.itinerary.title;

                    let author: HTMLElement = document.getElementById(
                        "itineraryDetailsAuthor"
                    ) as HTMLElement;

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
                    
                    let likeButton: HTMLButtonElement =
                        document.getElementById("itineraryDetailsLike") as HTMLButtonElement;

                    try {
                        const getLikePromise: Promise<boolean> =
                            httpRequests.itineraryIsLiked(loggedInUser.email, parseInt(itineraryId));
                        getLikePromise.then((liked: boolean) => {
                            setButtonLiked(liked);
                        });
                    } catch (error) {
                        alert("Could not get like state");
                    }

                    likeButton.addEventListener("click", () => {
                        try {
                          httpRequests.updateLikeOnItinerary(loggedInUser.email, parseInt(itineraryId));
                          setButtonLiked(!buttonLiked);
                        } catch (error) {
                          alert("Could not update like");
                        }
                      });

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
                                let circle1 = document.createElement("div");
                                circle1.style.width = "4px";
                                circle1.style.height = "4px";
                                circle1.style.backgroundColor = "#d65745";
                                circle1.style.borderRadius = "50%";
                                circle1.style.margin = "auto";
                                circle1.style.marginTop = "4px";
                                circle1.style.marginBottom = "4px";
                                itineraryDestinationBox.appendChild(circle1);

                                let circle2 = document.createElement("div");
                                circle2.style.width = "7px";
                                circle2.style.height = "7px";
                                circle2.style.backgroundColor = "#d65745";
                                circle2.style.borderRadius = "50%";
                                circle2.style.margin = "auto";
                                circle2.style.marginTop = "4px";
                                circle2.style.marginBottom = "4px";
                                itineraryDestinationBox.appendChild(circle2);

                                let circle3 = document.createElement("div");
                                circle3.style.width = "4px";
                                circle3.style.height = "4px";
                                circle3.style.backgroundColor = "#d65745";
                                circle3.style.borderRadius = "50%";
                                circle3.style.margin = "auto";
                                circle3.style.marginTop = "4px";
                                circle3.style.marginBottom = "4px";
                                itineraryDestinationBox.appendChild(circle3);
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

    // Updates the list when the component is loaded on a page.
    useEffect(() => {
        updateExpandableItineraryContainerDiv();
    }, []);

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
                        <div id="itineraryLikeButton">
                            <LikeButton id={"itineraryDetailsLike"} initialLiked={buttonLiked} />
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

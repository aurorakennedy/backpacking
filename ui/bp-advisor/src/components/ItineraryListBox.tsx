import { useEffect, useState } from "react";
import ItinerarySummaryBox from "./ItinerarySummaryBox";
import "./itineraryListBoxStyle.css";
import { Itinerary, ItineraryAndDestinations, ItineraryComment, LoggedInUser } from "./types";
import httpRequests from "./httpRequests";
import { createRoot } from "react-dom/client";
import LikeButton from "./LikeButton";
import { Link } from "react-router-dom";
import RatingBar from "./RatingBar";
import DeleteItinerary from "./DeleteItinerary";
import Comment from "./Comment";
import AddComment from "./AddComment";

type ItineraryListBoxProps = {
    idOfWrappingDiv: string;
    itinerariesBasedOn:
        | "Your itineraries"
        | "Recommended itineraries"
        | "Liked itineraries"
        | "Searched itineraries"
        | "Rated itineraries"
        | "All itineraries";
    loggedInUser: LoggedInUser;

    //added keyword
    keyword: string;
};

/**
 * Component for a BP-Advisor list of itineraries. Creates a list of itineraries as HTML based on the input.
 *
 * @param itinerariesBasedOn String that defines what the itineraries in the list should be based on. Can be "Your itineraries",
 * which returns a list of the logged in users itineraries, "Recommended itineraries", which returns a list of recommended
 * itineraries for the logged in user, "Liked itineraries", which returns a list of itineraries the user has liked,
 * "Rated itineraries, which returns a list of the itineraries that the user has rated, or "All itineraries", which returns a
 * list of all created itineraries if the logged in user is an admin.
 * @param loggedInUser A user object, the logged in user.
 * @returns HTML-code for a BP-Advisor list of itineraries.
 */
const ItineraryListBox = ({
    itinerariesBasedOn,
    loggedInUser,
    idOfWrappingDiv,

    //10th March: added keyword
    keyword,
}: ItineraryListBoxProps) => {
    const [itineraryBoxExpanded, setitineraryBoxExpanded] = useState<Boolean>(false);
    const [buttonLiked, setButtonLiked] = useState(false);

    const [itineraryId, setItineraryId] = useState(-1);
    const [title, setTitle] = useState("");
    const [time, setTime] = useState(-1);
    const [cost, setCost] = useState(-1);
    const [desc, setDesc] = useState("");

    const [sameUser, setSameUser] = useState(false);
    const [comments, setComments] = useState([
        { id: 1, author: "Test", content: "Test", allowEditing: false },
    ]);

    const [hasLikedOrRated, setHasLikedOrRated] = useState(false);

    // Updates the list when the component is loaded on a page.
    useEffect(() => {
        updateExpandableItineraryContainerDiv();
    }, []);

    async function updateExpandableItineraryContainerDiv(): Promise<
        React.MouseEventHandler<HTMLButtonElement> | any
    > {
        if (itinerariesBasedOn === "Your itineraries") {
            // Checks whether the itineraries should be based on
            // the logged in user. Should check for more strings
            // in the future, for example "recentItineraries", or
            // "favorites".
            try {
                const promise: Promise<Itinerary[]> = httpRequests.getItinerariesByUserEmail(
                    loggedInUser.email
                );
                promise.then((itinerariesOfUser: Itinerary[]) => {
                    displayItineraries(itinerariesOfUser, itinerariesBasedOn);
                });
            } catch (error) {
                alert("Could not load itineraries. Please refresh the page");
            }
        } else if (itinerariesBasedOn === "Recommended itineraries") {
            try {
                const promise: Promise<Itinerary[]> = httpRequests.getRecommendedItineraries(
                    loggedInUser.email
                );
                promise.then((recommendedItineraries: Itinerary[]) => {
                    displayItineraries(recommendedItineraries, itinerariesBasedOn);
                });
            } catch (error) {
                alert("Could not load itineraries. Please refresh the page");
            }
        } else if (itinerariesBasedOn === "Liked itineraries") {
            try {
                const promise: Promise<Itinerary[]> = httpRequests.getLikedItineraries(
                    loggedInUser.email
                );
                promise.then((likedItineraries: Itinerary[]) => {
                    displayItineraries(likedItineraries, itinerariesBasedOn);
                });
            } catch (error) {
                alert("Could not load itineraries. Please refresh the page");
            }

            //March 10th: Added another searched itineraries
            //Need help here. I think it messed up all ItineraryListBox
        } else if (itinerariesBasedOn === "Searched itineraries") {
            try {
                const promise: Promise<Itinerary[]> = httpRequests.searchByKeyword(keyword);
                //Takes in keyword string

                promise.then((searchedItineraries: Itinerary[]) => {
                    displayItineraries(searchedItineraries, itinerariesBasedOn);
                });
            } catch (error) {
                alert("Could not load itineraries. Please refresh the page");
            }
        } else if (itinerariesBasedOn === "Rated itineraries") {
            try {
                const promise: Promise<Itinerary[]> = httpRequests.getRatedItineraries(
                    loggedInUser.email
                );
                promise.then((ratedItineraries: Itinerary[]) => {
                    displayItineraries(ratedItineraries, itinerariesBasedOn);
                });
            } catch (error) {
                alert("Could not load itineraries. Please refresh the page");
            }
        } else if (itinerariesBasedOn === "All itineraries") {
            checkIfUserIsAdmin().then((isAdmin: Boolean) => {
                if (isAdmin) {
                    try {
                        const promise: Promise<Itinerary[]> = httpRequests.getEveryItinerary();
                        promise.then((allItineraries: Itinerary[]) => {
                            displayItineraries(allItineraries, itinerariesBasedOn);
                        });
                    } catch (error) {
                        alert("Could not load itineraries. Please refresh the page");
                    }
                }
            });
        }

        //10th march, marisa, adding another paranthesis
    }

    function displayItineraries(itineraries: Itinerary[], itinerariesBasedOn: string) {
        let listContainerDiv: HTMLDivElement = document.getElementById(
            idOfWrappingDiv
        ) as HTMLDivElement;
        if (itineraries.length > 0) {
            let title: HTMLElement = document.createElement("h2");
            title.classList.add("itineraryListTitles");
            title.innerHTML = itinerariesBasedOn;
            listContainerDiv.appendChild(title);
        }
        let expandableItineraryListDiv: HTMLDivElement = document.createElement("div");
        if (itinerariesBasedOn === "Searched itineraries") {
            expandableItineraryListDiv.classList.add("expandableSearchItineraryList");
        } else {
            expandableItineraryListDiv.classList.add("expandableItineraryList");
        }
        itineraries.reverse().forEach((itinerary) => {
            let itinerarySummaryDiv: HTMLDivElement = document.createElement("div");
            itinerarySummaryDiv.classList.add("itinerarySummaryDiv");

            itinerarySummaryDiv.id = itinerary.id.toString();
            itinerarySummaryDiv.addEventListener("click", function (event: MouseEvent) {
                const itinerarySummaryDiv = (event.target as HTMLElement).closest(
                    ".itinerarySummaryDiv"
                ) as HTMLDivElement;
                if (itinerarySummaryDiv) {
                    handleExpansionOpen(itinerarySummaryDiv.id);
                }
            });

            let description: string = "";
            if (itinerary.description.length > 150) {
                description = itinerary.description.substring(0, 65) + " ...";
            } else {
                description = itinerary.description;
            }

            let imgElement: HTMLImageElement | null = null;

            try {
                const imagePromise: Promise<Uint8Array> = httpRequests.getItineraryImage(
                    itinerary.id
                );
                imagePromise.then((imageArray: Uint8Array) => {
                    if (imageArray.length != 0) {
                        imgElement = document.createElement("img") as HTMLImageElement;
                        const byteArray: Uint8Array = new Uint8Array(imageArray);
                        const blob: Blob = new Blob([byteArray]);
                        const url: string = URL.createObjectURL(blob);
                        imgElement.src = url;
                    }
                    let itinerarySummaryBox = (
                        <ItinerarySummaryBox
                            title={itinerary.title}
                            image={imgElement}
                            description={description}
                            estimatedTime={itinerary.estimatedTime.toString()}
                            cost={itinerary.cost.toString()}
                        />
                    );
                    createRoot(itinerarySummaryDiv).render(itinerarySummaryBox);
                });
            } catch (error) {
                console.error(error);
            }

            expandableItineraryListDiv.appendChild(itinerarySummaryDiv);

            listContainerDiv.appendChild(expandableItineraryListDiv);
        });
    }

    /**
     * Function for checking whether the logged in user is an admin
     */
    async function checkIfUserIsAdmin(): Promise<Boolean> {
        let result: Boolean = false;
        try {
            const isAdminPromise: Promise<Boolean> = httpRequests.isAdmin(loggedInUser.email);
            result = await isAdminPromise;
        } catch (error) {}

        return result;
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
            console.log("Itinerary ID: " + itineraryId);
            const promise: Promise<ItineraryAndDestinations> =
                httpRequests.getItineraryAndDestinationsById(parseInt(itineraryId));
            promise.then(async (itineraryAndDestinations: ItineraryAndDestinations) => {
                console.log(itineraryAndDestinations);

                setItineraryId(parseInt(itineraryId));
                setTitle(itineraryAndDestinations.itinerary.title);
                setTime(itineraryAndDestinations.itinerary.estimatedTime);
                setCost(itineraryAndDestinations.itinerary.cost);
                setDesc(itineraryAndDestinations.itinerary.description);

                let title: HTMLElement = document.getElementById(
                    "itineraryBoxTitle"
                ) as HTMLElement;
                title.innerHTML = itineraryAndDestinations.itinerary.title;

                let author: HTMLElement = document.getElementById(
                    "itineraryDetailsAuthor"
                ) as HTMLElement;

                const email: string = itineraryAndDestinations.itinerary.writerEmail;

                const usernamePromise: Promise<string> = httpRequests.getUsernameByEmail(email);
                const username: string = await usernamePromise;

                author.innerHTML = "Author: " + username;

                setSameUser(loggedInUser.email === email);

                let duration: HTMLElement = document.getElementById(
                    "itineraryDetailsDuration"
                ) as HTMLElement;

                duration.innerHTML =
                    "Duration: " + itineraryAndDestinations.itinerary.estimatedTime + " days";

                let cost: HTMLElement = document.getElementById(
                    "itineraryDetailsCost"
                ) as HTMLElement;

                cost.innerHTML = "Cost: $ " + itineraryAndDestinations.itinerary.cost;

                let itineraryBoxDescription: HTMLElement = document.getElementById(
                    "itineraryBoxDescription"
                ) as HTMLElement;

                itineraryBoxDescription.innerHTML = itineraryAndDestinations.itinerary.description;

                let itineraryDestinationBox: HTMLDivElement = document.getElementById(
                    "itineraryDestinationBox"
                ) as HTMLDivElement;

                let likeButton: HTMLButtonElement = document.getElementById(
                    "itineraryDetailsLike"
                ) as HTMLButtonElement;

                try {
                    const getLikePromise: Promise<boolean> = httpRequests.itineraryIsLiked(
                        loggedInUser.email,
                        parseInt(itineraryId)
                    );
                    getLikePromise.then((liked: boolean) => {
                        setButtonLiked(liked);
                    });
                } catch (error) {
                    alert("Could not get like state");
                }

                likeButton.addEventListener("click", () => {
                    try {
                        httpRequests.updateLikeOnItinerary(
                            loggedInUser.email,
                            parseInt(itineraryId)
                        );
                        setButtonLiked(!buttonLiked);
                        setHasLikedOrRated(!hasLikedOrRated);
                    } catch (error) {
                        alert("Could not update like");
                    }
                });

                let itineraryLikeAndRatingFlexBox: HTMLDivElement = document.getElementById(
                    "itineraryLikeAndRatingFlexBox"
                ) as HTMLDivElement;

                let itineraryRatingBarDiv: HTMLDivElement = document.createElement("div");

                let ratingBar = (
                    <RatingBar
                        loggedInUser={loggedInUser}
                        itineraryId={parseInt(itineraryId)}
                        updateAverageRating={function (): void {
                            updateAverageRating(itineraryId);
                        }}
                        updateHasLikedOrRated={function (): void {
                            setHasLikedOrRated(!hasLikedOrRated);
                        }}
                    />
                );
                createRoot(itineraryRatingBarDiv).render(ratingBar);
                itineraryLikeAndRatingFlexBox.appendChild(itineraryRatingBarDiv);

                updateAverageRating(itineraryId);

                checkIfUserIsAdmin().then((isAdmin: Boolean) => {
                    if (
                        loggedInUser.email === itineraryAndDestinations.itinerary.writerEmail ||
                        isAdmin
                    ) {
                        let closeAndDeleteColumnDiv: HTMLDivElement = document.getElementById(
                            "closeAndDeleteColumn"
                        ) as HTMLDivElement;

                        let deleteButtonDiv: HTMLDivElement = document.createElement("div");
                        deleteButtonDiv.id = "itineraryDeleteButtonDiv";

                        let deleteButton = (
                            <DeleteItinerary itineraryID={itineraryAndDestinations.itinerary.id} />
                        );
                        createRoot(deleteButtonDiv).render(deleteButton);
                        closeAndDeleteColumnDiv.appendChild(deleteButtonDiv);
                    }
                });

                try {
                    const imagePromise: Promise<Uint8Array> = httpRequests.getItineraryImage(
                        itineraryAndDestinations.itinerary.id
                    );
                    imagePromise.then((imageArray: Uint8Array) => {
                        const imgAndDestinationsWrapper: HTMLDivElement = document.getElementById(
                            "imageAndDestinationWrapper"
                        ) as HTMLDivElement;
                        if (imageArray.length != 0) {
                            const imgElement: HTMLImageElement = document.createElement(
                                "img"
                            ) as HTMLImageElement;
                            imgElement.id = "itineraryImage";
                            const byteArray: Uint8Array = new Uint8Array(imageArray);
                            const blob: Blob = new Blob([byteArray]);
                            const url: string = URL.createObjectURL(blob);
                            imgElement.src = url;
                            imgAndDestinationsWrapper.prepend(imgElement);
                        }
                    });
                } catch (error) {
                    console.error(error);
                }

                let counterOfDestinations: number = 0;
                itineraryAndDestinations.destinations.forEach((destination) => {
                    let p = document.createElement("p");
                    p.innerHTML = destination.destinationName;
                    itineraryDestinationBox.appendChild(p);

                    counterOfDestinations++;

                    if (counterOfDestinations !== itineraryAndDestinations.destinations.length) {
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
                });

                const commentsList: ItineraryComment[] = await httpRequests.getComments(
                    parseInt(itineraryId)
                );

                console.log(commentsList);

                const updatedComments = commentsList.reverse().map((comment) => ({
                    id: comment.id,
                    author: comment.author,
                    content: comment.content,
                    allowEditing: loggedInUser.username === comment.author,
                }));

                setComments(updatedComments);
            });
        } catch (error) {}

        setitineraryBoxExpanded(true);
    }

    /**
     * Function that gets the average rating of an itinerary from the backend,
     * and displays it a paragraph element.
     * @param itineraryId string, the id of the itinerary
     */
    async function updateAverageRating(itineraryId: string) {
        let itineraryLikeAndRatingFlexBox: HTMLDivElement = document.getElementById(
            "itineraryLikeAndRatingFlexBox"
        ) as HTMLDivElement;

        let averageRatingElement: HTMLParagraphElement = document.getElementById(
            "averageRating"
        ) as HTMLDivElement;

        if (averageRatingElement === null) {
            averageRatingElement = document.createElement("p");
            averageRatingElement.id = "averageRating";
        }

        try {
            const averageRatingOfItineraryPromise: Promise<number> =
                httpRequests.getAverageRatingOfItinerary(parseInt(itineraryId));
            averageRatingOfItineraryPromise.then((averageRating: number) => {
                if (averageRating > 0) {
                    averageRatingElement.innerHTML = "Average rating: " + averageRating.toFixed(1);
                } else {
                    averageRatingElement.innerHTML = "No ratings yet";
                }
                itineraryLikeAndRatingFlexBox.appendChild(averageRatingElement);
            });
        } catch (error) {
            alert("Could not update average rating");
        }
    }

    /**
     * Closes the expanded itinerary box and refreshes the page.
     */
    const handleExpansionClose = () => {
        setitineraryBoxExpanded(false);
        if (hasLikedOrRated) {
            window.location.reload();
        }
    };

    const goToEditForm = () => {
        window.location.replace(`/editItinerary/${itineraryId}/${title}/${time}/${cost}/${desc}`);
    };

    return (
        <>
            {!itineraryBoxExpanded ? (
                <></>
            ) : (
                <>
                    <div id="itineraryBox">
                        <div id="imageAndDestinationWrapper">
                            <div id="itineraryDestinationBox">
                                <p id="itineraryDestinationBoxTitle">Destinations:</p>
                            </div>
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
                                <p id="itineraryDetailsCost" className="itineraryDetailElement"></p>
                            </div>
                            <div id="itineraryLikeAndRatingFlexBox">
                                <div id="itineraryLikeButton">
                                    <LikeButton
                                        id={"itineraryDetailsLike"}
                                        initialLiked={buttonLiked}
                                    />
                                </div>

                                <button
                                    id="editButton"
                                    type="button"
                                    hidden={!sameUser}
                                    onClick={goToEditForm}
                                >
                                    Edit
                                </button>
                            </div>
                            <p id="itineraryBoxDescription"></p>

                            <div
                                style={{
                                    backgroundColor: "#eee",
                                    padding: "20px",
                                    marginTop: "50px",
                                    borderRadius: "0px",
                                    border: "1px solid black",
                                    width: "100%",
                                }}
                            >
                                <div style={{ padding: "20px" }}>
                                    <AddComment
                                        onSubmit={async function (comment: string): Promise<void> {
                                            const usernamePromise: Promise<string> =
                                                httpRequests.getUsernameByEmail(loggedInUser.email);
                                            const username: string = await usernamePromise;
                                            const id: number = await httpRequests.addComment({
                                                id: -1,
                                                itineraryId: itineraryId,
                                                author: username,
                                                content: comment,
                                            });
                                            setComments([
                                                {
                                                    id: id,
                                                    author: username,
                                                    content: comment,
                                                    allowEditing: true,
                                                },
                                                ...comments,
                                            ]);
                                        }}
                                    ></AddComment>
                                </div>
                                <div style={{ padding: "10px", borderRadius: "10px" }}>
                                    {comments.map((comment) => (
                                        <Comment
                                            key={comment.id}
                                            author={comment.author}
                                            content={comment.content}
                                            allowEditing={comment.allowEditing}
                                            onDelete={function (): void {
                                                httpRequests.deleteComment(comment.id);
                                                const updatedComments = comments.filter(
                                                    (c) => c.id !== comment.id
                                                );
                                                setComments(updatedComments);
                                            }}
                                            onUpdate={function (updatedContent: string): void {
                                                httpRequests.updateComment(
                                                    comment.id,
                                                    updatedContent
                                                );
                                            }}
                                        />
                                    ))}
                                </div>
                            </div>
                        </div>
                        <div id="closeAndDeleteColumn">
                            <p id="itineraryBoxCloseButton" onClick={handleExpansionClose}>
                                Close
                            </p>
                        </div>
                    </div>
                </>
            )}
        </>
    );
};

export default ItineraryListBox;

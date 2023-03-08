import { useEffect, useState } from "react";
import httpRequests from "./httpRequests";
import "./ratingBarStyle.css";
import { LoggedInUser } from "./types";

type RatingBarProps = {
    loggedInUser: LoggedInUser;
    itineraryId: number;
};

/**
 * Component for a BP-Advisor rating bar of 5 stars.
 *
 * @param loggedInUser a user object, the logged in user
 * @param itineraryId number, the if of the itinerary
 * @returns HTML code for the rating bar component
 */
const RatingBar = ({ loggedInUser, itineraryId }: RatingBarProps) => {
    const [selectedRating, setSelectedRating] = useState<string>("");

    useEffect(() => {
        setUserRatingOfItinerary(loggedInUser.email, itineraryId);
    }, []);

    /**
     * Gets the rating of the user for an itinerary from the database and sets it.
     *
     * @param loggedInUserEmail
     * @param itineraryId
     */
    async function setUserRatingOfItinerary(
        loggedInUserEmail: string,
        itineraryId: number
    ) {
        try {
            const ratingPromise: Promise<number> =
                httpRequests.getUserRatingOfItinerary(
                    loggedInUserEmail,
                    itineraryId
                );
            ratingPromise.then((rating: number) => {
                setRating(rating);
            });
        } catch (error) {
            alert("Could not get previous rating");
        }
    }

    /**
     * Sets the rating of the rating bar
     *
     * @param rating a number between 1 and 5
     * @returns if number if not between 1 and 5
     */
    function setRating(rating: number): void {
        if (rating < 1 || rating > 5) {
            console.error("Rating must be between 1 and 5.");
            return;
        }

        const ratingInputs =
            document.querySelectorAll<HTMLInputElement>(".rating input");
        ratingInputs.forEach((input) => {
            if (input.value === rating.toString()) {
                input.checked = true;
            } else {
                input.checked = false;
            }
        });
    }

    /**
     * Sends the rating provided by the user to the database,
     * removes it if the user clicks again on the provided rating.
     *
     * @param event
     */
    async function handleRatingClick(
        event: React.MouseEvent<HTMLInputElement>
    ) {
        const target = event.target as HTMLInputElement;
        const rating = target.checked ? target.value : "0"; // NÅR RATING FJERNES, KAN DEN VÆRE 0 ELLER MÅ RATINGEN SLETTES FRA DATABASEN???
        try {
            await httpRequests.addRatingOfItinerary(
                loggedInUser.email,
                itineraryId,
                parseInt(rating)
            );
        } catch (error) {}
        removeRatingIfChecked(event);
    }

    /**
     * Removes the rating from the HTML if the user clicks on the
     * rating again.
     *
     * @param event
     */
    const removeRatingIfChecked = (
        event: React.MouseEvent<HTMLInputElement>
    ) => {
        const target = event.target as HTMLInputElement;
        if (target.checked && target.value === selectedRating) {
            setSelectedRating("");
            target.checked = false;
        } else {
            setSelectedRating(target.value);
        }
    };

    return (
        <div className="rating">
            <input
                type="radio"
                onClick={handleRatingClick}
                id="star5"
                name="rating"
                value="5"
            />
            <label htmlFor="star5"></label>
            <input
                type="radio"
                onClick={handleRatingClick}
                id="star4"
                name="rating"
                value="4"
            />
            <label htmlFor="star4"></label>
            <input
                type="radio"
                onClick={handleRatingClick}
                id="star3"
                name="rating"
                value="3"
            />
            <label htmlFor="star3"></label>
            <input
                type="radio"
                onClick={handleRatingClick}
                id="star2"
                name="rating"
                value="2"
            />
            <label htmlFor="star2"></label>
            <input
                type="radio"
                onClick={handleRatingClick}
                id="star1"
                name="rating"
                value="1"
            />
            <label htmlFor="star1"></label>
        </div>
    );
};

export default RatingBar;

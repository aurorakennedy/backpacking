import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Itinerary, LoggedInUser } from "./types";
import httpRequests from "./httpRequests";

/* type DeleteItineraryFormProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
    loggedInUser: LoggedInUser;
  };
   */

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
    const [loading, setLoading] = useState<boolean>(false);
  
    const handleDelete = async () => {
      console.log("Deletion initioated");
      setLoading(true);
      try {
          // Send a request to delete the itinerary
          await httpRequests.deleteItinerary(emailOfAuthor, titleOfItinerary);
          // Call the onDelete callback to update the UI
          // Navigate back to the homepage
          /* window.location.reload(); */
          } catch (error) {
          console.error(error);
          alert("Deletion unsuccessfull");
        setLoading(false);
      }
    };
  
    return (
      <div>
{/*         <p>Are you sure you want to delete this itinerary?</p>
 */}        <button id='slettKnapp' onClick={handleDelete} disabled={loading}>
          {loading ? "Deleting..." : "Delete"}
          Delete 
        </button>
      </div>
    );
  };
  
  export default DeleteItinerary;


/*
const DeleteItinerary: React.FC <DeleteItineraryFormProps> = () => {

    const onDelete = () => {
        console.log("delete");
    }

    const deleteOption = [
        {
            value: "Delete",
            onClick: onDelete,
            color: 'red',
        }
    ]

    return (
        <div className="deleteItinerary">
            <div className="delete-title"> Delete</div>
        </div>
    )
} 
 */

/* const DeleteItinerary = ({
    loggedInUser,
    setLoggedInUser,
  }: DeleteItineraryFormProps) => { 


    if 

    httpRequests.deletItinerary({
        itin
    })
    

    return(
        <div className="deleteItinerary">
                <div className="delete-title"> Delete</div>
            </div>
    )
  } 




const LogInBox = ({ setLoggedInUser }: LogInBoxProps) => {

    
    function submitLogInInfo(): React.MouseEventHandler<HTMLButtonElement> | any {
        const emailInputValue: string = (document.getElementById('emailInput') as HTMLInputElement).value;
        const passwordInputValue: string = (document.getElementById('passwordInput') as HTMLInputElement).value;

        if (emailInputValue.length < 1 || passwordInputValue.length < 1) {
            alert('You must insert username and password to log in.')
            return;
        }

        try {
            console.log(emailInputValue);
            console.log(passwordInputValue);
            const promise: Promise<User> = httpRequests.login({
                username: '',       //MUST BE AN EMPTY STRING TO ENSURE THE FORM OF A USER OBJECT
                email: emailInputValue,
                password: passwordInputValue
            });
            promise.then((user: User) => {
                if (user.email === 'failed') {
                    console.log(user);
                    alert('Incorrect username and/or password.');
                } else {
                    console.log(user);
                    const { username, email } = user;
                    setLoggedInUser({ username, email })
                }
            });

        } catch (error) {
            //TODO: Error handling
        }



    }
}

export default LogInBox */

  /**
   * Component for BP-Advisor to create new itineraries, including relevant input fields,
   * a submit button, and functions for sending the data to the back end.
   *
   * @returns HTML-code for a BP-Advisor login box and functions to support login.
   */
  /* const DeleteItinerary = ({
    loggedInUser,
    setLoggedInUser,
  }: DeleteItineraryFormProps) => { 
    <div className="deleteItinerary">
                <div className="delete-title"> Delete</div>
            </div>

  } */
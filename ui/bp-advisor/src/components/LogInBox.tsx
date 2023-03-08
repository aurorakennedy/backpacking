import "./logInBoxStyle.css";
import { Link, useNavigate } from "react-router-dom";
import { LoggedInUser, User } from "./types";
import httpRequests from "./httpRequests";
import React from "react";
import Header from "./Header";

type LogInBoxProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>;
};

/**
 * Component for BP-Advisor login box, including a title, e-mail and password input fields,
 * a submit button, and a sign up button.
 *
 * @returns HTML-code for a BP-Advisor login box and functions to support login.
 */
const LogInBox = ({ setLoggedInUser }: LogInBoxProps) => {
    console.log("LoginBox mounted");

    return (
        <>
            <Header />
            <div id="logInBox">
                <form>
                    <h2 id="logInBoxTitle"> Welcome back, backpacker!</h2>
                    <label id="emailInputLabel">E-mail</label>
                    <input
                        id="emailInput"
                        type="email"
                        placeholder="   ..."
                        required
                    ></input>
                    <label id="passwordInputLabel">Password</label>
                    <input
                        id="passwordInput"
                        type="password"
                        placeholder="   ..."
                        required
                    ></input>
                    <button
                        id="logInButton"
                        onClick={submitLogInInfo}
                        type="button"
                    >
                        {" "}
                        Log in
                    </button>
                </form>
                {/*  <p id='signUpButton'>Sign up</p> */}

                <Link id="signUpButton" to="/signUp">
                    Sign Up
                </Link>
            </div>
        </>
    );

    /**
     * Gets the input from the email and password input fields, validates it, and sends it to the
     * backend for processing. If a user with the corresponding emal and password is found, it
     * is returned and saved in the browser storage.
     */
    function submitLogInInfo():
        | React.MouseEventHandler<HTMLButtonElement>
        | any {
        const emailInputValue: string = (
            document.getElementById("emailInput") as HTMLInputElement
        ).value;
        const passwordInputValue: string = (
            document.getElementById("passwordInput") as HTMLInputElement
        ).value;

        if (emailInputValue.length < 1 || passwordInputValue.length < 1) {
            alert("You must insert username and password to log in.");
            return;
        }

        try {
            console.log(emailInputValue);
            console.log(passwordInputValue);
            const promise: Promise<User> = httpRequests.login({
                username: "", //MUST BE AN EMPTY STRING TO ENSURE THE FORM OF A USER OBJECT
                email: emailInputValue,
                password: passwordInputValue,
            });
            promise.then((user: User) => {
                if (user.email === "failed") {
                    console.log(user);
                    alert("Incorrect username and/or password.");
                } else {
                    console.log(user);
                    const { username, email } = user;
                    setLoggedInUser({ username, email });
                }
            });
        } catch (error) {
            //TODO: Error handling
        }
    }
};

export default LogInBox;

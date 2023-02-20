import './signUpBoxStyle.css';
import { Link } from 'react-router-dom';
import { LoggedInUser, User } from './types';
import httpRequests from './httpRequests';
import React from 'react';

type SignUpBoxProps = {
    setLoggedInUser: React.Dispatch<React.SetStateAction<LoggedInUser | null>>
}

/**
 * Component for BP-Advisor signup box, including a title, username, e-mail and password input fields, 
 * a sign up button, and a back to login button.
 * 
 * @returns HTML-code for a BP-Advisor signup box.
 */
const SingUpBox = ({ setLoggedInUser }: SignUpBoxProps) => {

    return (
        <div id='signUpBox'>
            <form onSubmit={submitSignUpInfo}>
                <h2 id='signUpBoxTitle'> Become a BP-Advisor!</h2>

                <label id='nameInputLabel'>Username</label>
                <input id='nameInput' type="input" placeholder='   ...'></input>
                <label id='emailSignUpInputLabel'>E-mail</label>
                <input id='emailSingUpInput' type='email' placeholder='   ...'></input>
                <label id='passwordSignUpInputLabel'>Password</label>
                <input id='passwordSignUpInput' type='password' placeholder='   ...'></input>
                <label id='repeatPasswordSignUpInputLabel'> Repeat </label>
                <input id='repeatPasswordSignUpInput' type='password' placeholder='   ...'></input>

                <button id='submitButton' onClick={submitSignUpInfo} type='button'> Sign Up</button>

                <Link id='backToLogInButton' to='/logIn'>
                    Back to Log In
                </Link>
            </form>
        </div>
    )

    /**
    * A function that gets the values of the input fields in the SignUpBox component, validates them,
    * and then sends the data to the backend through the register function of the httpRequests.ts file.
    * If the registration is successful, saves the user to the browser storage.
    */
    async function submitSignUpInfo(): Promise<React.MouseEventHandler<HTMLButtonElement> | any> {

        const usernameInputValue: string = (document.getElementById('nameInput') as HTMLInputElement).value;
        const emailInputValue: string = (document.getElementById('emailSingUpInput') as HTMLInputElement).value;
        const passwordInputValue: string = (document.getElementById('passwordSignUpInput') as HTMLInputElement).value;
        const repeatPasswordInputValue: string = (document.getElementById('repeatPasswordSignUpInput') as HTMLInputElement).value;

        if (usernameInputValue.length < 1 || emailInputValue.length < 1 || passwordInputValue.length < 1 || repeatPasswordInputValue.length < 1) {
            alert('You must provide input into all fields.')
            return;
        }

        if (usernameInputValue.length < 5) {
            alert('Your username must at least be 5 characters long.')
            return;
        }

        if (!(emailInputValue.includes(".") && emailInputValue.includes("@"))) {
            alert('The e-mail is invalid.')
            return;
        }

        if (passwordInputValue.length < 8) {
            alert('The password need to be at least 8 characters long.')
            return;
        }

        if (passwordInputValue !== repeatPasswordInputValue) {
            alert('Password and repeat fields must have the same input.')
            return;
        }

        try {
            await httpRequests.register({
                username: usernameInputValue,
                email: emailInputValue,
                password: passwordInputValue
            });

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

export default SingUpBox



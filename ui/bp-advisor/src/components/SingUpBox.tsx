import React from 'react';
import './signUpBoxStyle.css';
import httpRequests from './httpRequests';
import { Link } from 'react-router-dom';
import Header from './Header';

/**
 * Component for BP-Advisor signup box, including a title, username, e-mail and password input fields, 
 * a sign up button, and a back to login button.
 * 
 * @returns HTML-code for a BP-Advisor signup box.
 */
const SingUpBox = () => {

    return (
        <><div>
            <Header />
        </div><div id='signUpBox'>
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

                    <button id='submitButton' type='submit'> Sign Up</button>

                    <Link id='backToLogInButton' to='/logIn'>
                        Back to Log In
                    </Link>
                </form>
            </div></>
    )

    /**
    * A function that gets the values of the input fields in the SignUpBox component, validates them,
    * and then sends the data to the backend through the register function of the httpRequests.ts file.
    * If the registration is successfull, logs in the user and opens the home page.
    */
    function submitSignUpInfo(): React.MouseEventHandler<HTMLButtonElement> | any {

        const nameInputValue: string = (document.getElementById('nameInput') as HTMLInputElement).value;
        const emailInputValue: string = (document.getElementById('emailSingUpInput') as HTMLInputElement).value;
        const passwordInputValue: string = (document.getElementById('passwordSignUpInput') as HTMLInputElement).value;
        const repeatPasswordInputValue: string = (document.getElementById('repeatPasswordSignUpInput') as HTMLInputElement).value;

        //MUST ADD VALIDATION

        try {
            httpRequests.register({
                username: nameInputValue,
                email: emailInputValue,
                password: passwordInputValue
            });

            //TODO: LOGIN if register succeded
            /* httpRequests.login(

            ) */

            //TODO: Change page if register and login succeded

        } catch (error) {
            //TODO: Error handeling
        }
    }
}

export default SingUpBox



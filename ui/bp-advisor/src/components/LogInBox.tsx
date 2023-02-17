import React from 'react';
import './logInBoxStyle.css';
import { Link } from 'react-router-dom';
import httpRequests from './httpRequests';
import Header from './Header';

/**
 * Component for BP-Advisor login box, including a title, e-mail and password input fields, 
 * a submit button, and a sign up button.
 * 
 * @returns HTML-code for a BP-Advisor login box.
 */
const LogInBox = () => {

    return (
        <><div>
            <Header />
        </div><div id='logInBox'>
                <form onSubmit={submitLogInInfo}>
                    <h2 id='logInBoxTitle'> Welcome back, backpacker!</h2>
                    <label id='emailInputLabel'>E-mail</label>
                    <input id='emailInput' type='email' placeholder='   ...'></input>
                    <label id='passwordInputLabel'>Password</label>
                    <input id='passwordInput' type='password' placeholder='   ...'></input>
                    <button id='logInButton' type='submit'> Log in</button>
                </form>
                {/*  <p id='signUpButton'>Sign up</p> */}
                <Link id='signUpButton' to='/signUp'>
                    Sign Up
                </Link>
            </div></>
    )

    function submitLogInInfo(): React.MouseEventHandler<HTMLButtonElement> | any {
        const emailInputValue: string = (document.getElementById('emailInput') as HTMLInputElement).value;
        const passwordInputValue: string = (document.getElementById('passwordInput') as HTMLInputElement).value;

        //TODO: Validation

        //TODO: 
        /*
        try {
            httpRequests.login();        
            //TODO: New page must open if login successfull            
        } catch (error) {
            //TODO: Error handeling
        }
         */


    }
}

export default LogInBox
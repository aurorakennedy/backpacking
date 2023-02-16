import React from 'react';
import './signUpBoxStyle.css';
import { Link } from 'react-router-dom';

/**
 * Component for BP-Advisor signup box, including a title, username, e-mail and password input fields, 
 * a sign up button, and a back to login button.
 * 
 * @returns HTML-code for a BP-Advisor signup box.
 */
 const SingUpBox = () => {

    return (
        <div id='signUpBox'>
            <form>
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
        </div>
    )
}

export default SingUpBox



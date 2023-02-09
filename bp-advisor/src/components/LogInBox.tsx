import React from 'react';
import "./styles.css";

/**
 * Component for BP-Advisor login box, including a title, e-mail and password input fields, 
 * a submit button, and a sign up button.
 * 
 * @returns HTML-code for a BP-Advisor login box.
 */
const LogInBox = () => {

    return (
        <div className="logInBox">
            <form>
                <h2 id='logInBoxTitle'> Welcome back, backpacker!</h2>
                <div id='inputFieldsBox'>
                    <label id='emailInputLabel'>E-mail</label>
                    <input id='emailInput' type="input" placeholder='   ...'></input>
                    <label id='passwordInputLabel'>Password</label>
                    <input id='passwordInput' type="input" placeholder='   ...'></input>
                    <button id='submitButton' type='submit'> Log in</button>
                </div>
            </form>
            <p id='signUpButton'>Sign up</p>
        </div>
    )
}

export default LogInBox
import React from 'react';
import "./signUpBoxStyle.css";

/**
 * Component for BP-Advisor login box, including a title, e-mail and password input fields, 
 * a submit button, and a sign up button.
 * 
 * @returns HTML-code for a BP-Advisor login box.
 */
 const SingUpBox = () => {

    return (
        <div className="signUpBox">
            <form>
                <h2 id='signUpBoxTitle'> Become a BP - Advisor!</h2>
                <div id='inputFieldsBox'>
                <label id='nameInputLabel'>Name</label>
                    <input id='nameInput' type="input" placeholder='   ...'></input>
                    

                    <label id='emailInputLabel'>E-mail</label>
                    <input id='emailInput' type="input" placeholder='   ...'></input>
                    <label id='passwordInputLabel'>Password</label>
                    <input id='passwordInput' type="input" placeholder='   ...'></input>

                    <label id='countrieInputLabel'>Countrie</label>
                    <input id='countrieInput' type="input" placeholder='   ...'></input>
                    <button id='submitButton' type='submit'> Sign Up</button>
                </div>
            </form>
        </div>
    )
}

export default SingUpBox
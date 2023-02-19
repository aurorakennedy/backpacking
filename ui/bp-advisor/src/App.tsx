import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LoggedInUser } from './components/types';
import LogInBox from './components/LogInBox';
import React, { useEffect, useState } from 'react';
import SignUpBox from './components/SignUpBox';

function App() {

    // Keeps track of the logged in user.
    // When the page is loaded, checks the browsers storage to see whether a user is already logged in.
    const [loggedInUser, setLoggedInUser] = useState<LoggedInUser | null>(
        JSON.parse(localStorage.getItem('loggedInUser') || 'null')
    );

    // Saves the logged in user to the browser storage when user logges in.
    useEffect(() => {
        localStorage.setItem('loggedInUser', JSON.stringify(loggedInUser));
    }, [loggedInUser]);

    /**
     * Deletes the info about the logged in user in the browser when the user logges out.
     */
    const logOut: React.MouseEventHandler<HTMLParagraphElement> | undefined = () => {
        localStorage.setItem('loggedInUser', 'null');
        window.location.reload();
    }

    return (
        <Router>
            <div className="App">
                <h1 id='title'> BP-Advisor</h1>
                <h2 id='subTitle'>Share your backpacking routes</h2>

                {loggedInUser ? ( //TODO: Front page must show when user is logged in
                    <><p>Welcome back, {loggedInUser.username}!</p>
                        <p id='logOutButton' onClick={logOut}> Log out </p></>
                ) : (
                    <Routes>
                        <Route path='/' element={<LogInBox setLoggedInUser={setLoggedInUser} />} />
                        <Route path='/signUp' element={<SignUpBox setLoggedInUser={setLoggedInUser} />} />
                        <Route path='/logIn' element={<LogInBox setLoggedInUser={setLoggedInUser} />} />
                    </Routes>
                )}
            </div>
        </Router>
    );
}

export default App;



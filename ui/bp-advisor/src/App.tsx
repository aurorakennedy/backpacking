import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { LoggedInUser } from './components/types';
import LogInBox from './components/LogInBox';
import React, { useEffect, useState } from 'react';
import SignUpBox from './components/SignUpBox';
import HomePage from './components/HomePage';
import CreateNewItineraryForm from './components/CreateNewItineraryForm';


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
        window.location.replace('/logIn');
    }

    return (
        <Router>
            <div className="App">

                {loggedInUser ? (
                    <>
                        <Routes>
                            <Route path='/' element={<HomePage />} />
                            <Route path='/logIn' element={<HomePage />} />
                            <Route path='/signUp' element={<HomePage />} />
                            <Route path='/homePage' element={<HomePage />} />
                            <Route path='/createItinerary' element={<CreateNewItineraryForm />} />
                        </Routes>
                        <p id='logOutButton' onClick={logOut}> Log out </p> {/* Temporary logout button */}
                    </>
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



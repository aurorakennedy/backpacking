import React, { useEffect, useState } from 'react';
import './App.css';
import { LoggedInUser } from './components/types';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//Switch er Routes 

import LogInBox from './components/LogInBox';
import SignUpBox from './components/SignUpBox';

function App() {

    const [loggedInUser, setLoggedInUser] = useState<LoggedInUser | null>(
        JSON.parse(localStorage.getItem('loggedInUser') || 'null')
    );

    useEffect(() => {
        localStorage.setItem('loggedInUser', JSON.stringify(loggedInUser));
    }, [loggedInUser]);

    const logOut: React.MouseEventHandler<HTMLParagraphElement> | undefined = () => {
        localStorage.setItem('loggedInUser', 'null');
        window.location.reload();
    }

    return (
        <Router>
            <div className="App">
                <h1 id='title'> BP-Advisor</h1>
                <h2 id='subTitle'>Share your backpacking routes</h2>

                {loggedInUser ? (
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



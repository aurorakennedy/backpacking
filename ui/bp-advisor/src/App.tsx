import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { LoggedInUser } from "./components/types";
import LogInBox from "./components/LogInBox";
import React, { useEffect, useState } from "react";
import SignUpBox from "./components/SignUpBox";
import HomePage from "./components/HomePage";

import SearchPageBox from "./components/SearchPage";
import ItineraryForm from "./components/ItineraryForm";


function App() {
    // Keeps track of the logged in user.
    // When the page is loaded, checks the browsers storage to see whether a user is already logged in.
    const [loggedInUser, setLoggedInUser] = useState<LoggedInUser | null>(
        JSON.parse(localStorage.getItem("loggedInUser") || "null")
    );

    // Saves the logged in user to the browser storage when user logges in.
    useEffect(() => {
        localStorage.setItem("loggedInUser", JSON.stringify(loggedInUser));
    }, [loggedInUser]);

    return (
        <Router>
            <div className="App">
             
                {loggedInUser ? (
                    <>
                        <Routes>
                            <Route
                                path="/"
                                element={
                                    <HomePage
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path="/logIn"
                                element={
                                    <HomePage
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path="/signUp"
                                element={
                                    <HomePage
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path="/homePage"
                                element={
                                    <HomePage
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path="/SearchPage/:keyword"
                                element={
                                    <SearchPageBox
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path="/SearchPage"
                                element={
                                    <SearchPageBox
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path="/createItinerary"
                                element={
                                    <ItineraryForm
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                            <Route
                                path={`/editItinerary/:itineraryId/:title/:time/:cost/:desc`}
                                element={
                                    <ItineraryForm
                                        setLoggedInUser={setLoggedInUser}
                                        loggedInUser={loggedInUser}
                                    />
                                }
                            />
                        </Routes>
                    </>
                ) : (
                    <Routes>
                        <Route path="/" element={<LogInBox setLoggedInUser={setLoggedInUser} />} />
                        <Route
                            path="/signUp"
                            element={<SignUpBox setLoggedInUser={setLoggedInUser} />}
                        />

                        <Route
                            path="/logIn"
                            element={<LogInBox setLoggedInUser={setLoggedInUser} />}
                        />
                    </Routes>
                )}
            </div>
        </Router>
    );
}

export default App;

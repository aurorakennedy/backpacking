import React from 'react';
import './App.css';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//Switch er Routes 

import LogInBox from './components/LogInBox';
import SingUpBox from './components/SingUpBox';
import HomePage from './components/HomePage'; 



function App() {

    return (
        <Router>
            <div className="App">
            
                <HomePage></HomePage>

                <Routes>

                    
                    {/* <Route path='/' element={ <LogInBox />} />

                    <Route path='/signUp' element={ <SingUpBox />} />

                    <Route path='/logIn' element={<LogInBox />} /> */}


                </Routes>


            </div>

        </Router>
    );
}

export default App;



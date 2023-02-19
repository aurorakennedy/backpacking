import React from 'react';
import './App.css';

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
//Switch er Routes 

import LogInBox from './components/LogInBox';
import SignUpBox from './components/SignUpBox';

function App() {

    return (
        <Router>
            <div className="App">
                <h1 id='title'> BP-Advisor</h1>
                <h2 id='subTitle'>Share your backpacking routes</h2>
                {/*{ <LogInBox /> }*/}
                {/*    { <SingUpBox /> } */}
                <Routes>
                    {/*   <Route path='/'>
                        <LogInBox />
                    </Route> */}

                    <Route path='/' element={<LogInBox />} />


                    <Route path='/signUp' element={<SignUpBox />} />

                    <Route path='/logIn' element={<LogInBox />} />


                </Routes>


            </div>

        </Router>
    );
}

export default App;



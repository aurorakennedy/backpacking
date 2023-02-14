import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Link, Route} from 'react-router-dom';
//Switch er Routes 

import LogInBox from './components/LogInBox';
import SingUpBox from './components/SingUpBox';

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

                    <Route path='/' element = {<LogInBox />} />


                    <Route path='/signUp' element = {<SingUpBox />} />
                        

                </Routes>
                
    
            </div>
            
        </Router>
    );
}

export default App;



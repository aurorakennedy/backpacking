import React from 'react';
import logo from './logo.svg';
import './App.css';
import LogInBox from './components/LogInBox';
import SingUpBox from './components/SingUpBox';

function App() {
    return (
        <div className="App">
            <h1 id='title'> BP-Advisor</h1>
            <h2 id='subTitle'>Share your backpacking routes</h2>
            {/* <LogInBox />  */}
           { <SingUpBox /> }
        </div>
    );
}

export default App;
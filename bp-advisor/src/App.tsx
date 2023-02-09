import React from 'react';
import logo from './logo.svg';
import './App.css';
import LogInBox from './components/LogInBox';

function App() {
    return (
        <div className="App">
            <h1 id='title'> BP-Advisor</h1>
            <h2 id='subTitle'>Share your backpacking routes</h2>
            <LogInBox />
        </div>
    );
}

export default App;
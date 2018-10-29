import ListOfItems from "./component/ListOfItems";
import React, { Component } from 'react';

import './App.css';

class App extends Component {
    render() {
        return (
            <div className="App">
                <ListOfItems />
            </div>
        );
    }
}

export default App;

import React, { Component } from 'react';

class ActionButton extends Component {
    render() {
        return (
            <span className="action" onClick={ this.props.handleOnClick }>
                <button className="delete">Delete</button>
            </span>
        );
    }
}

export default ActionButton;

import React, { Component } from 'react';

class ActionButton extends Component {
    render() {
        return (
            <span className="action" onClick={ this.props.handleOnClick }>
                <span>
                    <input />

                    <button className="create-child">Create Child</button>
                </span>

                <span>
                    <input />

                    <button className="edit">Edit</button>
                </span>

                <button className="delete">Delete</button>
            </span>
        );
    }
}

export default ActionButton;

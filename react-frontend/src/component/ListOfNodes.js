import React, { Component } from 'react';
import ActionButton from './ActionButton';

class ListOfNodes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            nodes: []
        }

        this._handleAdd = this._handleAdd.bind(this);
        this._handleClick = this._handleClick.bind(this);
        this._refreshData = this._refreshData.bind(this);
    }

    componentDidMount() {
        this._refreshData();
    }

    _handleAdd(event) {
        let container = event.target.closest("div");
        let input = container.querySelector("input");
        let inputValue = input.value;

        if (inputValue.value === "") {
            return;
        }

        fetch('http://localhost:8080/nodes/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "name": inputValue
            })
        }).then(() => this._refreshData());

        input.value = "";
    }

    _handleClick(event) {
        let button = event.target.closest("button");

        if (button) {
            var action = button.className;

            let item = event.target.closest("li");
            let pk = item.getAttribute("data-pk");

            if (action === "delete") {
                this._handleDelete(pk);
            }
        }
    }

    _handleDelete(pk) {
        fetch('http://localhost:8080/nodes/' + pk + "/delete", {
            method: 'delete'
        }).then(() => this._refreshData());
    }

    _refreshData() {
        fetch('http://localhost:8080/nodes')
            .then(response => response.json())
            .then(
                data => this.setState(
                    {
                        nodes: data
                    }
                )
            );

    }

    render() {
        return (
            <div>
                <ul>
                    {
                        this.state.nodes.map(data => (
                            <li key={data.id} data-pk={ data.id }>
                                { data.name }

                                <ActionButton handleOnClick={this._handleClick} />
                            </li>
                        ))
                    }
                </ul>

                <div className="add">
                    <input />
                    <button onClick={ this._handleAdd }>Add</button>
                </div>
            </div>
        );
    }
}

export default ListOfNodes;

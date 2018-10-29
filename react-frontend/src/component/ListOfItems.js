import React, { Component } from 'react';
import Item from './Item'

class ListOfItems extends Component {
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
        // TODO This can probably DRY. refactor after feature complete
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

    _handleCreateChild(pk, inputValue) {
        // TODO This can probably DRY. Refactor after feature complete.
        fetch("http://localhost:8080/nodes/" + pk +"/child-node/new", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "name": inputValue
            })
        }).then(() => this._refreshData());
    }

    _handleEdit(pk, inputValue) {
        // TODO This can probably DRY. Refactor after feature complete.
        fetch("http://localhost:8080/nodes/" + pk +"/update", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "name": inputValue
            })
        }).then(() => this._refreshData());
    }

    _handleClick(event) {
        let button = event.target.closest("button");

        if (button) {
            var action = button.className;

            let item = event.target.closest("span.item");
            let pk = item.getAttribute("data-pk");

            if (action === "delete") {
                if (window.confirm("Are you sure?")) {
                    this._handleDelete(pk);
                }
            }
            else if (action === "create-child") {
                // TODO This can probably DRY. Refactor after feature complete.
                let container = event.target.closest("span");
                let input = container.querySelector("input");
                let inputValue = input.value;

                if (inputValue === "") {
                    return;
                }

                this._handleCreateChild(pk, inputValue);

                input.value = "";
            }
            else if (action === "edit") {
                // TODO This can probably DRY. refactor after feature complete
                let container = event.target.closest("span");
                let input = container.querySelector("input");
                let inputValue = input.value;

                if (inputValue === "") {
                    return;
                }

                this._handleEdit(pk, inputValue);

                input.value = "";

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

    renderItems(data) {
        if (data.hasOwnProperty("child-node")) {
            return (
                <li key={data.pk}>
                    <Item data={data} handleClick={this._handleClick} />

                    <ul>
                        {
                            this.renderItems(data["child-node"])
                        }
                    </ul>
                </li>
            )
        }

        return (
            <li key={data.pk}>
                <Item data={data} handleClick={this._handleClick} />
            </li>
        )
    }

    render() {
        return (
            <div>
                <ul>
                    {
                        this.state.nodes.map(data => this.renderItems(data))
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

export default ListOfItems;

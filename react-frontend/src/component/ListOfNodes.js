import React, { Component } from 'react';
import ActionButton from './ActionButton';

class ListOfNodes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            nodes: []
        }

        this._handleClick = this._handleClick.bind(this);
        this._refreshData = this._refreshData.bind(this);
    }

    componentDidMount() {
        this._refreshData();
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
            </div>
        );
    }
}

export default ListOfNodes;

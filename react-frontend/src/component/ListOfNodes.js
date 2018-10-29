import React, { Component } from 'react';

class ListOfNodes extends Component {
    constructor(props) {
        super(props);

        this.state = {
            nodes: []
        }
    }

    componentDidMount() {
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
            <ul>
                {
                    this.state.nodes.map(node => (
                        <li>{node.name}</li>
                    ))
                }
            </ul>
        );
    }
}

export default ListOfNodes;

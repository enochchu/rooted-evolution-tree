import React, { Component } from 'react';
import ActionButton from './ActionButton';

class Item extends Component {
    render() {
        let data = this.props.data;

        debugger;
        console.log(data);

        return (
            <li key={data.id} data-pk={ data.id }>
                { data.name }

                <ActionButton handleOnClick={this.props.handleClick} />
            </li>
        );
    }
}

export default Item;

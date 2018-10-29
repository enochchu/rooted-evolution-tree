import React, { Component } from 'react';
import ActionButton from './ActionButton';

/*
 * TODO: Parent node detection could be better?
 */

class Item extends Component {
    render() {
        let data = this.props.data;

        return (
            <span className="item" data-pk={ data.id }>
                { data.name }

                <ActionButton handleOnClick={this.props.handleClick} delete={(data["parent-node-id"] !== -1)}/>
            </span>
        );
    }
}

export default Item;

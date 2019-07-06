import React from 'react'
import {Modal} from 'antd'
import banana from './banana.gif'

class EasterEgg extends React.Component {
  render() {
    const {visible} = this.props;
    return (
      <Modal visible={visible} footer={null} title="自由的气息" closable={false} style={{textAlign:"center"}}>
        <img src={banana} alt="banana"></img>
      </Modal>
    )
  }
}

export default EasterEgg;
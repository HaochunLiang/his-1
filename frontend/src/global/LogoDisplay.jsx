import React from 'react';
import logo from './logo.png'
import EasterEgg from './EasterEgg'

class LogoDisplay extends React.Component {
  state={
    clickCount:0
  }

  render() {
    return(
      <div style={{textAlign:'center',margin:'0 auto'}}>
        <img 
          onClick={()=>{this.setState({clickCount:this.state.clickCount+1})}}
          style={{padding:'50px',textAlign:'center'}} 
          alt="his-logo" 
          src={logo} />

        <EasterEgg visible={this.state.clickCount>=10}/>
      </div>
    )
  }
} 

export default LogoDisplay;
import React from 'react';
import {Select} from 'antd';

const {Option} = Select;

class Demo extends React.Component {

  onChange=(v1,v2,v3)=>{
    console.log('onChange ',v1,v2,v3)
  }

  onFocus=(v1,v2,v3)=>{
    console.log('onFocus ',v1,v2,v3)
  }

  onBlur=(v1,v2,v3)=>{
    console.log('onBlur ',v1,v2,v3)
  }

  onSearch=(str)=>{
    console.log('onSearch ',str)
    setTimeout(()=>{
      this.loadOption(str)
    },300)
  }

  loadOption=(str)=>{
    const {allData} = this.state;
    const newData = allData.filter(x=>x.name.startsWith(str));
    this.setState({optionData:newData})
  }

  state = {
    optionData:[],
    allData:[]
  }

  componentDidMount=()=>{
    var allData = []
    for(var i=0;i<1000;i++)
      allData.push({
        id:i,name:i+"_"
      })
    this.setState({allData})
  }

  render(){
    const {optionData} = this.state;
    const {onChange,onFocus,onBlur,onSearch} = this;

    return (
      <Select
        showSearch
        style={{ width: 200 }}
        placeholder="Select a person"
        optionFilterProp="children"
        onChange={onChange}
        onFocus={onFocus}
        onBlur={onBlur}
        onSearch={onSearch}
    >
      {optionData.map(x=>(<Option key={x.id} value={x.id}>{x.name}</Option>))}
    </Select>)
  }
}


export default Demo;







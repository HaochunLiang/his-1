import React from 'react';
import {Card, Table, Input, Button} from 'antd';
import DetailDrawer from './DetailDrawer';

class HistoryCard extends React.Component {

  state = { 
    drawerVisible: false,
    drawerData:null,
    searchText:''
  };

  showDrawer = (data) => {
    console.log('drawer show',data);
    this.setState({
      drawerVisible: true,
      drawerData:data
    });
  };

  hideDrawer = () => {
    this.setState({drawerVisible: false});
  };

  handleSearchInputChange=e=>{
    this.setState({searchText:e.target.value})
  }

  column = [
    {
      title:"病历号",
      dataIndex:"medical_record_id"
    },{
      title:"姓名",
      dataIndex:"patient_name"
    },{
      title:"性别",
      dataIndex:"gender",
      render:(str)=>(str==="male"?<span>男性</span>:<span>女性</span>)
    },{
      title:"日期",
      dataIndex:"consultation_date"
    },{
      title:"医生",
      dataIndex:"outpatient_doctor_name"
    },{
      title:"看诊科室",
      dataIndex:"department_name"
    },{
      title:"操作",
      render:(data)=>(<Button bill={data} type="primary" onClick={()=>{this.showDrawer(data)}}>详情</Button>)
    }
  ]

  render() {

    return(<Card title={
        <div>
          <div style={{float:'left',paddingTop:5}}>
            <span>查询历史挂号记录</span>
          </div>
          <div style={{float:'right'}}>
            <Input 
              placeholder="输入病历号" 
              style={{width:'300px'}} 
              onChange={this.handleSearchInputChange.bind(this)}
              value={this.state.searchText}
            ></Input>
            <Button type="primary" onClick={()=>{this.props.searchHistory(parseInt(this.state.searchText))}}>搜索</Button>
          </div>
        </div>
      }>
        <Table columns={this.column} dataSource={this.props.data}/>
        <DetailDrawer
          visible={this.state.drawerVisible}
          data={this.state.drawerData}
          onClose={this.hideDrawer.bind(this)}
          withdrawNumber={
            (id)=>{this.props.withdrawNumber(id,(succ)=>{
              if(succ) 
                this.setState({drawerVisible:false})
            })}
          }
        />
      </Card>)
  }
}

export default HistoryCard;


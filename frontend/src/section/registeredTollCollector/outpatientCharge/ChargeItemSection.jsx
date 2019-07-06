import React from 'react';
import {Modal,Tabs,Input,Button, Table, DatePicker} from 'antd';

const { TabPane } = Tabs;
const confirm=Modal.confirm;
const {RangePicker} = DatePicker;

class ChargeItemSection extends React.Component {

  state={
    charges_id_list:[],
    should_pay:0,
    truely_pay:0,
    start_date:"",
    end_date:""
  }

  chargeItemColumn = [
    {
      title:"病历号",
      dataIndex:'medical_record_id'
    },{
      title:"缴费名称",
      dataIndex:'item_name'
    },{
      title:"价格",
      dataIndex:'cost'
    },{
      title:"数量",
      dataIndex:'quantity'
    },{
      title:"执行科室",
      dataIndex:'excute_department'
    },{
      title:"费用类型",
      dataIndex:'expense_classification'
    },{
      title:"创建时间",
      dataIndex:'create_time'
    },{
      title:"状态",
      dataIndex:'status'
    }
  ]

  chargedItemColumn = [{
    title:"病历号",
    dataIndex:'medical_record_id'
  },{
    title:"缴费名称",
    dataIndex:'item_name'
  },{
    title:"价格",
    dataIndex:'cost'
  },{
    title:"数量",
    dataIndex:'quantity'
  },{
    title:"执行科室",
    dataIndex:'excute_department'
  },{
    title:"费用类型",
    dataIndex:'expense_classification'
  },{
    title:"创建时间",
    dataIndex:'create_time'
  },{
    title:"状态",
    dataIndex:'status'
  },{
    title:"操作",
    render:(data)=>(<Button type="primary" disabled={data.status==="已退费"} onClick={()=>{
      const _this = this;
      const {start_date,end_date} = _this.state;
      confirm({
        title: '收费',
        content: `你确定要退费吗？`,
        okText: '确定',
        okType: 'danger',
        cancelText: '取消',
        onOk() { _this.props.withdraw(data.medical_record_id,data.id,start_date,end_date)},
        onCancel() {console.log('Cancel clicked');},
      });
    }}>退费</Button>)
  }]
  

  rowSelection = {
    onChange: (selectedRowKeys, selectedRows) => {
      //console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      var should_pay = 0;
      selectedRows.map(x=>should_pay += x.cost*x.quantity);
      this.setState({
        should_pay:should_pay,
        charges_id_list:selectedRowKeys
      })
    },
  }

  handleTrulyPayInputChange=e=>{
    console.log(e.target.value)
    this.setState({truely_pay:e.target.value})
  }

  render() {
    const state = this.state;
    const {searchHistoryChargeRecord,medical_record_id} = this.props;

    var {chargeItems,chargedItems} = this.props;
    //过滤掉cost=0的，一般是因退药导致
    chargedItems = chargedItems.filter(x=>x.cost>0)

    return(
    <Tabs defaultActiveKey="1">
      <TabPane tab="待缴费" key="1">
        <Table 
          dataSource={chargeItems} 
          rowSelection={this.rowSelection}
          columns={this.chargeItemColumn}/>
          {this.props.loading?null:
          <div style={{float:'right'}}>
            <Button 
              type="danger" size="large"
              style={{float:'right',marginTop:'35px'}}
              disabled={!(state.should_pay>0 && state.truely_pay>=state.should_pay)}
              onClick={()=>{
                this.setState({
                  should_pay:0,
                  truely_pay:0
                })
                this.props.charge({
                  medical_record_id:parseInt(this.props.medical_record_id),
                  charges_id_list:state.charges_id_list,
                  should_pay:state.should_pay,
                  truely_pay:state.truely_pay,
                  retail_fee:parseFloat(state.truely_pay-state.should_pay)
                })
              }}>
                收费</Button>
            <span style={{float:'right',marginRight:'50px'}}>找零：<span style={{fontSize:'60px'}}>{(state.truely_pay-state.should_pay).toFixed(2)}</span>元</span>
            <span style={{float:'right',marginRight:'50px'}}>应收：<span style={{fontSize:'60px'}}>{(state.should_pay).toFixed(2)}</span>元</span>
            <Input 
              onChange={this.handleTrulyPayInputChange.bind(this)} 
              placeholder="输入应收费用" value={state.truely_pay} 
              style={{float:'right',width:'200px',marginTop:'40px',marginRight:'50px'}} 
              addonBefore="实收" addonAfter="元"/>
          </div>}
          
      </TabPane>
      <TabPane tab="历史记录" key="2" disabled={medical_record_id===-1}>
        <RangePicker onChange={(v1,v)=>{
          this.setState({
            start_date:v[0]+' 00:00:00',
            end_date:v[1]+' 00:00:00'
          })
          searchHistoryChargeRecord(medical_record_id,v[0]+' 00:00:00',v[1]+' 00:00:00')
        }}/>
        <Table dataSource={chargedItems} columns={this.chargedItemColumn}/>
      </TabPane>
    </Tabs>)
  }
}


export default ChargeItemSection;

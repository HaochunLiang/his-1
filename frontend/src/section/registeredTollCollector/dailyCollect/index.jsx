import React from 'react';
import {Button,Col,Row,Card,Table,Layout,DatePicker,Form,Spin,Modal} from 'antd';
import API from '../../../global/ApiConfig';
import moment from 'moment';
import Message from '../../../global/Message';

const {RangePicker} = DatePicker;
const {Content} = Layout;

class DailyCollectSection extends React.Component {
  state={
    historyTableSelectionLoading:true,
    historyTableSelection:[],
    historyTableSelectionDisplay:[],

    historyTableLoading:true,
    historyTableData:{
      list:[]
    },

    newDailyCollectModalDisplay:false
  }

  componentDidMount() {this.init()}

  handleDailyCollectModalOpen=()=>{this.setState({newDailyCollectModalDisplay:true})}
  handleDailyCollectModalClose=()=>{this.setState({newDailyCollectModalDisplay:false})}

  handleNewDailySubmit = e => {
    e.preventDefault();
    const _this = this;
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        console.log(values)
        const time = values.time.map(x=>x.format('YYYY-MM-DD'));
        console.log('Received values of form: ', time);
        _this.submitDailyCollect({start_time:time[0],end_time:time[1]})
      };
    });
  }

  columns = [{
    title:'开始时间',
    dataIndex:'start_time',
  },{
    title:'结束时间',
    dataIndex:'end_time'
  },{
    title:"操作",
    render:(data)=>(<Button onClick={()=>{this.viewHistoryDailyCollectDetail(data.id)}}>查看</Button>)
  }]


  init=()=>{
    API.request(API.outpatientWorkstation.dailyCollect.getHistoryList)
    .ok((data)=>{
      for(var i=0;i<data.length;i++) data[i].key = data[i].id;
      console.log(data)
      this.setState({
        historyTableSelection:data,
        historyTableSelectionDisplay:data,
        historyTableSelectionLoading:false
      })
    }).submit();
  }

  viewHistoryDailyCollectDetail=(daily_collect_id)=>{
    API.request(API.outpatientWorkstation.dailyCollect.dailyCollectDetail,{daily_collect_id})
    .ok((list)=>{
      list.forEach(x=>x.key = x.id);
      this.setState({
        historyTableData:{
          list:list
        }
      })
    }).submit();
  }

  submitDailyCollect=(reqData)=>{
    API.request(API.outpatientWorkstation.dailyCollect.dailyCollectRequest,reqData)
    .ok((data)=>{
      Message.success('结算成功!');
      this.handleDailyCollectModalClose();
      this.viewHistoryDailyCollectDetail(data.id)
    }).submit();
  
  }

  onDatePickerChange=(date,dateString)=>{
    if(dateString[0]==='') {
      this.setState({historyTableSelectionDisplay:this.state.historyTableSelection})
    } else {
      dateString = dateString.map(x=>x+' 00:00')
      console.log(dateString)
      this.setState({
        historyTableSelectionDisplay:this.state.historyTableSelection.filter(x=>x.start_time >= dateString[0] && dateString[1]>= x.end_time)
      })
    }
  }

  render() {
    const state = this.state;
    const getFieldDecorator = this.props.form.getFieldDecorator;
    //const me = this.props.me;
    return (<Content style={{ margin: '0 16px',paddingTop:5 }}>
      <Row>
        <Col span={8}>
          <Card style={{minHeight:'850px'}} title={
          <div>
            <p style={{float:'left',paddingBottom:0}}>历史日结列表</p>
            <Button 
              type="primary"
              style={{float:'right'}} 
              onClick={this.handleDailyCollectModalOpen.bind(this)}>
              新日结</Button>
          </div>}>
            {state.historyTableSelectionLoading?
            <Spin style={{textAlign:'center'}}>载入中...</Spin>:
            <div>
              <span>时间:&nbsp;&nbsp;</span>
              <RangePicker onChange={this.onDatePickerChange.bind(this)} /> 
              <Table columns={this.columns} dataSource={state.historyTableSelectionDisplay}/>
            </div>}
          </Card>
        </Col>
          
        <Col span={16}>
          <Card title={"历史日结详情"} style={{minHeight:'850px'}}>

            <Table columns={[
              {
                title:"收费编号",
                dataIndex:"id"
              },{
                title:"类型",
                dataIndex:"type"
              },{
                title:"数量",
                dataIndex:"quantity"
              },{
                title:"病历号",
                dataIndex:"medical_record_id"
              },{
                title:"应收",
                dataIndex:"should_pay"
              },{
                title:"实收",
                dataIndex:"truely_pay"
              },{
                title:"找零",
                dataIndex:"retail_fee"
              },{
                title:"创建日期",
                dataIndex:"create_time"
              }
            ]} dataSource={state.historyTableData.list}></Table>

            
          </Card>
        </Col>
      </Row>

      <Modal
        closable
        title="日结"
        visible={state.newDailyCollectModalDisplay}
        onCancel={this.handleDailyCollectModalClose}
        onOk={this.handleDailyCollectModalClose}
        destroyOnClose
        footer={null}
      >
        <Form layout="inline" onSubmit={this.handleSubmit}>
          <Form.Item label="时间">
            {getFieldDecorator('time', {
              initialValue: [moment(),moment()],
              rules: [{ required:true}],
            })(<RangePicker/>)}
          </Form.Item>
          <Button type="danger" htmlType="submit" onClick={this.handleNewDailySubmit.bind(this)}>日结</Button>
        </Form>
      </Modal>

    </Content>)
  }
}

export default Form.create({name:'ee'})(DailyCollectSection); 


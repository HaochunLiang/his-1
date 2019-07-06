import React from 'react';
import {Layout,Typography, Form, Table, Button, Card,Descriptions,DatePicker} from 'antd'
import { Chart,Geom,Axis,Tooltip} from "bizcharts";
import API from '../../../global/ApiConfig';

const { RangePicker } = DatePicker;
const {Content} = Layout;


class PersonalWorkloadStatistic extends React.Component {

  state = {
    tableData:[]
  };//null

  columns=[
    {title:"检查编号",dataIndex:"exam_item_id"},
    {title:"检查员编号",dataIndex:"user_id"},
    {title:"结果",dataIndex:"result"},
    {title:"建议",dataIndex:"advice"},
    {title:"创建时间",dataIndex:"create_time"},
  ]


  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const range = values.range;
        console.log(range);
        const start_date = range[0].format('YYYY-MM-DD HH:mm:ss');
        const end_date = range[1].format('YYYY-MM-DD HH:mm:ss');
        console.log('Received values of form: ', start_date,end_date);
        API.request(API.financialAdmin.workloadStatistic.doctorOfTechnology,{start_date,end_date})
        .ok(tableData=>{
          tableData.forEach(x=>x.key = x.id)
          this.setState({tableData})
        }).submit();
      }
    });
  };

  
  render() {
    const { getFieldDecorator } = this.props.form;
    var {tableData} = this.state;
    const {me} = this.props;

    tableData.forEach(x=>{
        x.key = x.exam_item_id;
        x.date = x.create_time.substr(5,5)
      }
    );

    //按照date归类统计
    const chartsData  = tableData.reduce((groups,item,index,arr)=>{
      const groupFound = groups.find(record => record.date === item.date)
      if(groupFound) {
        //增加
        groupFound.num += 1;
      } else {
        //插入
        groups.push({
          date:item.date,
          num:1
        })
      }
      return groups;
    },[])

    const cols = {
      num: {
        tickInterval: 20
      }
    };

    console.warn(chartsData)

    return(
      <Content style={{ margin: '0 16px',paddingTop:20 }}>
       <Card>

       <Typography.Title style={{textAlign:'center'}} level={3}>医技医生个人工作量统计</Typography.Title>

       <Form onSubmit={this.handleSubmit.bind(this)} layout="inline">
          <Form.Item label="日期" hasFeedback>
            {getFieldDecorator('range', {
              rules: [
                {
                  required: true,
                  message: '选择日期!',
                }
              ],
            })(<RangePicker renderExtraFooter={() => 'extra footer'} showTime />)}
          </Form.Item>
          <Button type="primary" icon="search" htmlType="submit">查询</Button>
        </Form><br/>

        <Descriptions title="我的信息" bordered column={3}>
          <Descriptions.Item label="用户名">{me.username}</Descriptions.Item>
          <Descriptions.Item label="真实姓名">{me.real_name}</Descriptions.Item>
          <Descriptions.Item label="科室">{me.department_name}</Descriptions.Item>
          <Descriptions.Item label="角色">{me.role_name}</Descriptions.Item>
          <Descriptions.Item label="职称">{me.title}</Descriptions.Item>
        </Descriptions>

        <Table 
          title={()=><b>历史检查记录</b>}
          columns={this.columns} 
          dataSource={tableData}/>

        {(tableData.length===0)?null:
        <div>
          <Typography.Title level={3}>总检查人次：{tableData.length}</Typography.Title><br/>
          
          <br/>
          <Chart height={400} data={chartsData} scale={cols} forceFit>
            <Axis name="date" />
            <Axis name="num" />
            <Tooltip
              crosshairs={{
                type: "y"
              }}
            />
            <Geom 
              type="area" 
              adjustType="stack" 
              color={["country", ["#ffd54f", "#ef6c00", "#1976d2", "#64b5f6"]]} 
              position="date*num" />
          </Chart>
        </div>}

       </Card>
      </Content>)
  }
}

export default Form.create({ name: 'form' })(PersonalWorkloadStatistic);


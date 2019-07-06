import React from 'react';
import {Layout,Typography, Form, Table, Button,DatePicker,Descriptions, Card, Row, Col} from 'antd'
import { Chart,Geom,Axis,Tooltip,Coord,Legend} from "bizcharts";
import API from '../../global/ApiConfig'


const { RangePicker } = DatePicker;
const {Content} = Layout;

class OutpatientDoctorWorkloadStatistic extends React.Component {

  state = {
    tableData:[]
  };//null

  columns=[
    {title:"病历号",dataIndex:"medical_record_id"},
    {title:"病人姓名",dataIndex:"patient_name"},
    {title:"病人性别",dataIndex:"gender",render:(text)=>(text==="male"?"男":"女")},
    {title:"年龄",dataIndex:"age"},
    {title:"挂号来源",dataIndex:"registration_source"},
    {title:"挂号费",dataIndex:"cost"},
    {title:"状态",dataIndex:"status"},
    {title:"病历号",dataIndex:"consultation_date"},
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
        API.request(API.financialAdmin.workloadStatistic.outpatientDoctor,{start_date,end_date})
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

    var allCost = 0;
    tableData.forEach(x=>{
        x.key = x.medical_record_id;
        x.date = x.consultation_date.substr(5,5)
        allCost += x.cost;
        if(x.age < 10 ) x.range="<10";
        else if(x.age>=10 && x.age < 20 ) x.range="10~20";
        else if(x.age>=20 && x.age < 30 ) x.range="20~30";
        else if(x.age>=30 && x.age < 40 ) x.range="30~40";
        else if(x.age>=30 && x.age < 50 ) x.range="40~50";
        else if(x.age>=50 && x.age < 60 ) x.range="50~60";
        else x.range=">=60";
      }
    );

    //按照date归类统计
    const chartsData1  = tableData.reduce((groups,item,index,arr)=>{
      const groupFound = groups.find(record => record.date === item.date)
      if(groupFound) {
        //增加
        groupFound.cost += item.cost;
      } else {
        //插入
        groups.push({
          date:item.date,
          cost:item.cost
        })
      }
      return groups;
    },[])

    //按照age
    var chartsData2  = tableData.reduce((groups,item,index,arr)=>{
      const groupFound = groups.find(record => record.range === item.range)
      if(groupFound) {
        //增加
        groupFound.num += 1;
      } else {
        //插入
        groups.push({
          range:item.range,
          num:1
        })
      }
      return groups;
    },[])


    const cols = {
      cost: {
        tickInterval: 20
      }
    };


    console.warn(chartsData2)

    return(
      <Content style={{ margin: '0 16px',paddingTop:20 }}>
       <Card>

       <Typography.Title style={{textAlign:'center'}} level={3}>门诊医生个人工作量统计</Typography.Title>

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
          title={()=><b>历史挂号统计</b>}
          columns={this.columns} 
          dataSource={tableData}/>

        {(tableData.length===0)?null:
        <div>
          <Typography.Title level={3}>总共挂号费：{allCost}</Typography.Title><br/>
          
          <br/>
          <Row>
            <Col span={12}>
              <Chart height={400} data={chartsData1} scale={cols} forceFit>
                <Axis name="date" />
                <Axis name="cost" />
                <Tooltip
                  crosshairs={{
                    type: "y"
                  }}
                />
                <Geom type="interval" position="date*cost" />
              </Chart>
            </Col>

            <Col span={12}>
              <Chart height={400} data={chartsData2} padding="auto" forceFit>
                <Coord type="polar" />
                <Tooltip />
                <Legend
                  position="right"
                />
                <Geom
                  type="interval"
                  color="range"
                  position="range*num"
                  style={{
                    lineWidth: 1,
                    stroke: "#fff"
                  }}
                />
              </Chart>
            </Col>
          </Row>
        </div>}

       </Card>
      </Content>)
  }
}

export default Form.create({ name: 'form' })(OutpatientDoctorWorkloadStatistic);


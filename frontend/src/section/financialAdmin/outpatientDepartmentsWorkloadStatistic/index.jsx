import React from 'react';
import {Layout,Typography, Form, Table, Button, Card, Row, Col,Empty} from 'antd'
import { DatePicker } from 'antd';
import { Chart,Geom,Axis,Tooltip} from "bizcharts";
import API from '../../../global/ApiConfig';

const cols = {
  sales: {
    tickInterval: 20
  }
};

const { RangePicker } = DatePicker;
const {Content} = Layout;

class OutpatientDepartmentsWorkloadStatisticSection extends React.Component {

  state={
    loading:true,
    departmentStatistic:[],
    typeStatistic:[]
  }

  loadStatistic=(start_date,end_date)=>{
    API.request(API.financialAdmin.workloadStatistic.department,{
      start_date,end_date
    }).ok(data=>{
      data.forEach(x=>x.key = x.department_id)
      this.setState({
        departmentStatistic:data,
        loading:false
      })
    }).submit();

    API.request(API.financialAdmin.workloadStatistic.typeStatistic,{
      start_date,end_date
    }).ok(data=>{
      this.setState({
        typeStatistic:data,
        loading:false
      })
    }).submit();
  }

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        const range = values.range;
        console.log(range);
        const start_date = range[0].format('YYYY-MM-DD');
        const end_date = range[1].format('YYYY-MM-DD');
        //console.log(start_date,end_date)
        this.loadStatistic(start_date+' 00:00:00',end_date+' 23:59:59')
      }
    });
  };

  render() {
    const {departmentStatistic,typeStatistic,loading} = this.state;
    const { getFieldDecorator } = this.props.form;

    return(
      <Content style={{ margin: '0 16px',paddingTop:10 }}>
        <Card style={{minHeight:'800px'}}>
        <Typography.Title style={{textAlign:'center'}} level={3}>科室工作量统计</Typography.Title>

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
        </Form>

        {loading?<Empty image={Empty.PRESENTED_IMAGE_SIMPLE}/>:
        <div>
          <br/>
          <Table columns={[
            {title:"科室ID",dataIndex:"department_id"},
            {title:"科室名",dataIndex:"department_name"},
            {title:"总费用",dataIndex:"fee"},
            {title:"发票数",dataIndex:"count"},
          ]} dataSource={departmentStatistic} pagination={false}></Table>
          
          <br/>
          <Typography.Title style={{textAlign:'center'}} level={3}>科室总收入统计图</Typography.Title>
          <Row>
            <Col span={12}>
              <Chart height={400} data={departmentStatistic} scale={cols} forceFit>
                <Axis name="department_name" />
                <Axis name="fee" />
                <Tooltip
                  crosshairs={{
                    type: "y"
                  }}
                />
                <Geom type="interval" position="department_name*fee" />
              </Chart>
            </Col>
            <Col span={12}>
              <Chart height={400} data={typeStatistic} scale={cols} forceFit>
                <Axis name="type" />
                <Axis name="fee" />
                <Tooltip
                  crosshairs={{
                    type: "y"
                  }}
                />
                <Geom type="interval" position="type*fee" />
              </Chart>
            </Col>
          </Row>
        </div>}

        </Card>
    </Content>)
  }
}

export default Form.create({ name: 'form' })(OutpatientDepartmentsWorkloadStatisticSection);


import React from 'react';

import {Row,Layout, Table} from 'antd'

import {
    Chart,
    Geom,
    Axis,
    Tooltip
  } from "bizcharts";

const {Content} = Layout;

const mainColumn = [
  {
    title:'用户',
    key:1,
    dataIndex:'user'
  },{
    title:'上午',
    key:2,
    dataIndex:'am'
  },{
    title:'下午',
    key:3,
    dataIndex:'pm'
  },{
    title:'全天',
    key:4,
    dataIndex:'all'
  }
]

const data2=[{
  shift:'user1',
  am:0,
  pm:0,
  all:5,
},{
  shift:'上午',
  am:0,
  pm:0,
  all:2,
}]

const data = [
    {
      user: "user1",
      times: 5
    },
    {
      user: "user2",
      times: 2
    }
  ];
  const cols = {
    sales: {
      tickInterval: 20
    }
  };


class SchedulingStatistics extends React.Component {
    render() {
        return(
          <Content style={{ margin: '0 16px',paddingTop:5 }}>

          <Chart height={400} data={data} scale={cols} forceFit>
          <Axis name="user" />
          <Axis name="times" />
          <Tooltip
            crosshairs={{
              type: "y"
            }}
          />
          <Geom type="interval" position="user*times" />
        </Chart>

        <Row>    
            <Table columns={mainColumn} dataSource={data2}/>
        </Row>

          </Content>)
      }
}

export default SchedulingStatistics;
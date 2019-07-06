import React from 'react';
import ReactToPrint from 'react-to-print';

import {Card,Typography, Divider, Button} from 'antd';
const { Title } = Typography;

class RegistrationBill extends React.Component {

  render() {
    //bill from props
    const bill=this.props.bill
    console.log('bill',bill)
    return (
      <Card style={{margin:20,width:'300px'}}>
        <div style={{margin:20}}>
        <Title style={{textAlign:'center'}}  level={3}>门诊挂号单</Title>
        <Divider/>
          <p><b>病 例 号：</b>{bill.medical_record_id}</p>
          <p><b>姓    名：</b>{bill.patient_name}</p>
          <p><b>科    室：</b>{bill.department_name}</p>
          <p><b>挂号等级：</b>{bill.registration_level_name}</p>
          <p><b>医    生：</b>{bill.outpatient_doctor_name}</p>
          <p><b>金    额：</b>{bill.cost}</p>
          <p><b>日    期：</b>{bill.create_time}</p>
        <Divider style={{marginTop:0,marginBottom:10}}/>
        </div>
      </Card>
    );
  }
}

class RegistrationBillPrint extends React.Component {
  render() {
    return (
      <div style={{textAlign:'center'}}>
        <RegistrationBill 
          ref={el => (this.componentRef = el)}
          bill={this.props.bill} />
        <ReactToPrint
          trigger={() => <Button type="primary">打印</Button>}
          content={() => this.componentRef}
        />
      </div>
    );
  }
}

export default RegistrationBillPrint;
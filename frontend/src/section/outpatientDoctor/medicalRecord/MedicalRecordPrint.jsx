import React from 'react';
import ReactToPrint from 'react-to-print';

import {Card,Typography, Divider, Button, Modal,Row,Col} from 'antd';
const { Title } = Typography;

class PrintField extends React.Component {

  render() {
    const {data} = this.props;
    const {medicalRecord,registration} = data;

    return (
      <Card style={{margin:20,width:'900px'}}>
        <div style={{margin:20}}>
        <Title style={{textAlign:'center'}}  level={3}>NEU XXXXXXX 医院</Title>
        <Typography.Paragraph style={{textAlign:'center'}}>
          <b>姓名：</b>{registration.patient_name}<span style={{marginLeft:70}}/>
          <b>性别：</b>{registration.gender==="male"?"男":"女"}<span style={{marginLeft:70}}/>
          <b>年龄：</b>{registration.age}
        </Typography.Paragraph>
        <Divider style={{marginTop:0,marginBottom:10}}/>
        <Typography.Paragraph style={{textAlign:'center'}}>
          <b>创建日期：</b>{registration.consultation_date}<span style={{marginLeft:70}}/>
          <b>病历：</b>{medicalRecord.id}<span style={{marginLeft:70}}/>
        </Typography.Paragraph>
        <Divider style={{marginTop:0,marginBottom:10}}/>

        <Row>
          <Col span={4}><b>主诉：</b></Col>
          <Col span={20}><Typography.Paragraph>{medicalRecord.chief_complaint}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>现病史：</b></Col>
          <Col span={20}><Typography.Paragraph>{medicalRecord.current_medical_history}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>既往史：</b></Col>
          <Col span={20}><Typography.Paragraph>{medicalRecord.past_history}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>过敏史：</b></Col>
          <Col span={20}><Typography.Paragraph>{medicalRecord.allergy_history}</Typography.Paragraph></Col>
        </Row>
        <Row>
          <Col span={4}><b>体格检查：</b></Col>
          <Col span={20}><Typography.Paragraph>{medicalRecord.physical_examination}</Typography.Paragraph></Col>
        </Row>

        {medicalRecord.diagnose?
        <Row>
          <Col span={4}><b>诊断：</b></Col>
          <Col span={20}><Typography.Paragraph>
          {medicalRecord.diagnose.western_diagnose
            .map(d=>(<p key={d.disease_id}>{d.disease_name}&nbsp;&nbsp;&nbsp;&nbsp;<b>{d.suspect?"疑似":""}</b></p>))}
          {medicalRecord.diagnose.chinese_diagnose
            .map(d=>(<p key={d.disease_id}>{d.disease_name}&nbsp;&nbsp;&nbsp;&nbsp;<b>{d.syndrome_differentiation}</b></p>))}
          </Typography.Paragraph></Col>
        </Row>
        :null}
        
       
        <Divider/>
        <Typography.Paragraph><b>以下空白</b></Typography.Paragraph>
        <Typography.Paragraph style={{textAlign:'center'}}>
          <b>患者签字：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u></b>
          <b>医生签字：<u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u></b>
        </Typography.Paragraph>
        <Typography><b>注意：治疗期间出现新症状或病情加重请及时复诊，病情平稳，请按医生要求复诊！</b></Typography>
        </div>
    </Card>
    );
  }
}

class MedicalRecordPrint extends React.Component {
  render() {
    return (
      <div style={{textAlign:'center'}}>
        <Modal 
          visible={this.props.visible}
          closable
          onCancel={this.props.onCancel}
          destroyOnClose
          width={1000}
          footer={null}
          >
          <PrintField 
            ref={el => (this.componentRef = el)}
            data={this.props.data} />
          <ReactToPrint
            trigger={() => <Button type="primary">打印</Button>}
            content={() => this.componentRef}
          />
        </Modal>
      </div>
    );
  }
}

export default MedicalRecordPrint;
import React from 'react';
import {Descriptions, Typography,Col, Row, Table, Checkbox,Button} from 'antd';
import MedicalRecordPrint from './MedicalRecordPrint';

class MedicalRecordOverview extends React.Component {

  state={
    printVisible:false
  }

  openPrint=()=>{
    this.setState({printVisible:true})
  }

  closePrint=()=>{
    this.setState({printVisible:false})
  }

  render() {
    const {confirmDiagnose} = this.props;
    const {herbalMedicinePrescriptionList,patentMedicinePrescriptionList} = this.props;
    const {currentPatient} = this.props;

    const {printVisible} = this.state;
    const {medicalRecord} = currentPatient;
    const {chinese_diagnose,western_diagnose} = medicalRecord.diagnose;
    
    western_diagnose.forEach(x=>x.key = x.disease_id)
    chinese_diagnose.forEach(x=>x.key = x.disease_id)

    herbalMedicinePrescriptionList.forEach(prescription=>{
      prescription.key = prescription.id;
      //不显示amount为0的，这种一般是退药导致的
      prescription.prescription_item_list = prescription.prescription_item_list.filter(x=>x.amount >0 );
      prescription.prescription_item_list.forEach(x=>x.key=x.drug.id)
    });

    patentMedicinePrescriptionList.forEach(prescription=>{
      prescription.key = prescription.id;
      //不显示amount为0的，这种一般是退药导致的
      prescription.prescription_item_list = prescription.prescription_item_list.filter(x=>x.amount >0 );
      prescription.prescription_item_list.forEach(x=>x.key=x.drug.id)
    });

    return (
      <div style={{minHeight:'770px'}}>
        <Descriptions title={
          <div>
            <span style={{marginRight:'20px'}}>病历信息</span>
            <Button style={{marginRight:'20px'}} type="primary" size="small" icon="check" disabled={medicalRecord.status==='诊毕'} onClick={confirmDiagnose}>确诊</Button>
            <Button style={{marginRight:'20px'}}  type="dashed" size="small" icon="printer" onClick={this.openPrint.bind(this)}>打印</Button>
          </div>
        } column={2} size="small" bordered>
          <Descriptions.Item label="创建日期">{medicalRecord.create_time}</Descriptions.Item>
          <Descriptions.Item label="主诉">{medicalRecord.chief_complaint}</Descriptions.Item>
          <Descriptions.Item label="现病史">{medicalRecord.current_medical_history}</Descriptions.Item>
          <Descriptions.Item label="现病治疗情况">{medicalRecord.current_treatment_situation}</Descriptions.Item>
          <Descriptions.Item label="过敏史">{medicalRecord.allergy_history}</Descriptions.Item>
          <Descriptions.Item label="既往史">{medicalRecord.past_history}</Descriptions.Item>
          <Descriptions.Item label="体格检查">{medicalRecord.physical_examination}</Descriptions.Item>
        </Descriptions>

      <br/>
        <Row>
          <Col span={12}>
            <Typography.Title level={4}>西医诊断</Typography.Title>
          {(!western_diagnose||western_diagnose.length===0)?
            (<Typography.Paragraph>（无）</Typography.Paragraph>):
            (<Table 
              size="small" pagination={false}
              dataSource={western_diagnose}
              columns={[
                {title:"ICD编码",dataIndex:'disease_code'},
                {title:"疾病名称",dataIndex:'disease_name'},
                {title:"主症",dataIndex:'main_symptom',render:(main_symptom)=>(<Checkbox checked={main_symptom}/>)},
                {title:"疑似",dataIndex:'suspect',render:(suspect)=>(<Checkbox checked={suspect}/>)}
              ]}
            />)}
          </Col>
          <Col span={12}>
          <Typography.Title level={4}>中医诊断</Typography.Title>
          {(!chinese_diagnose||chinese_diagnose.length===0)?
            (<Typography.Paragraph>（无）</Typography.Paragraph>):
            (<Table 
              style={{padding:'10px'}}
              size="small" pagination={false}
              dataSource={chinese_diagnose}
              columns={[
                {title:"疾病编码",dataIndex:'disease_code'},
                {title:"疾病名称",dataIndex:'disease_name'},
                {title:"辩证分型",dataIndex:'syndrome_differentiation'},
              ]}
            />)}
          </Col>
        </Row>

        <Typography.Title level={4}>成药处方</Typography.Title>
        {patentMedicinePrescriptionList?<div>
          {patentMedicinePrescriptionList.map(prescription=>{
            if(prescription.status==="已提交")
            return (<Table
              key={prescription.id}
              title={()=>{return "处方号："+prescription.id}}
              size="small" pagination={false}
              dataSource={prescription.prescription_item_list}
              columns={[
                {title:"编号",dataIndex:"drug.code"},
                {title:"名称",dataIndex:"drug.name"},
                {title:"用法",dataIndex:"usage"},
                {title:"频次",dataIndex:"frequency"},
                {title:"数量",dataIndex:"amount"},
                {title:"天数",dataIndex:"day_count"},
                {title:"次数",dataIndex:"times"},
                {title:"状态",dataIndex:"status"},
                {title:"医嘱",dataIndex:"note"},
              ]}
            />);
              else return null}
        )}
        </div>:null}
        
      <br/>
      {herbalMedicinePrescriptionList?<div>
      <Typography.Title level={4}>草药处方</Typography.Title>
        {herbalMedicinePrescriptionList.map(prescription=>{
          if(prescription.status==="已提交")
          return (<Table
            key={prescription.id}
            title={()=>{return "处方号："+prescription.id}}
            size="small" pagination={false}
            dataSource={prescription.prescription_item_list}
            columns={[
              {title:"编号",dataIndex:"drug.code"},
              {title:"名称",dataIndex:"drug.name"},
              {title:"用法",dataIndex:"usage"},
              {title:"频次",dataIndex:"frequency"},
              {title:"数量",dataIndex:"amount"},
              {title:"天数",dataIndex:"day_count"},
              {title:"次数",dataIndex:"times"},
              {title:"状态",dataIndex:"status"},
              {title:"医嘱",dataIndex:"note"},
            ]}
          />);
            else return null}
        )}
        </div>:null}

        <MedicalRecordPrint 
          herbalMedicinePrescriptionList={herbalMedicinePrescriptionList}
          patentMedicinePrescriptionList={patentMedicinePrescriptionList}
          visible={printVisible} 
          onCancel={this.closePrint.bind(this)} 
          data={currentPatient}/>

      </div>
    )
  }
}

export default MedicalRecordOverview;
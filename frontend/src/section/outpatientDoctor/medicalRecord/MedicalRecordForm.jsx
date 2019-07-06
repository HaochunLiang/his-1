import React from 'react';
import {Typography,Collapse,Button} from 'antd'
import Message from '../../../global/Message';
import DiagnoseSelectionTable from './DiagnoseSelectionTable'
import MedicalRecordBasicForm from './MedicalRecordBasicForm';
import MedicalRecordPrint from './MedicalRecordPrint'

const {Panel} = Collapse;

class MedicalRecordForm extends React.Component {

  state={
    printModalVisible:false,
    printData:null
  }

  componentDidMount=()=>{this.props.onRef(this)}

  clearMedicalRecord=()=>{
    this.DiagnoseSelectionTable.clear();
    this.MedicalRecordBasicForm.clear();
  }

  //应用病历模板（不带诊断）
  applyMedicalRecordTemplate=(template)=>{
    this.MedicalRecordBasicForm.applyMedicalRecordTemplateData(template)
  }

  //应用诊断模板
  applyDiagnoseTemplate=(template)=>{
    this.DiagnoseSelectionTable.applyDiagnose(template)
  }

  //应用数据(用户的病历，诊断信息)
  applyMedialRecordData=(data)=>{
    this.MedicalRecordBasicForm.applyMedicalRecordData(data)
    this.DiagnoseSelectionTable.applyDiagnose(data.diagnose)
  }

  handleSubmit = (mode) => {
    const {currentPatient} = this.props;
    const {medicalRecord} = currentPatient;
    const medical_record_id = medicalRecord.id;
    
    const values = this.MedicalRecordBasicForm.formValues();
    //values.diagnose = this.DiagnoseSelectionTable.state.patientDiagnose;
    console.log('form operate mode:',mode);

    switch(mode) {
      case "clear":{
        Message.showConfirm('清空','你确认要清空病历吗？',
        ()=>{
          this.clearMedicalRecord();
        },()=>{})
        break;
      }
      case "store":{
        //暂存
        values.id = medical_record_id;
        values.diagnose = this.DiagnoseSelectionTable.patientDiagnoseData();
        this.props.updateMedicalRecord(values)
        break;
      }
      case "print":{
        const {currentPatient} = this.props;
        const {registration} = currentPatient;
        this.setState({
          printModalVisible:true,
          printData:{
            registration:registration,
            medicalRecord:values,
          }
        })
        break;
      }
      case "save":{
        //保存
        this.MedicalRecordBasicForm.submit((err,values) =>{
          if (!err) {
            values.id = medical_record_id;
            values.diagnose = this.DiagnoseSelectionTable.patientDiagnoseData();
            this.props.saveMedicalRecord(values)
          }
        });
        break;
      }
      case "saveAsTemplate":{
        this.props.openMedicalRecordTemplateEditor("new",values)
        break;
      }
      default:{
        console.error('known mode');
        break;
      }
    }
  };
  

  render() {
    const {allDiagnoses,disabled} = this.props;
    const {printModalVisible,printData} = this.state;

    return (
      <div style={{minHeight:'870px',marginRight:'10px'}} >
        <div>
          <Typography.Title level={4} style={{float:'left'}}>病历信息</Typography.Title>
          <Button icon="save" disabled={disabled} type="primary" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("store")}}>暂存</Button>
          <Button icon="save" disabled={disabled} type="primary" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("save")}}>保存</Button>
          <Button icon="delete" disabled={disabled} type="danger" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("clear")}}>清空</Button>
          <Button icon="save" disabled={disabled} type="danger" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("saveAsTemplate")}}>存为模板</Button>
          <Button icon="printer" disabled={disabled} type="dashed" style={{float:'right',marginRight:'10px'}} onClick={()=>{this.handleSubmit("print")}}>打印</Button>
          <MedicalRecordPrint 
            visible={printModalVisible}
            data={printData}
            onCancel={()=>{this.setState({printModalVisible:false})}}
          />
        </div><br/><br/>

        <Collapse defaultActiveKey={['1']} accordion >
          <Panel header="基本信息" key="1" disabled={disabled} forceRender>
            <MedicalRecordBasicForm
              disabled={disabled}
              onRef={(ref)=>{this.MedicalRecordBasicForm = ref}}
            />
          </Panel>

          <Panel header="初步诊断" key="2" disabled={disabled} forceRender>
            <DiagnoseSelectionTable 
              diagnoses={allDiagnoses}
              disabled={disabled}
              onRef={(ref)=>{this.DiagnoseSelectionTable = ref}}
            />
          </Panel>
        </Collapse>

      </div>
    );
  }
 
}

export default MedicalRecordForm;
import React from 'react';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import MedicalRecordOverview from './MedicalRecordOverview';

class CompleteDiagnose extends React.Component {

  state={
    patentMedicinePrescriptionList:[],
    herbalMedicinePrescriptionList:[]
  }

  componentDidMount=()=>{
    this.props.onRef(this)
    this.loadPrescription();
  }

  //加载处方
  loadPrescription=()=>{
    const {currentPatient} = this.props;
    const {registration} = currentPatient;
    const {medical_record_id} = registration;
    API.request(API.outpatientDoctor.prescription.allPrescription,{
      type:0,
      medical_record_id
    }).ok(patentMedicinePrescriptionList=>{
      this.setState({patentMedicinePrescriptionList})
    }).submit();
    API.request(API.outpatientDoctor.prescription.allPrescription,{
      type:1,
      medical_record_id
    }).ok(herbalMedicinePrescriptionList=>{
      this.setState({herbalMedicinePrescriptionList})
    }).submit();
  }

  //提交确诊
  confirmDiagnose=()=>{
    Message.showConfirm('确诊','你确认要确诊该患者吗，确诊后不可再修改！',()=>{
      this.props.completeDiagnose();
    })
  }

  render() {
    const {currentPatient} = this.props;
    const {medicalRecord} = currentPatient;
    const {chinese_diagnose,western_diagnose} = medicalRecord.diagnose;
    const {herbalMedicinePrescriptionList,patentMedicinePrescriptionList} = this.state;
    
    western_diagnose.forEach(x=>x.key = x.disease_id)
    chinese_diagnose.forEach(x=>x.key = x.disease_id)

    herbalMedicinePrescriptionList.forEach(prescription=>{
      prescription.key = prescription.id;
      prescription.prescription_item_list.forEach(x=>x.key=x.drug.id)
    });

    patentMedicinePrescriptionList.forEach(prescription=>{
      prescription.key = prescription.id;
      prescription.prescription_item_list.forEach(x=>x.key=x.drug.id)
    });

    return (
      <div>
        <MedicalRecordOverview
          currentPatient={currentPatient}
          patentMedicinePrescriptionList={patentMedicinePrescriptionList}
          herbalMedicinePrescriptionList={herbalMedicinePrescriptionList}
          confirmDiagnose={this.confirmDiagnose.bind(this)}
        />
      </div>
    )
  }
}

export default CompleteDiagnose;
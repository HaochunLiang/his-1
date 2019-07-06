import React from 'react';
import {Row,Col} from 'antd'
import { Tabs } from 'antd';

import MedicalRecordForm from './MedicalRecordForm'
import MedicalRecordTemplate from './MedicalRecordTemplate' 
import DiagnoseTemplate from './DiagnoseTemplate';
import HistoryMedicalRecord from './HistoryMedicalRecord';
import API from '../../../global/ApiConfig'
import Message from '../../../global/Message';

const TabPane = Tabs.TabPane;

class MedicalRecordHome extends React.Component {

  state={
    allMedicalRecordTemplate:null,
    allDiagnoseTemplate:null,
    allHistoryMedicalRecord:[],
    //全部的疾病列表
    allDiagnoses:{
      chineseDiagnoseDiseases:[],
      westernDiagnoseDiseases:[]
    }
  }

  componentDidMount=()=>{
    this.props.onRef(this)
    this.getMedicalRecordTemplateList();
    this.getDiagnoseTemplateList();
    this.loadAllDiagnoseDiseases();
  }

  //全部的诊断信息
  loadAllDiagnoseDiseases=()=>{
    API.request(API.outpatientDoctor.medicalRecord.allDiagnoseDiseases)
    .ok(allDiagnoses=>{
      this.setState({allDiagnoses})
      //console.warn('看到了没，这个是我接受到的诊断数据',allDiagnoses.westernDiagnoseDiseases.length,allDiagnoses.chineseDiagnoseDiseases.length)
    }).submit();
    /*this.setState({allDiagnoses:{
      westernDiagnoseDiseases:[
        {id:1,name:"23232"}
      ],
      chineseDiagnoseDiseases:[
        {id:2,name:"2323---"}
      ]
    }})*/
  }

  //应用数据(用户的病历，诊断信息)
  applyMedialRecordData=(data)=>{
    this.MedicalRecordForm.applyMedialRecordData(data)
  }

  //开启病历模板编辑器
  openMedicalRecordTemplateEditor=(mode,values)=>{
    this.MedicalRecordTemplate.showEditTemplateModal(mode,values)
  }

  //提交病历暂存(更新病历)
  updateMedicalRecord=(medicalRecord)=>{
    console.log('update medicalRecord:',medicalRecord)
    API.request(API.outpatientDoctor.medicalRecord.update,medicalRecord)
    .ok(data=>{
      //this.props.refreshPatientMedicalRecord();
      this.props.refreshPatientMedicalRecord();
      Message.success('病历已暂存')
    }).submit();
  }

  //提交病历保存
  saveMedicalRecord=(medicalRecord)=>{
    console.log('save medicalRecord:',medicalRecord)
    API.request(API.outpatientDoctor.medicalRecord.save,medicalRecord)
    .ok(data=>{
      this.props.refreshPatientMedicalRecord();
      Message.success('病历已保存')
    }).submit();
  }  

  //历史病历列表
  syncAllHistoryMedicalRecord=(type,medical_certificate_number)=>{
    console.log('loading history medical record...')
    API.request(API.outpatientDoctor.medicalRecord.historyMedicalRecordList,{type,medical_certificate_number})
    .ok(allHistoryMedicalRecord=>{
      this.setState({allHistoryMedicalRecord})
    }).submit();
  }  
  /**************************************** 病历模板  ****************************************** */
  //使用病历模板 （获得模板详细）
  applyMedicalRecordTemplate=(id)=>{
    API.request(API.outpatientDoctor.medicalRecordTemplate.detail,{id})
    .ok(medicalRecordTemplate=>{
      this.MedicalRecordForm.applyMedicalRecordTemplate(medicalRecordTemplate)
    }).submit();
  }
  
  //病历模板列表
  getMedicalRecordTemplateList=()=>{
    API.request(API.outpatientDoctor.medicalRecordTemplate.list)
    .ok((allMedicalRecordTemplate)=>{
      this.setState({allMedicalRecordTemplate})
    }).submit();
  }

  //存为病历模板 创建病历模板
  createMedicalRecordTemplate=(template)=>{
    console.log('save as temmplate:',template)
    API.request(API.outpatientDoctor.medicalRecordTemplate.create,template)
    .ok(data=>{
      Message.success('模板创建成功！')
      this.getMedicalRecordTemplateList();
    }).submit();
  }

  //更新病历模板
  updateMedicalRecordTemplate=(template)=>{
    console.log('update temmplate:',template)
    API.request(API.outpatientDoctor.medicalRecordTemplate.update,template)
    .ok(data=>{
      Message.success('模板修改成功！')
      this.getMedicalRecordTemplateList();
    }).submit();
  }

  //删除病历模板
  deleteMedicalRecordTemplate=(id)=>{
    console.log('deleted template id',id)
    API.request(API.outpatientDoctor.medicalRecordTemplate.delete,{idArr:[id]})
    .ok(data=>{
      Message.success('删除成功！')
      this.getMedicalRecordTemplateList();
    }).submit();
   // API.request(API.medicalRecordTemplate.)
  }

  /**************************************  诊断模板  ***************************************** */
  //使用诊断模板 （获得模板详细）
  applyDiagnoseTemplate=(id)=>{
    //这个和引用病历不同，不是调用子组建（MedicalRecordForm）而是修改父的state
    API.request(API.outpatientDoctor.diagnoseTemplate.detail,{id:id})
    .ok((data)=>{
      console.log('apply diagnose template:',data);
      this.MedicalRecordForm.applyDiagnoseTemplate(data.diagnose);
    }).submit();
  }
    
  //诊断模板列表
  getDiagnoseTemplateList=()=>{
    API.request(API.outpatientDoctor.diagnoseTemplate.list)
    .ok((allDiagnoseTemplate)=>{
      //resolve key
      ["personal","department","hospital"].forEach(type=>{
        allDiagnoseTemplate[type].forEach(diagnose=>{
          diagnose.chinese_diagnose.forEach(x=>x.key=x.disease_id);
          diagnose.western_diagnose.forEach(x=>x.key=x.disease_id);
        });
      });
      this.setState({allDiagnoseTemplate})
    }).submit();
  }

  //存为诊断模板 创建诊断模板
  createDiagnoseTemplate=(template)=>{
    console.log('save as temmplate:',template)
    API.request(API.outpatientDoctor.diagnoseTemplate.create,template)
    .ok(data=>{
      Message.success('模板创建成功！')
      this.getDiagnoseTemplateList();
    }).submit();
  }

  //更新诊断模板
  updateDiagnoseTemplate=(template)=>{
    console.log('update temmplate:',template)
    API.request(API.outpatientDoctor.diagnoseTemplate.update,template)
    .ok(data=>{
      Message.success('模板修改成功！')
      this.getDiagnoseTemplateList();
    }).submit();
  }

  //删除诊断模板
  deleteDiagnoseTemplate=(id)=>{
    console.log('deleted template id',id)
    API.request(API.outpatientDoctor.diagnoseTemplate.delete,{idArr:[id]})
    .ok(data=>{
      Message.success('删除成功！')
      this.getDiagnoseTemplateList();
    }).submit();
    // API.request(API.medicalRecordTemplate.)
  }
  
  /**************************************************************************** */

  render() {
    const {currentPatient} = this.props;
    const {allMedicalRecordTemplate,allDiagnoseTemplate,allDiagnoses,allHistoryMedicalRecord } = this.state;
    const disabled = (currentPatient.registration===null || currentPatient.registration===undefined);
     return (
      <div>
        <Row>
          <Col span={16}>
            <MedicalRecordForm
              onRef={(ref)=>{this.MedicalRecordForm = ref;}}
              ref="medicalRecordForm"
              currentPatient={currentPatient}
              createMedicalRecordTemplate={this.createMedicalRecordTemplate.bind(this)}
              updateMedicalRecord={this.updateMedicalRecord.bind(this)}
              saveMedicalRecord={this.saveMedicalRecord.bind(this)}
              openMedicalRecordTemplateEditor={this.openMedicalRecordTemplateEditor.bind(this)}
              allDiagnoses={allDiagnoses}
              disabled={disabled}/>
          </Col>

          <Col span={8}>
            <Tabs defaultActiveKey="1" >
              <TabPane tab="病历模板" key="1">
                <MedicalRecordTemplate 
                  onRef={(ref)=>this.MedicalRecordTemplate=ref}
                  applyTemplate={this.applyMedicalRecordTemplate.bind(this)}
                  createMedicalRecordTemplate={this.createMedicalRecordTemplate.bind(this)}
                  updateMedicalRecordTemplate={this.updateMedicalRecordTemplate.bind(this)}
                  deleteMedicalRecordTemplate={this.deleteMedicalRecordTemplate.bind(this)}
                  disabled={disabled}
                  allMedicalRecordTemplate={allMedicalRecordTemplate}/>
              </TabPane>

              <TabPane tab="常用诊断" key="2">
                <DiagnoseTemplate 
                    onRef={(ref)=>this.DiagnoseTemplate=ref}
                    applyTemplate={this.applyDiagnoseTemplate.bind(this)}
                    createDiagnoseTemplate={this.createDiagnoseTemplate.bind(this)}
                    updateDiagnoseTemplate={this.updateDiagnoseTemplate.bind(this)}
                    deleteDiagnoseTemplate={this.deleteDiagnoseTemplate.bind(this)}
                    disabled={disabled}
                    allDiagnoses={allDiagnoses}
                    allDiagnoseTemplate={allDiagnoseTemplate}/>
              </TabPane>

              <TabPane tab="历史病历" key="3" disabled={disabled}>
                <HistoryMedicalRecord
                  allHistoryMedicalRecord={allHistoryMedicalRecord}
                />
              </TabPane>
            </Tabs>

          </Col>
        </Row>
      </div>)
  }
}

export default MedicalRecordHome;
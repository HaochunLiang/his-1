import React from 'react'
import {Row,Col, Tabs, Button,Card, Divider} from 'antd'
import API from '../../../global/ApiConfig'
import LogoDisplay from '../../../global/LogoDisplay'
import PatientSelector from './PatientSelector'
import CurrentProjectDisplay from './CurrentProjectDisplay'

import AdditionalRecording from './additionalRecording'
import ResultSubmit from './resultSubmit'

const {TabPane} = Tabs;

class MainWorkStation extends React.Component {

  state={
    currentRegistrationInfo:null,
    currentProject:null,

    /*currentRegistrationInfo:{

    },
    currentProject:{
      id:1,
      non_drug_item:{
        code: "UIW82",
        department_id: 1,
        expense_classification_id: 1,
        fee: "10",
        format: "XX规格",
        id: 1,
        name: "输液",
        pinyin: "SY",
      }
    },*/

    /**
     * currentRegistrationInfo:{
     *    medical_record_id :,
          outpatient_doctor_id:我觉得要加上医生的id，不是自己的病人病历不能修改，只读
          patient_name :,
          age :,
          gender:,
          medical_insurance_diagnosis :,
          registration_department:"神经科",
          registration_category :"",
          registration_source:"",

     * }
     */
  }
  

  //查询挂号列表
  searchRegistration=(data,callback)=>{
    API.request(API.doctorOfTechnology.IADExcute.searchRegistration,data)
    .ok(registrationList=>{
      callback(registrationList)
    }).submit();
  }

  //查询某病人全部（自己可以用的）检查检验项目
  searchExcuteProject=(medical_record_id,callback)=>{
    API.request(API.doctorOfTechnology.IADExcute.allExcuteProject,{medical_record_id})
    .ok((list)=>{
      callback(list)
    }).submit();
  }

  //登记
  register=(id,callback)=>{
    API.request(API.doctorOfTechnology.IADExcute.register,{exam_item_id:[id]})
    .ok(data=>{
      callback(true)
    }).submit()
  }


  //提交结果
  submitResult=(result,callback)=>{
    API.request(API.doctorOfTechnology.IADExcute.submitResult,result)
    .ok(data=>{
      callback(true)
    }).submit()
  }
  
  //查看结果
  getIADResult=(exam_item_id,callback)=>{
    API.request(API.doctorOfTechnology.IADExcute.getResult,{exam_item_id})
    .ok(result=>{
      callback(result)
    }).submit();
  }
  

  //选择当前的病人挂号信息
  selectCurrentRegistrationInfo=(currentRegistrationInfo)=>{
    this.setState({currentRegistrationInfo})
  }

  //开始录入
  startLog=(item,registration)=>{
    //console.warn(item,registration)
    this.setState({
      currentRegistrationInfo:registration,
      currentProject:item
    })
    //否则会不好编辑
    if(this.ResultSubmit) {
      this.ResultSubmit.setState({
        submited:false
      })
    }
  }

  render() {
    //const {me} = this.props;
    //console.warn(me);
    const {currentRegistrationInfo,currentProject} = this.state;
    const disabled = currentRegistrationInfo===null || currentRegistrationInfo===undefined;

    return(
    <div>
      <Row>
        <Col span={3}>
          <Card style={{margin:'5px',minHeight:'900px'}}>
            <Button 
                onClick={()=>{this.PatientSelector.open()}}
                type="primary"
              >
                选择病人
            </Button>
            <Divider/>
            <CurrentProjectDisplay
              registration={currentRegistrationInfo}
              project={currentProject}
            />
          </Card>

          <PatientSelector
            onRef={(ref)=>this.PatientSelector=ref}
            getIADResult={this.getIADResult.bind(this)}
            searchRegistration={this.searchRegistration.bind(this)}
            searchExcuteProject={this.searchExcuteProject.bind(this)}
            register={this.register.bind(this)}
            selectCurrentRegistrationInfo={this.selectCurrentRegistrationInfo.bind(this)}
            startLog={this.startLog.bind(this)}
            currentRegistrationInfo={currentRegistrationInfo}
          />
        </Col>

        <Col span={21}>
          {disabled?<LogoDisplay/>:
          <Card style={{margin:'5px',minHeight:'900px'}}>
            <Tabs defaultActiveKey="1">
              <TabPane tab="结果录入" key="1">
                  <ResultSubmit
                    onRef={(ref)=>{this.ResultSubmit=ref}}
                    submitResult={this.submitResult.bind(this)}
                    currentProject={currentProject}
                    currentRegistration={currentRegistrationInfo}
                  />
              </TabPane>
              <TabPane tab="医技补录" disabled={currentRegistrationInfo===null} key="2">
                <AdditionalRecording
                  currentRegistration={currentRegistrationInfo} /> 
              </TabPane>
            </Tabs>
          </Card>}
        </Col>
      </Row>

    </div>)
  }
}

export default MainWorkStation;
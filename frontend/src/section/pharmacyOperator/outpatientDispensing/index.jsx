import React from 'react';
import {Row,Col,Layout} from 'antd';
import Sider from './sider';
import API from '../../../global/ApiConfig';
import LogoDisplay from '../../../global/LogoDisplay';
import Message from '../../../global/Message';
import PrescriptionDisplay from './PrescriptionDisplay';

const {Content} = Layout;

class OutpatientDispensingSection extends React.Component {

  state = {
    registrationList:[],/** 
      key:1,
      id:1,
      medical_record_number:'0120000',
      name:'刘金星'
    }*/
    dispenseList:[
         /* {
              "id":1,
              "medical_record_id" :1,
              "type":"成药", 
              "status":"", 
              "create_time":"",
              "user_id":1,
              "prescription_item_list":[
                  { 
                      "drug_item":{
                          "id" :1,
                          "code":"AIE929",
                          "name":"脑残片",
                          "format":"例",
                          "unit":"unit",
                          "manufacturer":"manu",
                          "dosage_form":"dos", 
                          "type":"type",
                          "price":"price",
                          "pinyin":"prinyin",
                          "stock":"stock"
                      },
                      "id":1,
                      "note":"note",
                      "usage" :"usage",
                      "dosage":"dosage",
                      "frequency":"fre",
                      "day_count":"dc",
                      "times" :"times",
                      "amount":"amount",
                      "prescription_id":"pi",
                      "drug_id":1,
                      "status":"已取药"
                  }
              ]
          }*/
      ]
    ,
    withdrawableList:[],
    withdrawedList:[],
    selectedRegistration:null
  }

  //取药
  dispensePrescription=(prescription_item_ids)=>{
    API.request(API.pharmacyWorkStation.drugDispatcher.dispenseSubmit,prescription_item_ids)
    .ok(data=>{
      Message.success('已取药！')
      this.refreshPrescription();
    }).submit();
  }

  //退药
  withdrawPrescripton=(prescription_items)=>{
    /**{id:1, amount:3}
        {id:2, amount:2} */
    API.request(API.pharmacyWorkStation.drugDispatcher.withdrawSubmit,prescription_items)
    .ok(data=>{
      Message.success('已退药！')
      this.refreshPrescription();
    }).submit();
  }

  searchRegistrationList=(medical_record_id)=>{
    API.request(API.outpatientWorkstation.registration.searchRegistration,{medical_record_id:parseInt(medical_record_id)})
    .ok(data=>{
      /*if(data.length) {
        data.forEach(x=>x.key=x.medical_record_id)
        this.setState({registrationList:data})
      } else {
        Message.openNotification('失败','找不到该病历号对应的挂号信息，请检查输入是否正确。')
      }*/
      if(data) {
        data.key = medical_record_id;
        const row = [data];
        this.setState({registrationList:row})
      } else {
        Message.openNotification('失败','找不到该病历号对应的挂号信息，请检查输入是否正确。')
      }
    }).submit()
  }

  searchPrescription=(registration)=>{
    this.setState({selectedRegistration:registration})
    //可取
    API.request(API.pharmacyWorkStation.drugDispatcher.dispenseList,{medical_record_id:registration.medical_record_id})
    .ok(dispenseList=>{
      this.setState({dispenseList})
    }).submit();
    //可退
    API.request(API.pharmacyWorkStation.drugDispatcher.withdrawableList,{medical_record_id:registration.medical_record_id})
    .ok(withdrawableList=>{
      this.setState({withdrawableList})
    }).submit();
    //已退
    API.request(API.pharmacyWorkStation.drugDispatcher.withdrawedList,{medical_record_id:registration.medical_record_id})
    .ok(withdrawedList=>{
      this.setState({withdrawedList})
    }).submit();
  }

  refreshPrescription=()=>{
    this.searchPrescription(this.state.selectedRegistration)
  }

  render() {
    const {registrationList,selectedRegistration} = this.state;
    
    return(
      <Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Row>
          <Col span={6} style={{minWidth:'100px'}}>
            <Sider 
              searchPrescription={this.searchPrescription.bind(this)}
              searchRegistrationList={this.searchRegistrationList.bind(this)}
              registrationList={registrationList}/>
          </Col>

          <Col span={18}>
            {selectedRegistration?
              <PrescriptionDisplay 
                withdrawPrescripton={this.withdrawPrescripton.bind(this)}
                dispensePrescription={this.dispensePrescription.bind(this)}
                registration={selectedRegistration}
                withdrawableList={this.state.withdrawableList}
                withdrawedList={this.state.withdrawedList}
                dispenseList={this.state.dispenseList}/>
            :<LogoDisplay/>}
          </Col>
        </Row>
      </Content>)
  }

}

export default OutpatientDispensingSection;
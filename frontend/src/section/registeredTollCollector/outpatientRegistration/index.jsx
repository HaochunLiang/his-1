import React from 'react';
import {Card,Layout, Spin, Typography, Modal} from 'antd';
import RegistrationForm from './RegistrationForm';
import HistoryCard from './HistoryCard';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import RegistrationBillPrint from './RegistrationBillPrint'

const {Content} = Layout;

class OutpatientRegistration extends React.Component {

  state = {
    loading:true,
    defaultRegistrationLevel:{},
    registrationLevel:[],
    settlementCategory:[],
    departments:[],
    outPatientDoctors:[],
    history:[],
    cost:0,
    payMode:false,
    printVisible:false,
    bill:{}
  }

  
  componentDidMount=()=>{this.init();}

  setPaymentMode=(payMode)=>{this.setState({payMode})}

  handlePrintCancel=()=>{this.setState({printVisible:false})}

  handlePrintOk=()=>{this.setState({printVisible:true})}

  reset=()=>{this.setState({payMode:false})}

  departmentsId2Name(id) {
    for(var i=0;i<this.state.departments.length;i++)
      if(this.state.departments[i].id===id)
         return this.state.departments[i].name;
  }

  registrationLevelId2Name(id) {
    for(var i=0;i<this.state.registrationLevel.length;i++)
      if(this.state.registrationLevel[i].id===id)
         return this.state.registrationLevel[i].name;
  }

  outPatientDoctorId2Name(id) {
    for(var i=0;i<this.state.outPatientDoctors.length;i++) {
      console.log(this.state.outPatientDoctors[i].uid,id)
      if(this.state.outPatientDoctors[i].uid===id)
         return this.state.outPatientDoctors[i].real_name;
    }
  }

  //初始化面板信息 加载必须的数据
  init=()=>{
    if(this.state.loading)//第一次加载
      API.request(API.outpatientWorkstation.registration.init)
      .ok((data)=>{
        this.setState({
          defaultRegistrationLevel:data.defaultRegistrationLevel,
          registrationLevel:data.registrationLevel,
          settlementCategory:data.settlementCategory,
          departments:data.departments,
          loading:false,
        })
      }).submit();
    else {
      //TODO:clear form...
      this.setState({history:[]})
    } 
  }

  //同步医生列表
  syncDoctorList=(data)=>{
    API.request(API.outpatientWorkstation.registration.syncDoctorList,data)
    .ok((data)=>{
      if(data.length===0) 
        Message.openNotification("找不到排班医生","没有医生在此时间段，或没有匹配的科室，请重新选择")
      this.setState({
        outPatientDoctors:data
      })
    }).submit();
  }

  calculateFee=async (data)=>{
    API.request(API.outpatientWorkstation.registration.calculateFee,data)
    .ok((data)=>{
      this.setState({
        payMode:true,
        cost:data.fee
      })
    }).submit();
  }
  
  submitRegistration=async(values)=>{
    API.request(API.outpatientWorkstation.registration.confirmRegistration,values)
    .ok((data=>{
      var bill = values;
      console.log('enter',values)///---------------------
      bill.key = data.bill.medical_record_id;
      bill.department_name = this.departmentsId2Name(values.department_id);
      bill.registration_level_name = this.registrationLevelId2Name(values.registration_level_id);
      bill.outpatient_doctor_name = this.outPatientDoctorId2Name(values.outpatient_doctor_id);
      bill.medical_record_id = data.bill.medical_record_id; //病历号
      bill.create_time = data.bill.create_time;
      bill.cost = data.bill.cost;
      bill.status = '未看诊';
      console.log('leave',values)//-----------------------
      var newHistory = this.state.history;
      newHistory.push(bill)
      this.setState({
        cost:data.fee,
        printVisible:true,
        bill:bill,
        history:newHistory
      })
      this.reset();
    })).submit();
  }

  //退号
  withdrawNumber=(medical_record_id,callback)=>{
    API.request(API.outpatientWorkstation.registration.withdrawNumber,{medical_record_id})
    .ok((data)=>{
      Message.success('退号成功！');
      this.init();
      callback(true)
    }).submit();
  }

  //搜索历史记录
  searchHistory=(id)=>{
    //fix 6.25
    API.request(API.outpatientWorkstation.registration.searchRegistration,{medical_record_id:id})
    .ok((data)=>{
      data.key = data.medical_record_id;
      this.setState({
        history:[data]
      })
    }).submit();
  }

  render() {
    const state = this.state;
    return(<Content style={{ margin: '0 16px',paddingTop:5 }}>
      <Card title="挂号">
        {state.loading?
        <div style={{textAlign:'center',paddingTop:30}}>
          <Spin/><br/>
          <Typography.Paragraph>加载中...</Typography.Paragraph>
        </div>
        :<RegistrationForm
          defaultRegistrationLevel={state.defaultRegistrationLevel}
          registrationLevel={state.registrationLevel}
          settlementCategory={state.settlementCategory}
          departments={state.departments}
          outPatientDoctors={state.outPatientDoctors}
          cost={this.state.cost}
          payMode={this.state.payMode}
          syncDoctorList={this.syncDoctorList.bind(this)}
          calculateFee={this.calculateFee.bind(this)}
          setPaymentMode={this.setPaymentMode.bind(this)}
          submitRegistration={this.submitRegistration.bind(this)}
        />}
      </Card><br/>

      <HistoryCard 
        data={this.state.history} 
        searchHistory={this.searchHistory.bind(this)}
        withdrawNumber={this.withdrawNumber.bind(this)}/>

      <Modal 
        title="打印单据" footer={null}  closable
        style={{textAlign:'center'}}
        width={400}
        onCancel={this.handlePrintCancel.bind(this)}
        onOk={this.handlePrintOk.bind(this)}
        visible={this.state.printVisible
      }>
        <RegistrationBillPrint bill={this.state.bill}/>
      </Modal>

    </Content>)
  }

}

export default OutpatientRegistration;


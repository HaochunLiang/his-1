import React from 'react';
import {Typography, Button, Table} from 'antd'
import API from '../../../global/ApiConfig';

class PatientFee extends React.Component {

  state={
    data:[]
  }

  componentDidMount=()=>{
    this.props.onRef(this);
    //this.syncHistoryFee();
  }
  
  syncHistoryFee=()=>{
    const {currentPatient} = this.props;
    const {registration} = currentPatient;
    const {medical_record_id} = registration;
    API.request(API.outpatientDoctor.patientFee.historyChargeItems,{medical_record_id})
    .ok(data=>{
      this.setState({data})
    }).submit();
  }

  render() {
    //const {currentPatient} = this.props;
    //const disabled = (currentPatient.registration===null || currentPatient.registration===undefined);
   
    const {data} = this.state;
    data.forEach(x=>x.key = x.id);
    return (
      <div>
        <div>
          <Typography.Title level={4} style={{float:'left',marginRight:'50px'}}>历史费用</Typography.Title>
          <Button 
            type="primary" 
            onClick={this.syncHistoryFee.bind(this)}
            >刷新</Button>
        </div>

        <Table
          columns={[
            {title:"收费编号",dataIndex:"id"},
            {title:"项目名称",dataIndex:"item_name"},
            {title:"单次费用",dataIndex:"price"},
            {title:"数量",dataIndex:"amount"},
            {title:"总费用",dataIndex:"fee"},
            {title:"规格",dataIndex:"format"},
            {title:"执行科室",dataIndex:"excute_department"},
            {title:"费用类型",dataIndex:"expense_classification"},
            {title:"状态",dataIndex:"status"},
            {title:"创建时间",dataIndex:"create_time"},
            {title:"缴费时间",dataIndex:"collect_time"},
            {title:"退费时间",dataIndex:"return_time"},
          ]}
          dataSource={this.state.data}
        />
      </div>
    )
  }
}

export default PatientFee;
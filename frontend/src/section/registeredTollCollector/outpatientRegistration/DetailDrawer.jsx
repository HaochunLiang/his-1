import React from 'react';
import {Drawer, Descriptions, Button} from 'antd'
import Message from '../../../global/Message';

class DetailDrawer extends React.Component {

  //处理退号
  handleWithdrawNumber=()=>{
    const data = this.props.data;
    Message.showConfirm(
      '退号',
      '你真的要退号吗？',
      ()=>this.props.withdrawNumber(data.medical_record_id))
  }

  render() {
    const data = this.props.data;
    return(
      <Drawer
          destroyOnClose
          title="挂号详情信息"
          placement="right"
          closable={true}
          visible={this.props.visible}
          onClose={this.props.onClose}
          width={800}
        >
          {data===null?null:
          <div>
            <Descriptions bordered column={2}>
              <Descriptions.Item label="病历号">{data.medical_record_id}</Descriptions.Item>
              <Descriptions.Item label="姓名">{data.patient_name}</Descriptions.Item>
              <Descriptions.Item label="性别">{data.gender==='male'?'男':'女'}</Descriptions.Item>
              <Descriptions.Item label="医生">{data.outpatient_doctor_name}</Descriptions.Item>
              <Descriptions.Item label="看诊科室">{data.department_name}</Descriptions.Item>
              <Descriptions.Item label="看诊状态">{data.status}</Descriptions.Item>
              <Descriptions.Item label="医保凭证类型：">{data.medical_certificate_number_type==="id"?"身份证":"医保卡"}</Descriptions.Item>
              <Descriptions.Item label="花费">{data.cost}</Descriptions.Item>
            </Descriptions>
            
            <br/><Button type="danger" onClick={this.handleWithdrawNumber.bind(this)} disabled={data.status!=="未看诊"}>退号</Button>
          </div>}
        </Drawer>)
  }
}

export default DetailDrawer;


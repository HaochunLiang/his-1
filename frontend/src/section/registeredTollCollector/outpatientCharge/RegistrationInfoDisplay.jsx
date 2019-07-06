import React from 'react';
import {Card,Descriptions,Form,Input,Button} from 'antd';


class RegistrationInfoDisplay extends React.Component {

  handleSearch=e=>{
    e.preventDefault();
    this.props.form.validateFieldsAndScroll((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
        this.props.handleSearch(values.medical_record_number);
      }
    });
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    const medicalRecord = this.props.medicalRecord;
    return(
    <Card style={{paddingTop:5 }} title={ 
      <Form onSubmit={this.handleSearch.bind(this)} layout="inline">
          <Form.Item label="输入病历号">
            {getFieldDecorator('medical_record_number', {
              rules: [{required: true,message: '输入病历号！'}],
            })(<Input />)}
          </Form.Item>
          <Button type="primary" htmlType="submit" icon="search">搜索</Button>
        </Form>}>
      {this.props.loading?null:
        <Descriptions title={"病历信息 "+medicalRecord.medical_record_id} bordered>
          <Descriptions.Item label="姓名">{medicalRecord.patient_name}</Descriptions.Item>
          <Descriptions.Item label="年龄">{medicalRecord.age}</Descriptions.Item>
          <Descriptions.Item label="性别">{medicalRecord.patient_name==="male"?"男":"女"}</Descriptions.Item>
          <Descriptions.Item label="病历号">{medicalRecord.medical_record_id}</Descriptions.Item>
          <Descriptions.Item label="医保诊断">{medicalRecord.medical_insurance_diagnosis}</Descriptions.Item>
          <Descriptions.Item label="看诊科室">{medicalRecord.department_name}</Descriptions.Item>
          <Descriptions.Item label="挂号类型">{medicalRecord.registration_category}</Descriptions.Item>
          <Descriptions.Item label="挂号来源">{medicalRecord.registration_source}</Descriptions.Item>
        </Descriptions>
      }
    </Card>)
  }
}

export default Form.create({ name: 'form' })(RegistrationInfoDisplay);

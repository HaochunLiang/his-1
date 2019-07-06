import React from 'react';
import {Form,Input} from 'antd';

class MedicalRecordBasicForm extends React.Component {

  state = {
    //初始化的数据
    formData:{
      chief_complaint:'',
      current_medical_history:'',
      current_treatment_situation:'',
      past_history:'',
      allergy_history:'',
      physical_examination:''
    }
  }

  componentDidMount=()=>{this.props.onRef(this)}

  clear=()=>{
    this.props.form.resetFields();
    this.setState({
      formData:{
        chief_complaint:'',
        current_medical_history:'',
        current_treatment_situation:'',
        past_history:'',
        allergy_history:'',
        physical_examination:''
    }})
  }

  applyMedicalRecordData=(data)=>{
    this.props.form.setFieldsValue(data);
    this.setState({formData:data})
  }

  applyMedicalRecordTemplateData=(data)=>{
    this.props.form.setFieldsValue({
      chief_complaint:data.chief_complaint,
      current_medical_history:data.current_medical_history,
      current_treatment_situation:data.current_treatment_situation,
      past_history:data.past_history,
      allergy_history:data.allergy_history,
      physical_examination:data.physical_examination
    });
    this.setState({formData:{
      chief_complaint:data.chief_complaint,
      current_medical_history:data.current_medical_history,
      current_treatment_situation:data.current_treatment_situation,
      past_history:data.past_history,
      allergy_history:data.allergy_history,
      physical_examination:data.physical_examination
    }})
  }

  submit = (callback) => {
    this.props.form.validateFields((err, data) => {
      if (err) {
        callback(err,null)
      } else {
        callback(null,data)
      }
    });
  }

  formValues = ()=>{
    return this.props.form.getFieldsValue();
  }

  render() {
    const {formData} = this.state;
    const {getFieldDecorator } = this.props.form;
    const {disabled} = this.props;

    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 4 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 19 },
      },
    };
    const rules=[{
        required:true,
        message:'字段不能为空'
    }];

    return (
      <Form {...formItemLayout} onSubmit={this.handleSubmit} disabled={disabled} >
        <Form.Item label="主诉">
          {getFieldDecorator('chief_complaint',
          {rules,initialValue:formData.chief_complaint})(<Input/>)}
        </Form.Item>
        <Form.Item label="现病史">
          {getFieldDecorator('current_medical_history',
          {rules,initialValue:formData.current_medical_history})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="现病治疗情况">
          {getFieldDecorator('current_treatment_situation',
          {rules,initialValue:formData.current_treatment_situation})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="既往史">
          {getFieldDecorator('past_history',
          {rules,initialValue:formData.past_history})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="过敏史">
          {getFieldDecorator('allergy_history',
          {rules,initialValue:formData.allergy_history})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="体格检查">
          {getFieldDecorator('physical_examination',
        {rules,initialValue:formData.physical_examination})(<Input.TextArea/>)}
      </Form.Item>
    </Form>
    );
  }
 
}

export default Form.create({ name: 'medical_record_basic' })(MedicalRecordBasicForm);
import React from 'react';
import {Form,Input,Radio,Divider,Button} from 'antd';

class MedicalRecordTemplateEdit extends React.Component {

   //修改模板修改
  handleSubmit=e=>{
    e.preventDefault();
    const form = this.props.form;
    const mode = this.props.mode;
    form.validateFields((err, values) => {
      if (!err) {
        this.props.handleUpdateTemplateSubmit(mode,values);
      }
    })
  }

  render() {
    const {getFieldDecorator } = this.props.form;
    const {template,mode } = this.props;
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
      <Form {...formItemLayout} onSubmit={this.handleSubmit} >
        {mode==="update"?
        <Form.Item label="编号">
          {getFieldDecorator('id',
          {rules,initialValue:template.id})(<Input disabled/>)}
        </Form.Item>:null}
        <Form.Item label="标题">
          {getFieldDecorator('title',
          {rules,initialValue:template.title})(<Input/>)}
        </Form.Item>
        <Form.Item label="分类">
          {getFieldDecorator('type',
          {rules,initialValue:template.type})(
            <Radio.Group >
            <Radio value={0}>个人</Radio>
            <Radio value={1}>科室</Radio>
            <Radio value={2}>医院</Radio>
          </Radio.Group>
          )}
        </Form.Item>
        
        <Divider/>
        <Form.Item label="主诉">
          {getFieldDecorator('chief_complaint',
          {rules,initialValue:template.chief_complaint})(<Input/>)}
        </Form.Item>
        <Form.Item label="现病史">
          {getFieldDecorator('current_medical_history',
          {rules,initialValue:template.current_medical_history})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="现病治疗情况">
          {getFieldDecorator('current_treatment_situation',
          {rules,initialValue:template.current_treatment_situation})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="既往史">
          {getFieldDecorator('past_history',
          {rules,initialValue:template.past_history})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="过敏史">
          {getFieldDecorator('allergy_history',
          {rules,initialValue:template.allergy_history})(<Input.TextArea/>)}
        </Form.Item>
        <Form.Item label="体格检查">
          {getFieldDecorator('physical_examination',
        {rules,initialValue:template.physical_examination})(<Input.TextArea/>)}
      </Form.Item>

      <Button type="primary" htmlType="submit">保存</Button>
    </Form>
    );
  }
 
}

export default Form.create({ name: 'medical_record_template_edit' })(MedicalRecordTemplateEdit);
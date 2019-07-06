import React from 'react';
import {Form,Input,Radio,Divider,Button} from 'antd';
import DiagnoseSelectionTable from './DiagnoseSelectionTable'

class DiagnoseTemplateEdit extends React.Component {

   //修改模板修改
  handleSubmit=e=>{
    e.preventDefault();
    const form = this.props.form;
    const mode = this.props.mode;
    form.validateFields((err, values) => {
      if (!err) {
        values.diagnoses = this.DiagnoseSelectionTable.state.patientDiagnose;
        this.props.handleUpdateTemplateSubmit(mode,values);
      }
    })
  }

  componentDidMount=()=>{
    const {template} = this.props;
    //深拷贝
    var copy =  JSON.parse(JSON.stringify(template))
    this.DiagnoseSelectionTable.setPatientDiagnose(copy)
  }

  render() {
    const {getFieldDecorator } = this.props.form;
    const {template,mode,allDiagnoses} = this.props;
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

        <DiagnoseSelectionTable 
          diagnoses={allDiagnoses}
          disabled={false}
          onRef={(ref)=>{this.DiagnoseSelectionTable = ref}}
        />

        <Button type="primary" htmlType="submit">保存</Button>
      </Form>
    );
  }
 
}

export default Form.create({ name: 'diagnose_template_edit' })(DiagnoseTemplateEdit);
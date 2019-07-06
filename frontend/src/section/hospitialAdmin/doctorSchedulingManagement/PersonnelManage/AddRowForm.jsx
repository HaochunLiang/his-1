import React from 'react';
import {Button,Input,Form,Select,DatePicker,InputNumber} from 'antd';
import moment from 'moment';

const Option = Select.Option
const dateFormat = 'YYYY-MM-DD';

function disabledDate(current) {
  // Can not select days before today and today
  return current && current < moment().startOf('day');
}

class AddRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received addRow values of form: ', values);
        this.props.newRow(values)
        this.props.exit();
      }
    });
  };

  handleIDChange = (e) =>{
    let value = e/*.target.value*/;
    (this.props.getAddTableInfo(value)).then(res=>{
      window.res = res;
      if(/*res.length!=0*/res.name!==undefined){
      this.props.form.setFieldsValue({
        name:`${res.name}`,
        department_name:`${res.department_name}`
      });
    }
    },error=>{
      console.log(error)
    })
    console.log('value',value);
  };

  handleNameChange = (e) =>{
    let value = e.target.value;
    (this.props.getAddNameTableInfo(value)).then(res=>{
      window.res = res;
      if(/*res.length!=0*/res.id!==undefined){
      this.props.form.setFieldsValue({
        id:`${res.id}`,
        department_name:`${res.department_name}`
      });
    }
    },error=>{
      console.log(error)
    })
    console.log('value',value);
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 18 },
      },
    };

    const config = {
      rules: [{ type: 'object', required: true, message: 'Please select time!' }],
    };

    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>
     <Form.Item label="ID">
       {getFieldDecorator('id', {
         rules: [{ required: true, message: '输入id' }],
       })(
        <InputNumber 
        min={1} 
        max={20000} 
        onChange={this.handleIDChange}
        style={{ width: 360 }}
        />,
       )}
     </Form.Item>
        
      <Form.Item label="姓名">
       {getFieldDecorator('name', {
         rules: [{ required: true, message: '输入姓名' }],
       })(
         <Input
           onChange={this.handleNameChange}
         />,
       )}
     </Form.Item>
     <Form.Item label="科室">
       {getFieldDecorator('department_name', {
         rules: [{ required: true, message: '输入科室名称' }],
       })(
         <Input/>,
       )}
     </Form.Item>

     <Form.Item label="班次">
       {getFieldDecorator('shift', {
         rules: [{ required: true, message: '输入班次' }],
       })(
        <Select initialValue="全天" style={{ width: 360 }}>
          <Option value="全天">全天</Option>
        <Option value="上午">上午</Option>
        <Option value="下午">下午</Option>
        </Select>,
       )}
     </Form.Item>

     <Form.Item label="有效期限">
     {getFieldDecorator('expiry_date', config)(
     <DatePicker
      format={dateFormat} 
      disabledDate={disabledDate}
      style={{ width: 360 }}
      />)}
        </Form.Item>
        <Form.Item label="挂号级别">
       {getFieldDecorator('registration_Level', {
         rules: [{ required: true, message: '输入挂号级别' }],
       })(
        <Select initialValue="专家号" style={{ width: 360 }}>
          <Option value="专家号">专家号</Option>
        <Option value="普通号">普通号</Option>
        </Select>,
       )}
     </Form.Item>
     <Form.Item label="排班限额">
       {getFieldDecorator('scheduling_limit', {
         rules: [{ required: true, message: '输入排班限额' }],
       })(
        <InputNumber 
        min={1} 
        max={200} 
        initialValue={10}
        style={{ width: 360 }}
        />,
       )}
     </Form.Item>
     <Button htmlType="submit" type="primary">提交</Button>
     </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

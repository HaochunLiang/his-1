import React from 'react';
import {Button,Input,Form,Select,DatePicker,message,InputNumber} from 'antd';
import moment from 'moment';

const Option = Select.Option
const dateFormat = 'YYYY-MM-DD';

function disabledDate(current) {
  // Can not select days before today and today
  return current && current < moment().endOf('day');
}

class AddRowForm extends React.Component {

  //初始化加载数据
componentDidMount = ()=>{

}

  handleSubmit = e => {
    e.preventDefault();
    const _this = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        var c = setTimeout(_this.props.addRowConflict(values),1000);
        c = _this.props.conflict;
        //this.props.newScheduleRow(values);
        if(c===0){
          this.props.newScheduleRow(values);
        }else if(c===1){
          this.props.exit();
          message.info('该排班信息已存在，添加失败');
        }else{
          console.log('error',c);
        }
        this.props.exit();
      }
    });
  };

  handleInputChange = (e) =>{
    window.e = e;
    let value = e.target.value;
    (this.props.getAddTableInfo(value)).then(res=>{
      console.log('res',res);
      window.res = res;
      if(res.length!==0){
      this.props.form.setFieldsValue({
        //department_name: `${res[0].department_name}`,
        id:`${res[0].id}`,
        residue:`${res[0].residue}`,
        valid:"有效",
        department_name:`${res[0].department_name}`,
        shift:`${res[0].shift}`,
        registration_Level:`${res[0].registration_Level}`
      });
    }
    },error=>{
      console.log(error)
    })
    console.log('value',value);
    //console.log('returnData',returnData);
   // this.props.form.setFieldsValue({
   //   department_name: `Hi, ${value === 'xiaoA' ? 'man' : 'lady'}!`,
   // });
  };

  handleInputChangeByID = (e) =>{
    window.e = e;
    let value = e;
    (this.props.getAddTableInfoByID(value)).then(res=>{
      console.log('res id',res);
      window.res = res;
      if(res.length!==0){
      this.props.form.setFieldsValue({
        //department_name: `${res[0].department_name}`,
        name:`${res[0].name}`,
        residue:`${res[0].residue}`,
        valid:"有效",
        department_name:`${res[0].department_name}`,
        shift:`${res[0].shift}`,
        registration_Level:`${res[0].registration_Level}`
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
      <Form.Item label="姓名">
       {getFieldDecorator('name', {
         rules: [{ required: true, message: '输入姓名' }],
       })(
         <Input onChange={this.handleInputChange} />,
       )}
     </Form.Item>
     <Form.Item label="ID">
       {getFieldDecorator('id', {
         rules: [{ required: true, message: '输入id' }],
       })(
        <InputNumber 
        min={1} 
        max={20000} 
        onChange={this.handleInputChangeByID}
        style={{ width: 360 }}
        />,
       )}
     </Form.Item>   
     <Form.Item label="排班时间">
     {getFieldDecorator('schedule_date', config)(
     <DatePicker
      format={dateFormat} 
      disabledDate={disabledDate}
      style={{ width: 360 }}
      />)}
        </Form.Item>

     <Form.Item label="午别">
       {getFieldDecorator('shift', {
         rules: [{ required: true, message: '输入午别' }],
       })(
        <Select initialValue="上午" style={{ width: 360 }}>
          <Option value="上午">上午</Option>
          <Option value="下午">下午</Option>
          <Option value="全天">全天</Option>
        </Select>,
       )}
     </Form.Item>
     <Form.Item label="科室">
       {getFieldDecorator('department_name', {
         rules: [{ required: true, message: '输入科室' }],
       })(
         <Input/>,
       )}
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
       {getFieldDecorator('reg_limit', {
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
     <Form.Item label="剩余号数">
       {getFieldDecorator('residue', {
         rules: [{ required: true, message: '输入剩余号数' }],
       })(
        <InputNumber 
        min={1} 
        max={200} 
        initialValue={10}
        style={{ width: 360 }}
        />,
       )}
     </Form.Item>
     <Form.Item label="有效状态">
       {getFieldDecorator('valid', {
         rules: [{ required: true, message: '输入有效状态' }],
       })(
        <Select initialValue="有效" style={{ width: 360 }}>
          <Option value="有效">有效</Option>
          <Option value="无效">无效</Option>
        </Select>,
       )}
     </Form.Item>
     <Button htmlType="submit" type="primary">提交</Button>
     </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

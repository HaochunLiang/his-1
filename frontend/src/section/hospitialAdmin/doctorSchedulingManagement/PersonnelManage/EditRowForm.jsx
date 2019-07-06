import React from 'react';
import {Button,Input,Form,Select,InputNumber,DatePicker} from 'antd';
import moment from 'moment';

const Option = Select.Option;
const dateFormat = 'YYYY-MM-DD';

function disabledDate(current) {
  return current && current < moment().startOf('day');
}

class EditRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received editRow values of form: ', values);
        values.id = form.props.data.key;
        this.props.updateRow(values);
        this.props.exit();
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    const data = this.props.data;
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

    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>
        <Form.Item label="姓名">
          {getFieldDecorator('name', {
            initialValue:data.name
          })(
            <Input disabled={true}/>
          )}
        </Form.Item>
        <Form.Item label="科室">
          {getFieldDecorator('department_name', {
            initialValue:data.department_name
          })(
            <Input disabled={true}/>
          )}
        </Form.Item>
        <Form.Item label="职称">
          {getFieldDecorator('title', {
            initialValue:data.title
          })(
            <Input disabled={true}/>
          )}
        </Form.Item>

        <Form.Item label="挂号级别">
       {getFieldDecorator('registration_Level', {
         rules: [{ required: true, message: '输入挂号级别' }],
         initialValue:data.registration_Level
       })(
        <Select style={{ width: 360 }}>
          <Option value="专家号">专家号</Option>
        <Option value="普通号">普通号</Option>
        <Option value="急诊号">急诊号</Option>
        </Select>,
       )}
     </Form.Item>

        <Form.Item label="班次">
       {getFieldDecorator('shift', {
         rules: [{ required: true, message: '输入班次' }],
         initialValue:data.shift
       })(
        <Select style={{ width: 360 }}>
          <Option value="全天">全天</Option>
        <Option value="上午">上午</Option>
        <Option value="下午">下午</Option>
        </Select>,
       )}
     </Form.Item>
        
        <Form.Item label="有效期限">
       {getFieldDecorator('expiry_date', {
            initialValue:moment(data.expiry_date, dateFormat)
          })(
        <DatePicker
          format={dateFormat} 
          disabledDate={disabledDate}
          initialValue={moment(data.expiry_date, dateFormat)}
          style={{ width: 360 }}
        />)}
        </Form.Item>

        <Form.Item label="排班限额">
          {getFieldDecorator('scheduling_limit', {
            rules: [{ required: true, message: '输入排班限额' }],
            initialValue:data.scheduling_limit
          })(
            <InputNumber 
              min={1} 
              max={200} 
              initialValue={10}
              style={{ width: 360 }}
              />,
          )}
        </Form.Item>
        <Button htmlType="submit" type="primary">修改</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row'})(EditRowForm);

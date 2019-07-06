import React from 'react';
import { Radio, Button,Input,Form,Icon,Select, InputNumber} from 'antd';
import  Roles from '../../../global/RolesGroup';

const Option = Select.Option
const RadioGroup = Radio.Group;

class AddRowForm extends React.Component {

  state = {
    participate_in_scheduling:false,
    isDoctor:false
  }

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

  changeParticipateInScheduling =(e)=>{
    console.log('radio checked', e.target.value);
    this.setState({
      participate_in_scheduling: e.target.value,
    });
  }

  selectRole=(value)=>{
    console.log('select role value', value);
    if(Roles.isDoctor(value)) {
      this.setState({
        isDoctor:true,
        participate_in_scheduling:true
      })
    }
  }

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
    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>
      <Form.Item label="UID">
        {getFieldDecorator('uid', {
          rules: [{ required: true, message: '输入UID' }],
        })(
          <InputNumber
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入UID"
          />,
        )}
      </Form.Item>
       <Form.Item label="用户名">
        {getFieldDecorator('username', {
          rules: [{ required: true, message: '输入用户名' }],
        })(
          <Input
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入用户名"
          />,
        )}
      </Form.Item>
      <Form.Item label="密码">
        {getFieldDecorator('password', {
          rules: [{ required: true, message: '输入密码' }],
        })(
          <Input
            prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入密码"
          />,
        )}
      </Form.Item>
      <Form.Item label="真名">
        {getFieldDecorator('real_name', {
          rules: [{ required: true, message: '输入真名' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入真名"
          />,
        )}
      </Form.Item>
      <Form.Item label="职称">
        {getFieldDecorator('title', {
          rules: [{ required: true, message: '输入职称' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入职称"
          />,
        )}
      </Form.Item>
      <Form.Item label="角色">
        {getFieldDecorator('role_id', {
          rules: [{ required: true, message: '选择角色' }],
        })(
          <Select
            name="role"
            showSearch
            placeholder="选择用户角色"
            optionFilterProp="children"
            onSelect={this.selectRole.bind(this)}
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.roles.map((x)=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
            </Select>
        )}
      </Form.Item>
      <Form.Item label="参与排班">
        {getFieldDecorator('participate_in_scheduling', {
          initialValue:this.state.participate_in_scheduling
        })(
          <RadioGroup 
            disabled={!this.state.isDoctor}
            onChange={this.changeParticipateInScheduling}>
            <Radio value={true}>参与排班</Radio>
            <Radio value={false}>不参与排班</Radio>
          </RadioGroup>
        )}
      </Form.Item>
      <Form.Item label="科室">
        {getFieldDecorator('department_id', {
          rules: [{ required: true, message: '科室' }],
        })(
          <Select
            showSearch
            placeholder="选择科室"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.departments.map((x)=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
            </Select>
        )}
      </Form.Item>
      <Button htmlType="submit" type="primary">提交</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

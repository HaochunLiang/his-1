import React from 'react';
import { Button,Input,Form,Icon,Select, InputNumber} from 'antd';
import Roles from '../../../global/RolesGroup';

const Option = Select.Option

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

  selectRole=(name)=>{
    console.log('select role value', name);
    if(Roles.isDoctor(name)) {
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
      <Form.Item label="编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入编号' }],
        })(
          <InputNumber
            prefix={<Icon type="link" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="编号不可重复"
          />,
        )}
      </Form.Item>
      <Form.Item label="名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入名称' }],
        })(
          <Input
            prefix={<Icon type="link" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="名称"
          />,
        )}
      </Form.Item>
      <Form.Item label="显示顺序号">
        {getFieldDecorator('seq_num', {
          rules: [{required: true, message: '输入选择顺序号' },
          {
            pattern: /^([1-9][0-9]*)+$/, message: '只能大于0的整数'
          }]
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入选择顺序号"
          />,
        )}
      </Form.Item>
      <Form.Item label="费用">
        {getFieldDecorator('fee', {
          rules: [{ required: true, message: '输入费用' },
          {
            pattern: /^([1-9][0-9]*)+$/, message: '只能大于0的整数'
          }]
        })(
          <Input
            prefix={<Icon type="link" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入费用"
          />,
        )}
      </Form.Item>
      <Form.Item label="是否默认">
        {getFieldDecorator('is_default', {
          rules: [{ required: true, message: '选择是否默认' }],
        })(
          <Select
            name="is_default"
            showSearch
            optionFilterProp="children"
            onSelect={this.selectRole.bind(this)}
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
              <Option value={1}>是</Option>
              <Option value={0}>否</Option>
            </Select>
        )}
      </Form.Item>
      <Button htmlType="submit" type="primary">提交</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

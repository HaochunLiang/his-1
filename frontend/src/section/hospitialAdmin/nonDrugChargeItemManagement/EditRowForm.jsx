import React from 'react';
import {Button,Input,Form,Icon,Select,InputNumber} from 'antd';
import Roles from '../../../global/RolesGroup';

const Option = Select.Option;

class EditRowForm extends React.Component {

  state = {
    participate_in_scheduling:false,
    isDoctor:false
  }

  handleSubmit = e => {
    e.preventDefault();
    const form = this;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received editRow values of form: ', values);
        values.uid = form.props.data.key;
        this.props.updateRow(values);
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
      <Form.Item label="编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入编号' }],
        })(
          <InputNumber
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="编号不得重复"
          />
        )}
      </Form.Item>
      <Form.Item label="统一编号">
        {getFieldDecorator('code', {
          rules: [{ required: true, message: '输入编码' }],
          initialValue:data.id
        })(
          <Input
            disabled
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="编码不得重复"
          />
        )}
      </Form.Item>
      <Form.Item label="名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入名称' }],
          initialValue:data.name
        })(
          <Input
            prefix={<Icon type="link" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入名称"
          />
        )}
      </Form.Item>
      <Form.Item label="拼音">
        {getFieldDecorator('pinyin', {
          rules: [{ required: true, message: '输入拼音' }],
          initialValue:data.pinyin
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入拼音"
          />
        )}
      </Form.Item>
      <Form.Item label="规格">
        {getFieldDecorator('format', {
          rules: [{ required: true, message: '输入规格' }],
          initialValue:data.format
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入规格"
          />
        )}
      </Form.Item>
      <Form.Item label="费用">
        {getFieldDecorator('fee', {
          rules: [{ required: true, message: '输入费用' }],
          initialValue:data.fee
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="输入费用"
          />
        )}
      </Form.Item>
      <Form.Item label="费用类型">
        {getFieldDecorator('expense_classification_id', {
          rules: [{ required: true, message: '选择费用类型' }],
          initialValue:data.expense_classification_id
        })(
          <Select
            showSearch
            placeholder="选择费用类型"
            optionFilterProp="children"
            onSelect={this.selectRole.bind(this)}
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.expense_classification.map((x)=>(<Option value={x.id} key={x.id}>{x.fee_name}</Option>))}
            </Select>
        )}
      </Form.Item>
      <Form.Item label="执行科室">
        {getFieldDecorator('department_id', {
          rules: [{ required: true, message: '选择执行科室' }],
          initialValue:data.department_id
        })(
          <Select
            showSearch
            placeholder="选择执行科室"
            optionFilterProp="children"
            onSelect={this.selectRole.bind(this)}
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.department.map((x)=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
            </Select>
        )}
      </Form.Item>
      <Button htmlType="submit" type="primary">修改</Button>
    </Form>)
  }
}

export default Form.create({ name: 'new_row'})(EditRowForm);

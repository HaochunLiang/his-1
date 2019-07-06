import React from 'react';
import {Button,Input,Form,Icon, InputNumber} from 'antd';

class AddRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        values.id = parseInt(values.id);
        console.log('Received addRow values of form: ', values);
        this.props.newRow(values)
        this.props.exit();
      }
    });
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
    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>
      <Form.Item label="编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入编号' }],
        })(
          <InputNumber
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="编号不得重复"
          />,
        )}
      </Form.Item>
       <Form.Item label="结算名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入结算名称' }],
        })(
          <Input
            prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="结算名称"
          />,
        )}
      </Form.Item>
      <Button htmlType="submit" type="primary">提交</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

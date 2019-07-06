import React from 'react';
import {Button,Input,Form,Icon} from 'antd';

class EditRowForm extends React.Component {

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
            initialValue:data.id
          })(
            <Input disabled/>
          )}
        </Form.Item>
        <Form.Item label="结算名称">
          {getFieldDecorator('name', {
            rules: [{ required: true, message: '输入结算名称' }],
            initialValue:data.name
          })(
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="输入结算名称"
            />,
          )}
        </Form.Item>
        <Button htmlType="submit" type="primary">修改</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row'})(EditRowForm);

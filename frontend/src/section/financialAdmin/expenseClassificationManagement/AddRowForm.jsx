import React from 'react';
import {Button,Input,Form,Icon, InputNumber} from 'antd';

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
          />
        )}
      </Form.Item>
      <Form.Item label="名称">
        {getFieldDecorator('fee_name', {
          rules: [{ required: true, message: '输入名称' }],
        })(
          <Input
            prefix={<Icon type="link" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="名称不可重复"
          />
        )}
      </Form.Item>
      <Form.Item label="拼音">
          {getFieldDecorator('pinyin', {
            rules: [{ required: true, message: '输入拼音' }],
          })(
            <Input
              prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="输入拼音"
            />
          )}
        </Form.Item>
      <Button htmlType="submit" type="primary">提交</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

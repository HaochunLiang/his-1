import React from 'react';
import {Button,Input,Form,Icon,Select,InputNumber} from 'antd';

const Option = Select.Option

class AddRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        values.id = parseInt(values.id);
        console.log('Received addRow values of form: ', values);
        this.props.newRow(values);
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
      <Form.Item label="科室编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入科室编号' }],
        })(
          <InputNumber
            prefix={<Icon type="bank" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="重要字段 请谨慎填写"
          />,
        )}
      </Form.Item>
       <Form.Item label="科室名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入科室名称' }],
        })(
          <Input
            prefix={<Icon type="bank" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="科室名称"
          />,
        )}
      </Form.Item>
      <Form.Item label="名称拼音">
        {getFieldDecorator('pinyin', {
          rules: [{ required: true, message: '输入名称拼音' }],
        })(
          <Input
            prefix={<Icon type="bank" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="名称拼音"
          />,
        )}
      </Form.Item>
      <Form.Item label="科室类别">
        {getFieldDecorator('classification_id', {
          rules: [{ required: true, message: '选择科室类别' }],
        })(
          <Select
            showSearch
            placeholder="选择类型"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {this.props.departmentClassification.map((x)=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
            </Select>
        )}
      </Form.Item>
      <Form.Item label="科室种类">
        {getFieldDecorator('type', {
          rules: [{ required: true, message: '选择科室种类' }],
        })(
          <Select
            showSearch
            placeholder="选择类型"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {["临床科室","医技科室","行政科室","财务科室"].map((x)=>(<Option value={x} key={x}>{x}</Option>))}
            </Select>
        )}
      </Form.Item>
      <Button htmlType="submit" type="primary">提交</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

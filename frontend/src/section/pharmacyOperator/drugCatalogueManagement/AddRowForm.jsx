import React from 'react';
import {Button,Input,Form,Icon,Select, InputNumber} from 'antd';

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
          rules: [{ required: true, message: '输入职称' }],
        })(
          <InputNumber
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="编号"
          />
        )}
      </Form.Item>
      <Form.Item label="国际编码">
        {getFieldDecorator('code', {
          rules: [{ required: true, message: '输入药品国际编码' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品国际编码"
          />
        )}
      </Form.Item>
      <Form.Item label="名称">
        {getFieldDecorator('name', {
          rules: [{ required: true, message: '输入药品名称' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品名称"
          />
        )}
      </Form.Item>
      <Form.Item label="规格">
        {getFieldDecorator('format', {
          rules: [{ required: true, message: '输入规格' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品规格"
          />
        )}
      </Form.Item>
      <Form.Item label="单位">
        {getFieldDecorator('unit', {
          rules: [{ required: true, message: '输入药品单位' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品单位"
          />
        )}
      </Form.Item>
      <Form.Item label="制造商">
        {getFieldDecorator('manufacturer', {
          rules: [{ required: true, message: '输入药品制造商' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品制造商"
          />
        )}
      </Form.Item>
      <Form.Item label="剂型">
        {getFieldDecorator('dosage_form', {
          rules: [{ required: true, message: '输入药品计型' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品剂型"
          />
        )}
      </Form.Item>
      <Form.Item label="药品类型">
        {getFieldDecorator('type', {
          rules: [{ required: true, message: '选择药品类型' }],
        })(
          <Select
            showSearch
            placeholder="药品类型"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
            {[{name:"中成药",key:1},{name:"中草药",key:2},{name:"西药",key:3}].map(x=>(<Select.Option key={x.key} value={x.name}>{x.name}</Select.Option>))}
            </Select>
        )}
      </Form.Item>
      <Form.Item label="价格">
        {getFieldDecorator('price', {
          rules: [{ required: true, message: '输入药品计型' }],
        })(
          <InputNumber
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品价格"
          />
        )}
      </Form.Item>
      <Form.Item label="拼音">
        {getFieldDecorator('pinyin', {
          rules: [{ required: true, message: '输入药品拼音' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品拼音"
          />
        )}
      </Form.Item>
      <Form.Item label="库存">
        {getFieldDecorator('stock', {
          rules: [{ required: true, message: '输入药品库存' }],
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品库存"
          />
        )}
      </Form.Item>  
      <Button htmlType="submit" style={{textAlign:'center'}} type="primary">提交</Button>
    </Form>)
  }
}

export default Form.create({ name: 'new_row' })(AddRowForm);

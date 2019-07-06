import React from 'react';
import {Button,Input,Form,Icon,Select, InputNumber} from 'antd';
import  Roles from '../../../global/RolesGroup';

class EditRowForm extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received editRow values of form: ', values);
        //values.uid = this.form.props.data.key;
        this.props.exit();
        this.props.updateRow(values);
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
    var data = this.props.data;
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

    //update后data会null
    if(!data) data = {}
    //console.warn(data)

    return(<Form onSubmit={this.handleSubmit} {...formItemLayout}>

      <Form.Item label="编号">
        {getFieldDecorator('id', {
          rules: [{ required: true, message: '输入职称' }],
          initialValue:data.id
        })(
          <InputNumber
            disabled
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="编号"
          />
        )}
      </Form.Item>
      <Form.Item label="国际编码">
        {getFieldDecorator('code', {
          rules: [{ required: true, message: '输入药品国际编码' }],
          initialValue:data.code
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
          initialValue:data.name
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
          initialValue:data.format
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
          initialValue:data.unit
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
          initialValue:data.manufacturer
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
          initialValue:data.dosage_form
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
          initialValue:data.type
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
          initialValue:data.price
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
          initialValue:data.pinyin
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
          initialValue:data.stock
        })(
          <Input
            prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
            placeholder="药品库存"
          />
        )}
      </Form.Item>  
      <Button htmlType="submit" type="primary">修改</Button>
    </Form>)
  }
}

export default Form.create({ name: 'new_row'})(EditRowForm);

import React from 'react';
import { Button,Input,Form,Icon,Select, InputNumber} from 'antd';
import  Roles from '../../../global/RolesGroup';

const Option = Select.Option

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
          <Form.Item >
            {getFieldDecorator('raw_id', {
              initialValue:data.id
            })(<Input hidden/>)}
          </Form.Item>
          <Form.Item label="统一编号">
            {getFieldDecorator('id', {
              rules: [{ required: true, message: '国际统一编码' }],
              initialValue:data.id
            })(
              <InputNumber
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                placeholder="编号不能重复"
              />
            )}
          </Form.Item>
          <Form.Item label="国际编码">
            {getFieldDecorator('code', {
              rules: [{ required: true, message: '国际统一编码不能重复' }],
              initialValue:data.code
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
                placeholder="国际统一编码不能重复"
              />
            )}
          </Form.Item>
          <Form.Item label="疾病名称">
            {getFieldDecorator('name', {
              rules: [{ required: true, message: '输入名称' }],
              initialValue:data.name
            })(
              <Input
                prefix={<Icon type="link" style={{ color: 'rgba(0,0,0,.25)' }} />}
                placeholder="名称不能重复"
              />
            )}
          </Form.Item>
          <Form.Item label="拼音助记符">
            {getFieldDecorator('pinyin', {
              rules: [{ required: true, message: '输入助记符' }],
              initialValue:data.pinyin
            })(
              <Input
                prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
                placeholder="输入助记符"
              />
            )}
          </Form.Item>
          <Form.Item label="自定义名称">
            {getFieldDecorator('custom_name', {
              initialValue:data.custom_name
            })(
              <Input
                prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
                placeholder="输入自定义名称"
              />
            )}
          </Form.Item>
          <Form.Item label="自定义拼音">
            {getFieldDecorator('custom_pinyin', {
              initialValue:data.custom_pinyin
            })(
              <Input
                prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
                placeholder="输入自定义拼音"
              />,
            )}
          </Form.Item>
          <Form.Item label="疾病分类">
            {getFieldDecorator('classification_id', {
              rules: [{ required: true, message: '选择分类' }],
              initialValue:data.classification_id
            })(
              <Select
                showSearch
                placeholder="选择分类"
                optionFilterProp="children"
                onSelect={this.selectRole.bind(this)}
                filterOption={(input, option) =>
                  option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                }
                >
                {this.props.diseaseClassification.map((x)=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
                </Select>
            )}
          </Form.Item>
          <Button htmlType="submit" type="primary">修改</Button>
      </Form>)
  }
}

export default Form.create({ name: 'new_row'})(EditRowForm);

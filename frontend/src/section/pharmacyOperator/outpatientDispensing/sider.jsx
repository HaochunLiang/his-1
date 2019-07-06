import React from 'react';
import {Input,Card,Icon, Form, Table, Button} from 'antd'



class Sider extends React.Component {

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        this.props.searchRegistrationList(values.medical_record_id);
      }
    });
  };

  siderColumn = [
    {
      title:'病历号',
      key:'medical_record_id',
      dataIndex:'medical_record_id'
    },{
      title:'姓名',
      key:'patient_name',
      dataIndex:'patient_name'
    },{
      title:"操作",
      render:(text,record,index)=>(<Button 
        type="primary" 
        size="small" 
        onClick={()=>this.props.searchPrescription(record)}
      >查看</Button>)
    }
  ]


  render() {
    const { getFieldDecorator } = this.props.form;
    const {registrationList} = this.props;
    registrationList.forEach(x=>x.key=x.medical_record_id)
    return(
      <div>
          <Form onSubmit={this.handleSubmit.bind(this)}>
            <Card title={
              <span>
                <span>查询退药/发药列表</span>
                <Button icon="sync" style={{float:'right'}} type="primary" htmlType="submit">查询</Button>
              </span>
            } style={{minWidth:'100px',minHeight:'800px'}} >
              <Form.Item>
                {getFieldDecorator('medical_record_id', {
                  rules: [{ required: true, message: '输入病历号' }],
                })(
                  <Input
                    prefix={<Icon type="number" style={{ color: 'rgba(0,0,0,.25)' }} />}
                    placeholder="病历号"
                  />
                )}
              </Form.Item>
              <Table size="small" columns={this.siderColumn} dataSource={registrationList} pagination={false}/>
            </Card>
          </Form>
          
      </div>)
  }

}

export default Form.create({ name: 'sider_form' })(Sider);;
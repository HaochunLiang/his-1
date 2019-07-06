import React from 'react'
import {Row,Col, Button,Card, Input, Form, Typography, Descriptions, Divider} from 'antd'


import ImageUpload from './ImageUpload'
import Message from '../../../../global/Message';

class ResultSubmit extends React.Component {

  componentDidMount=()=>{
    this.props.onRef(this);
  }

  state={
    submited:false
  }

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        values.images = this.ImageUpload.getData();
        console.log('Received values of form: ', values);
        Message.showConfirm('提交','你确定要提交吗？',()=>{
          this.props.submitResult(values,(succ)=>{
            if(succ)
              this.setState({submited:true})
          })
        })
      }
    });
  };

  render() {
    const {getFieldDecorator} = this.props.form;
    const {currentProject,currentRegistration} = this.props;
    const disabled = currentRegistration===null || currentRegistration===undefined;
    const {submited} = this.state;

    //console.warn(currentProject,currentRegistration)

    return (
      <Card title={disabled?"":currentProject.non_drug_item.name}>
        {disabled?null:
        <div>
          <Row>
            <Col span={12}>
              <div style={{marginRight:'15px'}}>
                <Descriptions column={2}  title="患者信息" bordered>
                  <Descriptions.Item label="患者姓名">{currentRegistration.patient_name}</Descriptions.Item>
                  <Descriptions.Item label="年龄">{currentRegistration.age}</Descriptions.Item>
                  <Descriptions.Item label="性别">{currentRegistration.gender==="male"?"男":"女"}</Descriptions.Item>
                  <Descriptions.Item label="病历号">{currentRegistration.medical_record_id}</Descriptions.Item>
                </Descriptions>
              </div>
            </Col>
            <Col span={12}>
              <Descriptions column={2}  title="项目信息" bordered>
                <Descriptions.Item label="项目名称">{currentProject.non_drug_item.name}</Descriptions.Item>
                <Descriptions.Item label="拼音">{currentProject.non_drug_item.pinyin}</Descriptions.Item>
                <Descriptions.Item label="规格">{currentProject.non_drug_item.format}</Descriptions.Item>
                <Descriptions.Item label="项目编码">{currentProject.non_drug_item.code}</Descriptions.Item>
                <Descriptions.Item label="执行编号">{currentProject.id}</Descriptions.Item>
                <Descriptions.Item label="费用">{currentProject.non_drug_item.fee}</Descriptions.Item>
              </Descriptions>
            </Col>
          </Row>
          <Divider/>
          <Row>
            <Col span={15}>
              <Typography.Title level={4}>结论与建议</Typography.Title>
              <Form labelCol={{ span: 4 }} wrapperCol={{ span: 13 }} onSubmit={this.handleSubmit}>
                <Form.Item label="当前项目编号">
                  {getFieldDecorator('exam_item_id', {
                    initialValue:currentProject.id
                  })(<Input disabled/>)}
                </Form.Item>
                <Form.Item label="结论">
                  {getFieldDecorator('result', {
                    rules: [{ required: true, message: '输入结论' }],
                  })(<Input.TextArea disabled={submited}/>)}
                </Form.Item>
                <Form.Item label="建议">
                  {getFieldDecorator('advice', {
                    rules: [{ required: true, message: '输入建议' }],
                  })(<Input.TextArea disabled={submited}/>)}
                </Form.Item>
                <Form.Item wrapperCol={{ span: 12, offset: 5 }}>
                  <Button type="primary" htmlType="submit" disabled={submited}>
                    提交
                  </Button>
                </Form.Item>
              </Form>
            </Col>
    
            <Col span={9}>
              <Typography.Title level={4}>图片上传</Typography.Title>
              <ImageUpload onRef={(ref)=>this.ImageUpload=ref} disabled={submited}/>
              {submited?<span style={{color:'red'}}>已成功提交结果</span>:null}
            </Col>
          </Row>
        </div>}
      </Card>
    )
  }
}

export default Form.create({ name:'result' })(ResultSubmit);
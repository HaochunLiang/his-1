import React from 'react';
import {Form,Input,Icon,Col, Select, Row,DatePicker,Radio,Button} from 'antd'
import moment from 'moment';
const { Option } = Select;
const RadioGroup = Radio.Group;

class RegistrationForm extends React.Component {

  state = {
    //初始化加载
   /* departments:[ //所有科室
      {name:"骨科",id:1},
      {name:"脑壳",id:2}
    ],
    defaultRegistrationLevel:{ //默认的挂号等级
      name:"普通号",id:2,price:10
    },
    registrationLevel:[ //所有挂号等级 
      {name:"专家号",id:1,price:20},
      {name:"普通号",id:2,price:10}
    ],
    settlementCategory:[ //所有支付方式
      {name:"现金",id:1},
      {name:"农行卡",id:2},
      {name:"支付宝",id:3}
    ],
    */
  }

  componentDidMount=()=>{
    //console.log(moment())
  }

  selectDepartment = async(value)=>{
    //console.log('select',value)
    this.props.form.resetFields(["outpatient_doctor_id"])
    await this.setState({departmentId:value})
    this.syncDoctorList();
  }

  handleRegistrationLevelChange = async (registrationLevelId) =>{
    this.props.form.resetFields(["outpatient_doctor_id"])
    await this.setState({registrationLevelId})
    //console.log('registrationLevelId',registrationLevelId)
    this.syncDoctorList()
  }

  //同步医生列表
  syncDoctorList=async()=>{
    console.log(this.state.departmentId,this.state.registrationLevelId)
    if(this.props.defaultRegistrationLevel!==null) 
      await this.setState({registrationLevelId:this.props.defaultRegistrationLevel.id})
    if(this.state.departmentId===undefined || this.state.registrationLevelId===undefined)
      return;
    const data={
      department_id:this.state.departmentId,
      registration_level_id:this.state.registrationLevelId
    }
    console.log('post sync doctor',data)
    this.props.syncDoctorList(data);
  }

  handleAgeChange = age=>{
    const now = moment();
    const birthday = now.subtract(age,'years')
    console.log(birthday)
    this.props.form.setFieldsValue({birthday})
  }

  handleBirthdayChange = birthday=>{
    const birthYear = birthday.year();
    const nowYear = moment().year();
    const age = nowYear - birthYear;
    this.props.form.setFieldsValue({age}) 
  }

  //表单提交
  handleSubmit =async e => {
    e.preventDefault();
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        const birthday = values.birthday.format('YYYY-MM-DD hh:mm:ss');
        const consultation_date = values.consultation_date.format('YYYY-MM-DD hh:mm:ss')
        values.birthday = birthday;
        values.consultation_date = consultation_date; 
        console.log('submit ',values)
        //非支付状态 请求价格
        if(!this.props.payMode) {
          await this.props.calculateFee(values)
          //console.log({truely_pay:this.props.cost})
          //form.setFieldsValue({truely_pay:form.cost})
        } //支付状态确认 打印表单
        else {
          values.should_pay =  this.props.cost;
          values.truely_pay = parseFloat(values.truely_pay)
          await this.props.submitRegistration(values)
          this.clearForm();
        }
      }
    });
  };

  cancelPaymentMode=()=>{this.props.setPaymentMode(false);}

  clearForm=()=>{this.props.form.resetFields();}

  render() {
    //年龄列表
    var ageArr = [];for(var i=0;i<150;i++) ageArr.push(i);
    const {getFieldDecorator} = this.props.form;
    const formItemLayout = {
      labelCol: {
        xs: { span: 24 },
        sm: { span: 8 },
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 },
      },
    };

    return(<Form onSubmit={this.handleSubmit.bind(this)}>
      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="姓名" {...formItemLayout}>
            {getFieldDecorator('patient_name', {
              rules: [{ required: true, message: '输入姓名' }],
            })(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="姓名" disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="性别" {...formItemLayout}>
            {getFieldDecorator('gender', {
              rules: [{ required: true, message: '选择性别' }],
              initialValue:"male"
            })(
              <Select disabled={this.props.payMode}>
                <Option value="male">男</Option>
                <Option value="female">女</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="出生日期" {...formItemLayout}>
            {getFieldDecorator('birthday', {
              rules: [{ required: true, message: '输入出生日期' }]
            })(
              <DatePicker 
                disabled={this.props.payMode}
                onChange={this.handleBirthdayChange.bind(this)}
                placeholder="出生日期"       
              />,
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="年龄" {...formItemLayout}>
            {getFieldDecorator('age', {
              rules: [{ required: true, message: '输入年龄' }],
              validator: (rule, value, callback) =>  {
                if(value>150 || value < 0) callback("不合理的年龄")
              }
            })(
              <Select showSearch onChange={this.handleAgeChange.bind(this)} disabled={this.props.payMode}>
                {ageArr.map(i=>(<Option value={i} key={i}>{i}</Option>))}
              </Select>
            )}
          </Form.Item>
        </Col>
      </Row>
      
      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="医疗类别" {...formItemLayout}>
            {getFieldDecorator('medical_category', {
              rules: [{ required: true, message: '选择医疗类别' }],
              initialValue:"default"
            })(
              <Select disabled={this.props.payMode}>
                <Option value="default">就诊</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="医疗证号" {...formItemLayout}>
            {getFieldDecorator('medical_certificate_number', {
              rules: [{ required: true, message: '输入医疗证号' }],
            })(
              <Input
                addonBefore={
                  getFieldDecorator('medical_certificate_number_type',{
                    initialValue:"id"
                  })(
                    <Select style={{ width: 90 }} disabled={this.props.payMode}>
                      <Option value={"id"}>身份证</Option>
                      <Option value={"insurance_card"}>医保卡</Option>
                    </Select>
                  )
                }
                placeholder="医疗证号"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号来源" {...formItemLayout}>
            {getFieldDecorator('registration_source', {
              rules: [{ required: true, message: '选择挂号来源' }],
              initialValue:"网络挂号"
            })(
              <Select disabled={this.props.payMode}>
                <Option value="窗口挂号">窗口挂号</Option>
                <Option value="网络挂号">网络挂号</Option>
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="家庭住址" {...formItemLayout}>
            {getFieldDecorator('address', {})(
              <Input
                prefix={<Icon type="home" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="家庭住址" disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="挂号科室" {...formItemLayout}>
            {getFieldDecorator('department_id', {
              rules: [{ required: true, message: '选择挂号科室' }],
            })(
                <Select
                  onChange={this.selectDepartment.bind(this)}
                  showSearch
                  placeholder="选择挂号科室"
                  optionFilterProp="children"
                  filterOption={(input, option) =>
                    option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                  }
                  disabled={this.props.payMode}
              >
                {this.props.departments.map(x=>(<Option value={x.id} key={x.id}>{x.name}</Option>))}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="挂号级别" {...formItemLayout}>
            {getFieldDecorator('registration_level_id', {
              rules: [{ required: true, message: '选择挂号级别' }],
              initialValue:this.props.defaultRegistrationLevel===null?0:this.props.defaultRegistrationLevel.id
            })(
              <Select onChange={this.handleRegistrationLevelChange.bind(this)} disabled={this.props.payMode}>
                {this.props.registrationLevel.map((x)=>
                  (<Option key={x.id} value={x.id}>{x.name}</Option>)
                )}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="门诊医生" {...formItemLayout}>
            {getFieldDecorator('outpatient_doctor_id', {
              rules: [{ required: true, message: '选择门诊医生' }],
            })(
              <Select disabled={this.props.outPatientDoctors.length===0 || this.props.payMode}>
                {this.props.outPatientDoctors.map((x)=>
                  (<Option key={x.uid} value={x.uid}>{x.real_name}</Option>)
                )}
              </Select>
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Form.Item label="结算类别" {...formItemLayout}>
            {getFieldDecorator('settlement_category_id', {
              rules: [{ required: true, message: '选择结算类别' }],
            })(
              <Select disabled={this.props.payMode}>
                {this.props.settlementCategory.map((x)=>
                  (<Option key={x.id} value={x.id}>{x.name}</Option>)
                )}
              </Select>
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row style={{textAlign:'center'}}>
        <Col span={6}>
          <Form.Item label="医保诊断" {...formItemLayout}>
            {getFieldDecorator('medical_insurance_diagnosis', {})(
              <Input
                prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="医保诊断" disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>

        <Col span={6}>
          <Form.Item label="看诊日期" {...formItemLayout}>
            {getFieldDecorator('consultation_date', {
              rules: [{ required: true, message: '输入看诊日期' }],
              initialValue:moment()
            })(
              <DatePicker
                placeholder="看诊日期"
                showToday
                disabled={this.props.payMode}
              />
            )}
          </Form.Item>
        </Col>

        <Col span={6}>
          <Form.Item label="病历本" {...formItemLayout}>
            {getFieldDecorator('has_record_book', {
              initialValue:0
            })(
              <RadioGroup disabled={this.props.payMode}>
              <Radio value={1}>要</Radio>
              <Radio value={0}>不要</Radio>
              </RadioGroup>
            )}
          </Form.Item>
        </Col>
      </Row>

      <Row>
        <Col span={6}>
          <div style={{textAlign:'center',display:this.props.payMode?'block':'none'}}>
          <span >应收：<span style={{fontSize:30}}>{this.props.cost}元</span></span>
          </div>
        </Col>
        <Col span={6}>
          {this.props.payMode?
          <Form.Item label="收款" style={{float:'right',display:this.props.payMode?'block':'none'}} {...formItemLayout}>
            {getFieldDecorator('truely_pay', {
              rules:[{required:true,message:'输入收款'}],
              initialValue:this.props.cost
            })(<Input
                prefix={<Icon type="money" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="实际收款" disabled={!this.props.payMode} type="number"
                onChange={(e)=>{
                  const take = e.target.value;
                  const withdraw = take-this.props.cost
                  this.props.form.setFieldsValue({retail_fee:withdraw})
                }}
              />
            )}
          </Form.Item>:null}
        </Col>
        <Col span={6}>
          <Form.Item label="找零" style={{float:'right',display:this.props.payMode?'block':'none'}} {...formItemLayout}>
            {getFieldDecorator('retail_fee', {
              initialValue:0,
              rules:[{validator:(rule, value, callback)=>{
                if(value<0) callback("实收不得小于应收！")
                else callback()
              }}]
            })(
              <Input
                prefix={<Icon type="money" style={{ color: 'rgba(0,0,0,.25)'}} />}
                placeholder="找零" disabled type="number"
              />
            )}
          </Form.Item>
        </Col>
        <Col span={6}>
          <Button 
            style={{float:'right',marginLeft:30}} size="large"
            hidden={!this.props.payMode}
            onClick={this.cancelPaymentMode.bind(this)}>
            取消
          </Button>
          <Button 
            htmlType="submit" 
            type="primary" 
            hidden={this.props.payMode}
            style={{float:'right',marginLeft:30}} size="large">
            挂号
          </Button>
          <Button 
            htmlType="submit" 
            type="danger" 
            hidden={!this.props.payMode}
            style={{float:'right',marginLeft:30}} size="large">
            付款
          </Button>
        </Col>
      </Row>
    
    </Form>)
  }
}

export default Form.create({name:'registration_form'})(RegistrationForm);
import React from 'react';
import { Form, Icon, Input, Button, Checkbox } from 'antd';
import API from '../../global/ApiConfig';

class LoginForm extends React.Component {

  state = {errorMsg:''}
  
  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields((err, values) => {
      if (!err) {
        API.request(API.login,values)
        .permissionDenied((msg)=>{this.setState({errorMsg:msg})})
        .ok((data)=>{window.location.href = "/main";})
        .submit()
      }
    });
  };

  handleChange = (e)=> {
    this.setState({errorMsg:''})
  }

  render() {
    const { getFieldDecorator } = this.props.form;
    return (
      <Form onSubmit={this.handleSubmit} onChange={this.handleChange} className="login-form">
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: '输入用户名' }],
          })(
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="用户名"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: '输入密码' }],
          })(
            <Input
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="密码"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('remember', {
            valuePropName: 'checked',
            initialValue: true,
          })(<Checkbox>自动登录</Checkbox>)}
          &nbsp;&nbsp;
          <Button className="login-form-forgot" type="link">
            忘记密码
          </Button>
          <br/>
          <span style={{color:'red'}}>{this.state.errorMsg}</span>
          <Button type="primary" block htmlType="submit" className="login-form-button">
            登录
          </Button>
        </Form.Item>
      </Form>
    );
  }
}

const WrappedLoginForm = Form.create({ name: 'normal_login' })(LoginForm);

export default WrappedLoginForm;
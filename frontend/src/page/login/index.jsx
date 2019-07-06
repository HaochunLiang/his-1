import React from 'react';
import {Card,Typography } from 'antd';

import LoginFrom from './LoginForm'

class LoginPage extends React.Component {
    
    componentDidMount() {
        document.getElementsByTagName("title")[0].innerHTML="HIS-登录"
        document.getElementsByTagName("body")[0].setAttribute("background","/bg.png")
        document.getElementsByTagName("body")[0].setAttribute("style",'background-size:cover')
    }

    render() {
        return (
        <div style={{paddingLeft:'150px',paddingTop:'200px'}}>
            <Card style={{width:'350px',height:'380px',paddingTop:'30px'}}>
                <Typography.Title>登录</Typography.Title>
                <LoginFrom/>
            </Card>
            
        </div>);
    }

    /**
     * <Typography.Paragraph>
                    初始用户 HospitialAdmin RegisteredTollCollector FinancialAdmin 
                    PharmacyOperator OutpatientDoctor DoctorOfTechnology，密码12345
            </Typography.Paragraph>
     */
}



export default LoginPage;
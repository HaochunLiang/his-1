import React from 'react';
import { Layout, Menu, Icon } from 'antd';

import DashboardSection from './DashboardSection'
import DashboardHeader from '../../section/DashboardHeader'
import API from '../../global/ApiConfig';
import Roles from '../../global/RolesGroup';

const { Footer, Sider } = Layout;

class DashboardPage extends React.Component {
  state = {
    collapsed: true,
    sectionKey:"0",
    openKey:"sub4",
    me:{
      username:"",
      real_name:"",
      department_id:1,
      department_name:"",
      role_id:1,
      role_name:"",
      title:""
    },
  };

  onCollapse = collapsed => {
    this.setState({ collapsed });
  };

  changeSection = e =>{
    this.setState({
      sectionKey:e.key,
    })
  }

  componentDidMount() {
    API.request(API.me.myInfo,{})
    .ok((me)=>{
      this.setState({me})
    }).submit();
  }

  render() {
    const DEV = false;
    const {me} = this.state;
    
    return (
      <Layout style={{ minHeight: '100vh' }}>
        <Sider onCollapse={this.onCollapse} collapsible collapsed={this.state.collapsed}>
          <div style={{height:'32px',margin:'16px'}}>
          <h1 style={{color:'white',textAlign:'center'}}>HIS</h1>
          </div>

          <Menu onClick={(e)=>this.setState({sectionKey:e.key})} theme="dark" defaultSelectedKeys={['0']} mode="inline">

            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-1"><Icon type="bank"/><span>科室管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-2"><Icon type="usergroup-add"/><span>用户管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-3"><Icon type="number"/><span>挂号级别管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-4"><Icon type="money-collect"/><span>结算类别管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-5"><Icon type="profile"/><span>诊断目录管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-6"><Icon type="project"/><span>非药品收费项目管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.HospitialAdmin)?<Menu.Item key="3-7"><Icon type="switcher"/><span>医生排班管理</span></Menu.Item>:null}
            {DEV?<Menu.Item key="0-4"><Icon type="user"/><span>----------</span></Menu.Item>:null}
            
            {(DEV || me.role_id===Roles.RegisteredTollCollector)?<Menu.Item key="4-1"><Icon type="file"/><span>挂号/退号</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.RegisteredTollCollector)?<Menu.Item key="4-2"><Icon type="pay-circle"/><span>收费/退费</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.RegisteredTollCollector)?<Menu.Item key="4-3"><Icon type="hourglass"/><span>收费员日结</span></Menu.Item>:null}
            {DEV?<Menu.Item key="0-3"><Icon type="user"/><span>----------</span></Menu.Item>:null}
       
     
            {(DEV || me.role_id===Roles.OutpatientDoctor)?<Menu.Item key="5-1"><Icon type="build"/><span>门诊病历首页</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.OutpatientDoctor)?<Menu.Item key="5-15"><Icon type="carry-out"/><span>个人工作量统计</span></Menu.Item>:null}
            {DEV?<Menu.Item key="0-2"><Icon type="user"/><span>----------</span></Menu.Item>:null}
       
       
            {(DEV || me.role_id===Roles.DoctorOfTechnology)?<Menu.Item key="6-1"><Icon type="pushpin"/><span>检查\检验</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.DoctorOfTechnology)?<Menu.Item key="6-3"><Icon type="carry-out"/><span>个人工作量统计</span></Menu.Item>:null}
            {DEV?<Menu.Item key="0-1"><Icon type="user"/><span>----------</span></Menu.Item>:null}
                  
            {(DEV || me.role_id===Roles.PharmacyOperator)?<Menu.Item key="7-1"><Icon type="shop"/><span>门诊药房</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.PharmacyOperator)?<Menu.Item key="7-3"><Icon type="bars"/><span>药品目录管理</span></Menu.Item>:null}
            {DEV?<Menu.Item key="0-0"><Icon type="user"/><span>----------</span></Menu.Item>:null}
       
            {(DEV || me.role_id===Roles.FinancialAdmin)?<Menu.Item key="8-1"><Icon type="project"/><span>费用科目管理</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.FinancialAdmin)?<Menu.Item key="8-2"><Icon type="fund"/><span>门诊日结核对</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.FinancialAdmin)?<Menu.Item key="8-3"><Icon type="bar-chart"/><span>科室工作量统计</span></Menu.Item>:null}
            {(DEV || me.role_id===Roles.FinancialAdmin)?<Menu.Item key="8-4"><Icon type="bar-chart"/><span>个人工作量统计</span></Menu.Item>:null}
            {DEV?<Menu.Item key="8-4"><Icon type="dot-chart"/><span>个人工作量统计</span></Menu.Item>:null}

          </Menu>
        </Sider>

        <Layout>
          <DashboardHeader me={this.state.me}/>
          <DashboardSection me={this.state.me} sectionKey={this.state.sectionKey}/>
          <Footer style={{textAlign:'center'}}/>
        </Layout>
        

      </Layout>
    );
  }
}

export default DashboardPage;


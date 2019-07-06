import React from 'react';
import MenuHead from './MenuHead';
import { Layout, Menu } from 'antd';

class DoctorSchedulingManagement extends React.Component {
  state = {
    current: 'setting:3',
    collapsed: false,
    sectionKey:"setting:3",
    openKey:"setting:3"
  };

  handleClick = e => {
    console.log('click ', e);
    this.setState({
      current: e.key,
    });
  };

  onCollapse = collapsed => {
    this.setState({ collapsed });
  };

  onOpenChange = selected => {
    for(var key of selected) {
      if(key!==this.state.openKey) {
        this.setState({openKey:key})
        return;
      }
    }
  }

  changeSection = e =>{
    this.setState({
      sectionKey:e.key,
    })
  }

  render() {
    return (

        <Layout style={{ minHeight: '100vh' }}>

      <Menu onClick={this.changeSection} onOpenChange={this.onOpenChange} openKeys={[this.state.openKey]} defaultSelectedKeys={['setting:3']} mode="horizontal">
        <Menu.Item key="setting:2">人员管理</Menu.Item>
        <Menu.Item key="setting:3">排班管理</Menu.Item>
   
      </Menu>

     
      <Layout>
          <MenuHead sectionKey={this.state.sectionKey}/>
    
        </Layout>
        

      </Layout>

    );
  }
}

export default DoctorSchedulingManagement;
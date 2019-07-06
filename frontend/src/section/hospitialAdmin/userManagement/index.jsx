import React from 'react';
import { Layout, Divider,Spin,Typography,Card} from 'antd';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;


class UserManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        users:[],//表格数据
        departments:[],//科室分类数据
        roles:[],//角色
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.reloadData();}

    /***************************************  数据交互   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        this.setState({loading:true})
        API.request(API.bacisInfoManagement.user.all)
        .ok((data)=>{
            var users = data.users;
            for(var d of users) {d.key = d.uid;}
            this.setState({
                roles:data.roles,
                departments:data.departments,
                users:users,
                loading:false
            });
        }).submit();
    }

    updateRow=(rowData)=>{
        API.request(API.bacisInfoManagement.user.update,rowData)
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("修改成功")
        }).submit();
    }

    newRow=(rowData)=>{
        API.request(API.bacisInfoManagement.user.add,rowData)
        .ok((data)=>{
            this.reloadData();
            Message.success("添加数据成功");
        }).submit();
    }

    deleteRow=(idsArr)=>{
        API.request(API.bacisInfoManagement.user.delete,{data:idsArr})
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("删除数据成功")
        }).submit();
    }
    
    render() {
        return (
        <Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Card>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                reloadData={this.reloadData.bind(this)}
                departments={this.state.departments}
                roles={this.state.roles}
                updateRow={this.updateRow.bind(this)}
                deleteRow={this.deleteRow.bind(this)}
                newRow={this.newRow.bind(this)}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<DataTable
                data={this.state.users} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
        </Card>  
        </Content>)
    }
}

export default UserManagement;
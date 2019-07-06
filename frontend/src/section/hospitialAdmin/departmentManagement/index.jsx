import React from 'react';
import { Layout, Divider,Spin,Typography, Card} from 'antd';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;


class DepartmentManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        tableData:[],//表格数据
        departmentClassification:[],//分类数据
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.reloadData();}

    /***************************************  API   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        API.request(API.bacisInfoManagement.department.all,{})
        .ok((data)=>{
            var tableData = data.department;
            for(var d of tableData) {d.key = d.id;}
            this.setState({
                tableData:tableData,
                departmentClassification:data.department_classification,
                loading:false
            });
        }).submit()
    }

    updateRow=(rowData)=>{
        API.request(API.bacisInfoManagement.department.update,rowData)
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("修改成功");
        }).submit()
    }

    newRow=(rowData)=>{
        API.request(API.bacisInfoManagement.department.add,rowData)
        .ok((data)=>{
            this.reloadData();
            Message.success("添加数据成功");
        }).submit()
    }

    deleteRow=(idArr)=>{
        API.request(API.bacisInfoManagement.department.delete,{data:idArr})
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("删除数据成功","")
        }).submit()
    }

    render() {
        return (
        <Content style={{ margin: '0 16px',paddingTop:5 }}>
        <Card>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                reloadData={this.reloadData.bind(this)}
                departmentClassification={this.state.departmentClassification}

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
                data={this.state.tableData} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
        </Card>
        </Content>)
    }
}

export default DepartmentManagement;
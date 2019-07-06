import React from 'react';
import { Layout, Divider,Spin,Typography, Card} from 'antd';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;

class ExpenseClassificationManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        allExpenseClassification:[],//表格数据
        loading:true,//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.reloadData();}

    /***************************************  数据交互   ******************************************* */
    //上传数据后 重置数据
    reloadData = async ()=>{
        await this.setState({loading:true});
        API.request(API.financialAdmin.expenseClassification.all)
        .ok(allExpenseClassification=>{
            allExpenseClassification.forEach(x=>x.key = x.id)
            this.setState({allExpenseClassification,loading:false})
        }).submit();
    }

    updateRow=(data)=>{
        API.request(API.financialAdmin.expenseClassification.update,data)
        .ok(data=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("修改成功")
        }).submit();
    }

    newRow=(data)=>{
        API.request(API.financialAdmin.expenseClassification.add,data)
        .ok(data=>{
            this.reloadData();
            Message.success("添加数据成功");
        }).submit();
    }

    deleteRow=(idArr)=>{
        API.request(API.financialAdmin.expenseClassification.delete,{idArr})
        .ok(data=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("删除数据成功","")
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
                data={this.state.allExpenseClassification} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
        </Card>
        </Content>)
    }
}

export default ExpenseClassificationManagement;
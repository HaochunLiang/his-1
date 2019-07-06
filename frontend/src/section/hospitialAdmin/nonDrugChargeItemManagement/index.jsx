import React from 'react';
import { Layout, Divider,Spin,Typography, Card} from 'antd';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;


class NonDrugChargeItemManagement extends React.Component {
    state = {
        selectedRows:[],//选中项
        non_drug_charge:[],//表格数据 非药品目录
        expense_classification:[],//费用分类
        department:[],//科室名称
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.reloadData();}

    resolveData=(data,department,expense_classification)=>{
        data.forEach(record=>{
            record.department_name = this.departmentId2Name(record.department_id,department);
            record.expense_classification_name = this.expenseId2Name(record.expense_classification_id,expense_classification)
        })
        return data;
    }

    departmentId2Name=(id,department)=>{
        return department.filter(x=>x.id===id)[0].name;
    }

    expenseId2Name=(id,expense_classification)=>{
        return expense_classification.filter(x=>x.id===id)[0].fee_name;
    }

    /***************************************  API   ******************************************* */
    //上传数据后 重置数据
    reloadData = ()=>{
        this.setState({loading:true})
        API.request(API.bacisInfoManagement.nonDrugItem.all,{})
        .ok((data)=>{
            var tableData = data.non_drug_charge;
            tableData.forEach(x=>x.key = x.id);
            tableData = this.resolveData(tableData,data.department,data.expense_classification)
            console.warn(tableData)
            this.setState({
                non_drug_charge:tableData,
                department:data.department,
                expense_classification:data.expense_classification,
                loading:false
            });
        }).submit();
    }

    updateRow=(rowData)=>{
        API.request(API.bacisInfoManagement.nonDrugItem.update,rowData)
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("修改成功")
        }).submit();
    }

    newRow=(rowData)=>{
        API.request(API.bacisInfoManagement.nonDrugItem.add,rowData)
        .ok((data)=>{
            this.reloadData();
            Message.success("添加数据成功");
        }).submit();
    }

    deleteRow=(idsArr)=>{
        API.request(API.bacisInfoManagement.nonDrugItem.delete,{data:idsArr})
        .ok((data)=>{
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
                department={this.state.department}
                expense_classification={this.state.expense_classification}
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
                data={this.state.non_drug_charge} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
        </Card>
        </Content>)
    }
}

export default NonDrugChargeItemManagement;
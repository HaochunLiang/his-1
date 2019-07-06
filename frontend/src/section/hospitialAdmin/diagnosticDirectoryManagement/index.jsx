import React from 'react';
import { Layout, Divider,Spin,Typography, Card} from 'antd';
import ToolBar from './ToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './DataTable'
const {Content} = Layout;


class DiagnosticDirectoryManagementSection extends React.Component {
    state = {
        selectedRows:[],//选中项
        diseases:[],//表格数据
        diseaseClassification:[],//疾病分类
        selectClassificationID:-1,
        loading:true//加载状态
    };

    //设置表格选中的数据
    setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

    //初始化加载数据
    componentDidMount = ()=>{this.init();}


    /***************************************  数据交互   ******************************************* */
    
    init = async()=>{
        API.request(API.bacisInfoManagement.diagnoseDirectory.allClassification,{})
        .ok((data)=>{
            var allClassification = data;
            this.setState({
                diseaseClassification:allClassification,
                loading:false
            });
        }).submit();
    }

    handleSelectClassification=async (selectClassificationID)=>{
        console.log('select classification id',selectClassificationID)
        await this.setState({
            loading:true,
            selectClassificationID:selectClassificationID
        });
        this.reloadData()
    }


    reloadData=()=>{
        console.log('reload disease, classification id',this.state.selectClassificationID)
        API.request(API.bacisInfoManagement.diagnoseDirectory.searchAllByClassificationId,{classification_id:this.state.selectClassificationID})
        .ok((data)=>{
            var diseases = data.diseases;
            diseases.forEach((x)=>{x.key = x.id})
            this.setState({
                diseases:diseases,
                loading:false,
                //selectClassificationID:this.state.selectClassificationID
            });
        }).submit();
    }

    updateRow=(rowData)=>{
        API.request(API.bacisInfoManagement.diagnoseDirectory.update,rowData)
        .ok((data)=>{
            this.setState({selectedRows:[]})
            this.reloadData();
            Message.success("修改成功")
        }).submit();
    }

    newRow=(rowData)=>{
        API.request(API.bacisInfoManagement.diagnoseDirectory.add,rowData)
        .ok((data)=>{
            this.reloadData();
            Message.success("添加数据成功");
        }).submit();
    }

    deleteRow=(idsArr)=>{
        API.request(API.bacisInfoManagement.diagnoseDirectory.delete,{data:idsArr})
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
                reloadData={this.reloadData.bind(this)}
                updateRow={this.updateRow.bind(this)}
                deleteRow={this.deleteRow.bind(this)}
                newRow={this.newRow.bind(this)}
                handleSelectClassification={this.handleSelectClassification.bind(this)}
                diseaseClassification={this.state.diseaseClassification}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<DataTable
                data={this.state.diseases} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
        </Card>
        </Content>)
    }
}

export default DiagnosticDirectoryManagementSection;
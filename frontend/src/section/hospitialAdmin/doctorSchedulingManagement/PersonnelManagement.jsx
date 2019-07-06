import React from 'react';

import {Layout, Divider,Spin,Typography} from 'antd'
import ToolBar from './PersonnelManage/ToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './PersonnelManage/DataTable';

const {Content} = Layout;

class PersonnelManagement extends React.Component {

  state = {
    selectedRows:[],//选中项
    shifts:[],//表格数据
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
      API.request(API.bacisInfoManagement.schedulingInfoManagement.getPersonnelInfo,{})
      .ok((data)=>{
          const shifts = data;
          for(var d of shifts) {d.key = d.id;}
              this.setState({
                  shifts:shifts,
                  loading:false
              });
      }).submit();
  }

updateRow=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.updatePersonnelInfo,data)
    .ok((data)=>{
        this.setState({selectedRows:[]})
        this.reloadData();
        Message.success("修改成功")
    }).submit();
}


newRow=(data)=>{
  API.request(API.bacisInfoManagement.schedulingInfoManagement.addPersonnelInfo,data)
  .ok((data)=>{
      this.reloadData();
      Message.success("添加数据成功");
  }).submit();
}

deleteRow=(data)=>{
  API.request(API.bacisInfoManagement.schedulingInfoManagement.deletePersonnelInfo,{data:data})
  .ok((data)=>{
      this.setState({selectedRows:[]})
      this.reloadData();
      Message.success("删除数据成功","")
  }).submit();
}

  //填入AddForm中的数据
getAddTableInfo=(data)=>{
  return new Promise((resolve,reject)=>{
  API.request(API.bacisInfoManagement.schedulingInfoManagement.getPersonnelAddInfo,{data:data})
  .ok((data)=>{
    resolve(data);
}).submit();
})
}

  //填入AddForm中的数据
  getAddNameTableInfo=(data)=>{
    return new Promise((resolve,reject)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getPersonnelAddNameInfo,{data:data})
    .ok((data)=>{
      resolve(data);
  }).submit(); 
    })
  }

  /*getAddNameTableInfo1 = (data)=>{
    const _this = this;
    var data1 = data;
    console.log('getAddNameTableInfo',data1);
    return new Promise((resolve,reject)=>{
      axios({
        method: API.schedulingInfoManagement.getPersonnelAddNameInfo.method,
        url: API.schedulingInfoManagement.getPersonnelAddNameInfo.url,
        data: {data:data},
        crossDomain: true
      }).then((res)=>{
        console.log('res',res);
        const data = res.data.data;
        data1 = data;
        if(res.data.code===Status.Ok) {
        } else if(data.code===Status.PermissionDenied) {
            Message.showAuthExpiredMessage();
        } else {
        }
        resolve(data1);
    }).catch((error)=>{
        Message.showNetworkErrorMessage();
    });
    })
  }*/

    render() {
        return(
          <Content style={{ margin: '0 16px',paddingTop:5 }}>
            <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                reloadData={this.reloadData.bind(this)}
                shifts={this.state.shifts}
                updateRow={this.updateRow.bind(this)}
                deleteRow={this.deleteRow.bind(this)}
                newRow={this.newRow.bind(this)}
                getAddTableInfo={this.getAddTableInfo.bind(this)}
                getAddNameTableInfo={this.getAddNameTableInfo.bind(this)}
            />
            <Divider/>
            {this.state.loading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<DataTable
                data={this.state.shifts} 
                rowSelection={this.state.rowSelection}
                reloadData={this.reloadData.bind(this)}
                setSelected={this.setSelected.bind(this)}
            />}
          </Content>)
      }
}

export default PersonnelManagement;
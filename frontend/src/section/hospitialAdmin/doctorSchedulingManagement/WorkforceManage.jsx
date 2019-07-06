import React from 'react';

import {Row,Col,Layout, Divider,Spin,Typography,Card,DatePicker,Modal,InputNumber} from 'antd'
import ToolBar from './WorkforceManage/ToolBar';
import ScheduleToolBar from './WorkforceManage/ScheduleToolBar';
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';
import DataTable from './WorkforceManage/DataTable';
import ScheduleTable from './WorkforceManage/ScheduleTable';
import moment from 'moment';

const {Content} = Layout;
const { RangePicker} = DatePicker;
const confirm = Modal.confirm; 
var dateRange = '';

function disabledDate(current) {
  // Can not select days before today and today
  return current && current < moment().startOf('day');
}

class WorkforceManage extends React.Component {

  state = {
    selectedRows:[],//选中项
    selectedScheduleRows:[],//排班结果选中项
    shifts:[],//表格数据
    schedules:[],//排班数据
    loading:true,//加载状态
    scheduleLoading:true,
    overwrite:false,//是否需要覆盖
    overwriteIds:[],//需覆盖的ID
    inputDate:true,//是否选择好了日期->排班按钮的disable属性
    num:3,//每日排班人数
    num1:3,
    num2:3,
    conflict:0,//新增行时检查冲突
    toAddddSchedule:false//需要向数据库中添加排班信息
  };

  //设置表格选中的数据
setSelected=(selectedRows)=>{this.setState({selectedRows:selectedRows})}

  //设置表格选中的数据
  setScheduleSelected=(selectedScheduleRows)=>{this.setState({selectedScheduleRows:selectedScheduleRows})}

//初始化加载数据
componentDidMount = ()=>{
  this.reloadData();
  this.setSchedule();
}

onDateChange = (date, dateString)=>{
  dateRange = dateString;
  this.timeConflict(dateRange);
}
onNumberChange = (value) =>{
  this.setState({num:value});
}


  //确认覆盖对话框
  showOverwriteConfirm = (data)=>{
    const _this = this;
    confirm({
      title: '选择的时间段已排班,请重新选择时间！',
      content: '如果想要覆盖已生成的排班信息，点击覆盖按钮',
      onOk() {
        console.log('覆盖',data);
        window.did = data;
        _this.setState({
          overwrite:true,
          overwriteIds:data,
          inputDate:false
      });
      },
      onCancel() {
        console.log('取消');
        _this.setState({
          inputDate:true
      });
      },
    });
  }


 

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

  //schedule
  reloadSchedule = ()=>{
    this.setState({scheduleLoading:true})
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getScheduleInfo,{})
    .ok((data)=>{
        var schedules = data;
        for(var d of schedules) {
            d.key = d.name;
          }
          this.setState({
            //schedules:schedules,
            scheduleLoading:false
          });
          this.addSchedule(schedules);
          this.setSchedule();
    }).submit();
}

setSchedule = ()=>{
    this.setState({scheduleLoading:true})
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getAllScheduleInfo,{})
    .ok((data)=>{
        var schedules = data;
        for(var d of schedules) {
          d.key = d.name;
        }
        this.setState({
          schedules:schedules,
          scheduleLoading:false
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

  //添加生成的排班信息至数据库
  addSchedule = (data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.addScheduleInfo,{data})
    .ok((data)=>{
        this.reloadData();
        Message.success("添加数据成功");
    }).submit();
}

  //schedule
  updateSchedule=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getScheduleInfo,data)
    .ok((data)=>{
        this.setState({selectedScheduleRows:[]})
        this.reloadSchedule();
        Message.success("updateSchedule")
    }).submit();
}

  //生成排班信息
  chooseRow=(data,num)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.chooseDoctor,{data,dateRange,num})
    .ok((data)=>{
        this.setState({selectedRows:[]})
        this.reloadSchedule();
    }).submit();
}

timeConflict=(inputData)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.findTimeConflict,{data:inputData})
    .ok((data)=>{
        if(data.length===0){
        this.setState({
            selectedRows:[],
            inputDate:false
          })
          //this.reloadData();         
          Message.success("选择时间段成功")
        }else{
          this.showOverwriteConfirm(data);
        }
    }).submit();
}

  findRowConflict=(data)=>{
    var isConflict = this.addRowConflict(data);
    if(isConflict==null){
      console.log('TRUE');
      this.newScheduleRow(data);
    }else{
      console.log('FALSE');
      
    }
  }
  //新增排班结果中的行
  newScheduleRow=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.addScheduleRowInfo,data)
    .ok((data)=>{
        this.reloadData();
        Message.success("添加数据成功");
    }).submit();
  }

//查找新增行中的冲突
addRowConflict=(inputData)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.findAddRowConflict,inputData)
    .ok((data)=>{
        if(data.length!==0){
            //conflict = 1;
            this.setState({conflict:1});
          }
    }).submit();
    return this.state.conflict;
  }

//删除排班结果中的行
deleteScheduleRow=(data)=>{
    const _this = this;
    API.request(API.bacisInfoManagement.schedulingInfoManagement.deleteScheduleRowInfo,{data})
    .ok((data)=>{
        _this.setState({selectedScheduleRows:[]})
        _this.reloadData();
        Message.success("删除数据成功","")
    }).submit();
  }

//填入AddForm中的数据
getAddTableInfo=(data)=>{
    const _this = this;
    return new Promise((resolve,reject)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.getAddInfo,{data:data})
    .ok((data)=>{
        _this.setState({selectedScheduleRows:[]})
        resolve(data);
  }).submit();
  })
  }

  //填入AddForm中的数据
getAddTableInfoByID=(data)=>{
  const _this = this;
  return new Promise((resolve,reject)=>{
  API.request(API.bacisInfoManagement.schedulingInfoManagement.getAddInfoByID,{data:data})
  .ok((data)=>{
      _this.setState({selectedScheduleRows:[]})
      resolve(data);
}).submit();
})
}


//覆盖排班信息
overwriteInfo=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.overwriteScheduleInfo,{data})
    .ok((data)=>{
        //this.deleteOverwriteInfo();
        Message.success("删除覆盖数据成功");
    }).submit();
  }

//删除覆盖的排班信息
/*deleteOverwriteInfo=(data)=>{
    API.request(API.bacisInfoManagement.schedulingInfoManagement.deleteOverwriteScheduleInfo,{data})
    .ok((data)=>{
        this.reloadSchedule();
        Message.success("删除覆盖数据成功");
    }).submit();
  }*/

    render() {
        return(
          <Content style={{ margin: '0 16px',paddingTop:5 }}>
            <Row>
          <Col span={6} style={{minWidth:'100px'}}>
            <Card title="循环策略" style={{overflow:'scroll',minWidth:'100px',height:'410px'}} >

            选择每日值班人数   
            <InputNumber 
                min={1} 
                max={10} 
                defaultValue={3} 
                onChange={this.onNumberChange} 
            />

            <Divider/>
            选择排班时间
            <RangePicker 
            onChange={this.onDateChange} 
            disabledDate={disabledDate}
            />
            </Card>
          </Col>
          <Col span={18}> 
          <Row>
            <Card title={
              <div>
                <div style={{float:'left',paddingTop:5}}>
                  <span>选择排班人员</span>
                </div>
                <div style={{float:'right'}}>
                <ToolBar
                disabled={this.state.loading}
                selectedRows={this.state.selectedRows}
                reloadData={this.reloadData.bind(this)}
                shifts={this.state.shifts}
                updateRow={this.updateRow.bind(this)}
                updateSchedule={this.updateSchedule.bind(this)}
                chooseRow={this.chooseRow.bind(this)}
                overwrite={this.state.overwrite}
                overwriteIds={this.state.overwriteIds}
                overwriteInfo={this.overwriteInfo.bind(this)}
                inputDate={this.state.inputDate}
                num={this.state.num}
                toAddSchedule={this.state.toAddddSchedule}
                setSchedule={this.setSchedule.bind(this)}
              />
                </div>
            </div>
            } style={{overflow:'scroll',minWidth:'100px',height:'410px'}} bodyStyle={{paddingTop:'5px',paddingBottom:'0px'}}>        
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
            </Card>
            </Row>
          </Col>
        </Row>
        <Row>
        <Card title={
          <div>
          <div style={{float:'left',paddingTop:5}}>
            <span>排班信息列表</span>
          </div>
          <div style={{float:'right'}}>
          <ScheduleToolBar
                disabled={this.state.scheduleLoading}
                selectedScheduleRows={this.state.selectedScheduleRows}
                reloadSchedule={this.reloadSchedule.bind(this)}
                getAddTableInfo={this.getAddTableInfo.bind(this)} 
                getAddTableInfoByID={this.getAddTableInfoByID.bind(this)} 
                schedules={this.state.schedules}
                updateSchedule={this.updateSchedule.bind(this)}
                deleteScheduleRow={this.deleteScheduleRow.bind(this)}
                newScheduleRow={this.newScheduleRow.bind(this)}
                findRowConflict={this.findRowConflict.bind(this)}
                addRowConflict={this.addRowConflict.bind(this)}
                conflict={this.state.conflict}
                componentDidMount={this.componentDidMount.bind(this)}
                setSchedule={this.setSchedule.bind(this)}
            />
          </div>
      </div>
        } style={{overflow:'scroll',minWidth:'100px',height:'650px'}} >
                {this.state.scheduleLoading?
            <div style={{textAlign:'center',paddingTop:100}}>
                <Spin/><br/>
                <Typography.Paragraph>加载中...</Typography.Paragraph>
            </div>
            :<ScheduleTable
                data={this.state.schedules} 
                rowSelection={this.state.rowSelection}
                reloadSchedule={this.reloadSchedule.bind(this)}
                setScheduleSelected={this.setScheduleSelected.bind(this)}
                updateSchedule={this.updateSchedule.bind(this)} 
                rowKey={this.id}
            />}
            </Card>
          </Row>
       
          </Content>)
      }
}

export default WorkforceManage;
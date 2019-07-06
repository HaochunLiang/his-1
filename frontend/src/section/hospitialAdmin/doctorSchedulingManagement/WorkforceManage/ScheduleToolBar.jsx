import React from 'react';
import {Button,Modal,Icon} from 'antd';
import AddRowForm from './AddRowForm';

const confirm = Modal.confirm;

class ScheduleToolBar extends React.Component {
  state = {
    editModalDisplay:false,
    addRowModalDisplay:false
    //conflict:0,
  }

  //显示 隐藏增加row的modal
  showAddRowModal() {
    this.setState({addRowModalDisplay:true})
  }

  hideAddRowModal() {
    this.setState({addRowModalDisplay:false})
  }

  

  //确认选择弹窗
  showDeleteConfirm() {
    const rowsNum = this.props.selectedScheduleRows.length;
    const _this = this;
    confirm({
      title: '你确定要删除吗',
      content: `将删除${rowsNum}条数据，该操作将无法恢复`,
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        const selectedScheduleRows = _this.props.selectedScheduleRows;
        _this.props.deleteScheduleRow(selectedScheduleRows);
        //_this.props.reloadSchedule();
      },
      onCancel() {
        console.log('Cancel clicked');
      },
    });
  }

  //更新数据
  reloadSchedule = ()=>{this.props.reloadSchedule()}

  render() {
      const selectedScheduleRows = this.props.selectedScheduleRows;
      const deleteButtonDisabled = selectedScheduleRows.length===0 ;//|| this.props.disabled;
      const addButtonDisabled = this.props.disabled;
      const deleteNum = selectedScheduleRows.length===0?null:selectedScheduleRows.length+"条记录";
      //this.setState({conflict:this.props.conflict});

      return (
      <div>
        <div style={{float:'right',margin:5}}>
          <Button onClick={this.props.setSchedule}>
            <Icon type="sync" spin={this.props.disabled}></Icon>
          </Button>
          &nbsp;
          <Button 
            type="danger"
            icon="delete"
            disabled={deleteButtonDisabled}
            onClick={this.showDeleteConfirm.bind(this)}
          >
            删除{deleteNum}
          </Button>
          &nbsp;
          <Button type="primary" icon="plus" 
            disabled={addButtonDisabled}
            onClick={this.showAddRowModal.bind(this)} >
            添加
          </Button>
      
        </div>

        <Modal
          destroyOnClose
          title="添加新记录"
          visible={this.state.addRowModalDisplay}
          onCancel={this.hideAddRowModal.bind(this)}
          footer={null}
        >
          <AddRowForm
            reloadSchedule={this.props.reloadSchedule.bind(this)}
            newScheduleRow={this.props.newScheduleRow.bind(this)} 
            findRowConflict={this.props.findRowConflict.bind(this)}
            addRowConflict={this.props.addRowConflict.bind(this)}
            getAddTableInfo={this.props.getAddTableInfo.bind(this)} 
            getAddTableInfoByID={this.props.getAddTableInfoByID.bind(this)}
            exit={this.hideAddRowModal.bind(this)}
            roles={this.props.roles}
            conflict={this.props.conflict}/>
        </Modal>
        
      </div>)
  }
}

export default ScheduleToolBar;

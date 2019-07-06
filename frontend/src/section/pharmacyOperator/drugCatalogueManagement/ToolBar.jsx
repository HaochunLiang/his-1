import React from 'react';
import {Button,Drawer,Breadcrumb,Icon, Modal} from 'antd';
import AddRowForm from './AddRowForm';
import EditRowForm from './EditRowForm';
import BatchImportUpload from './BatchImportUpload';
import Message from '../../../global/Message';


class ToolBar extends React.Component {
  state = {
    editDrawerDisplay:false,
    addRowDrawerDisplay:false,
    batchImportModalDisplay:false
  }

  //显示 隐藏增加row的Drawer
  showAddRowDrawer() {
    this.setState({addRowDrawerDisplay:true})
  }

  hideAddRowDrawer() {
    this.setState({addRowDrawerDisplay:false})
  }

  //显示 隐藏增加edit的Drawer
  showEditDrawer() {
    this.setState({editDrawerDisplay:true})
  }

  hideEditDrawer() {
    this.setState({editDrawerDisplay:false})
  }

  //显示 隐藏导入的Drawer
  showBatchImportModal() {
    this.setState({batchImportModalDisplay:true})
  }

  hideBatchImportModal() {
    this.setState({batchImportModalDisplay:false})
  }

  //确认删除弹窗
  showDeleteConfirm() {
    const rowsNum = this.props.selectedRows.length;
    const _this = this;
    
    Message.showConfirm(
      '你确认要删除吗',
      `将删除${rowsNum}条数据，该操作将无法恢复`,
      ()=> {
        const selectedRows = _this.props.selectedRows;
        _this.props.deleteRow(selectedRows.map(x=>x.id));
      },
      ()=> {
        console.log('Cancel clicked');
      },
    );
  }

  //处理批量导入
  batchImport() {
    alert('not implemented')
  }

  //更新数据
  reloadData = ()=>{this.props.reloadData()}

  handleUploadSuccess=()=>{
    this.setState({batchImportModalDisplay:false});
    this.props.reloadData();
  }

  render() {
      const selectedRows = this.props.selectedRows;
      const editButtonDisbbled = selectedRows.length!==1 || this.props.disabled;
      const deleteButtonDisabled = selectedRows.length===0 || this.props.disabled;
      const addButtonDisabled = this.props.disabled;
      const importButtonDisabled = this.props.disabled;
      const deleteNum = selectedRows.length===0?null:selectedRows.length+"条记录"

      return (
      <div >
        <Breadcrumb style={{float:'left',paddingTop:10}}>
          <Breadcrumb.Item>HIS</Breadcrumb.Item>
          <Breadcrumb.Item>
            门诊药房工作站
          </Breadcrumb.Item>
          <Breadcrumb.Item>
            药品管理
          </Breadcrumb.Item>
        </Breadcrumb>

        <div style={{float:'right',margin:5}}>
          <Button onClick={this.props.reloadData}>
            <Icon type="sync" spin={this.props.disabled}></Icon>
          </Button>
          &nbsp;
          <Button 
            icon="edit"
            disabled={editButtonDisbbled}
            onClick={this.showEditDrawer.bind(this)}
          >
            修改
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
            onClick={this.showAddRowDrawer.bind(this)} >
            添加
          </Button>
          &nbsp;
          <Button type="dashed" 
            disabled={importButtonDisabled}
            onClick={this.showBatchImportModal.bind(this)}>
            <Icon type="upload"/>导入
          </Button>
        </div>
        
        <Drawer
          closable
          width={500}
          destroyOnClose
          title="添加新记录"
          visible={this.state.addRowDrawerDisplay}
          onClose={this.hideAddRowDrawer.bind(this)}
          footer={null}
        >
          <AddRowForm
            reloadData={this.props.reloadData.bind(this)}
            newRow={this.props.newRow.bind(this)} 
            exit={this.hideAddRowDrawer.bind(this)}/>
        </Drawer>

        <Drawer
          width={500}
          closable
          destroyOnClose
          title="修改"
          visible={this.state.editDrawerDisplay}
          onClose={this.hideEditDrawer.bind(this)}
          footer={null}
        >
          <EditRowForm
            data={this.props.selectedRows[0]}  
            updateRow={this.props.updateRow.bind(this)}
            exit={this.hideEditDrawer.bind(this)} />
        </Drawer>

        <Modal
          destroyOnClose
          title="批量导入"
          visible={this.state.batchImportModalDisplay}
          onCancel={this.hideBatchImportModal.bind(this)}
          footer={null}
        >
          <BatchImportUpload 
            reloadData={this.props.reloadData.bind(this)} 
            handleUploadSuccess={this.handleUploadSuccess.bind(this)}/>
        </Modal>
      </div>)
  }
}

export default ToolBar;

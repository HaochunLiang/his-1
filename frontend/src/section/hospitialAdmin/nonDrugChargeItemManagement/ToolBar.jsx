import React from 'react';
import {Button,Modal,Breadcrumb,Icon} from 'antd';
import AddRowForm from './AddRowForm';
import EditRowForm from './EditRowForm';
import BatchImportUpload from './BatchImportUpload';

const confirm = Modal.confirm;

class ToolBar extends React.Component {
  state = {
    editModalDisplay:false,
    addRowModalDisplay:false,
    batchImportModalDisplay:false
  }

  //显示 隐藏增加row的modal
  showAddRowModal() {
    this.setState({addRowModalDisplay:true})
  }

  hideAddRowModal() {
    this.setState({addRowModalDisplay:false})
  }

  //显示 隐藏增加edit的modal
  showEditModal() {
    this.setState({editModalDisplay:true})
  }

  hideEditModal() {
    this.setState({editModalDisplay:false})
  }

  //显示 隐藏导入的modal
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
    confirm({
      title: '你确认要删除吗',
      content: `将删除${rowsNum}条数据，该操作将无法恢复`,
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        const selectedRows = _this.props.selectedRows;
        _this.props.deleteRow(selectedRows.map(x=>x.id));
      },
      onCancel() {
        console.log('Cancel clicked');
      },
    });
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
            基础信息管理
          </Breadcrumb.Item>
          <Breadcrumb.Item>
            非药品目录管理
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
            onClick={this.showEditModal.bind(this)}
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
            onClick={this.showAddRowModal.bind(this)} >
            添加
          </Button>
          &nbsp;
          <Button type="dashed" 
            disabled={importButtonDisabled}
            onClick={this.showBatchImportModal.bind(this)}>
            <Icon type="upload"/>导入
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
            reloadData={this.props.reloadData.bind(this)}
            newRow={this.props.newRow.bind(this)} 
            exit={this.hideAddRowModal.bind(this)}
            department={this.props.department}
            expense_classification={this.props.expense_classification} />
        </Modal>

        <Modal
          destroyOnClose
          title="修改"
          visible={this.state.editModalDisplay}
          onCancel={this.hideEditModal.bind(this)}
          footer={null}
        >
          <EditRowForm
            updateRow={this.props.updateRow.bind(this)} 
            data={this.props.selectedRows[0]}  
            exit={this.hideEditModal.bind(this)}
            department={this.props.department}
            expense_classification={this.props.expense_classification} />
        </Modal>

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

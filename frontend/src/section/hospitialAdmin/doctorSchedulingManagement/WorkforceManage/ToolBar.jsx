import React from 'react';
import {Button,Modal,Icon} from 'antd';

const confirm = Modal.confirm;

class ToolBar extends React.Component {
    state = {
        ModalText: '你确定要开始排班吗?',
        visible: false,
        confirmLoading: false,
      };
    
      showModal = () => {
        this.setState({
          visible: true,
        });
      };
    
      handleOk = () => {
        const _this = this;
        this.setState({
          ModalText: '正在排班...',
          confirmLoading: true,
        });
        //删除需要覆盖的
        if(_this.props.overwrite){
            _this.props.overwriteInfo(_this.props.overwriteIds);
            console.log('确认按钮:overwrite');
          }
  
         //生成排班信息
          const selectedRows = _this.props.selectedRows;
          console.log('selectedRows',selectedRows);
          _this.props.chooseRow(selectedRows,_this.props.num);
        setTimeout(() => {
          this.setState({
            visible: false,
            confirmLoading: false,
          });
          this.props.setSchedule();
        }, 1000);
      };
    
      handleCancel = () => {
        console.log('取消');
        this.setState({
          visible: false,
        });
      };
    
  /*state = {
    //editModalDisplay:false,
    //addRowModalDisplay:false,
    //batchImportModalDisplay:false
  }*/

  //更新数据
  reloadData = ()=>{this.props.reloadData()}

  //确认选择弹窗
  showChooseConfirm() {
    confirm({
      title: '你确定要开始排班吗',
      content: ` `,
      okText: '确定',
      okType: 'danger',
      cancelText: '取消',
      onOk() {
        //删除需要覆盖的
        if(this.props.overwrite){
          this.props.overwriteInfo(this.props.overwriteIds);
          console.log('确认按钮:overwrite');
        }
       //生成排班信息
        const selectedRows = this.props.selectedRows;
        console.log('selectedRows',selectedRows);
        this.props.chooseRow(selectedRows,this.props.num);
        this.props.setSchedule();
      },
      onCancel() {
        console.log('Cancel clicked');
      },
    });
  }

  render() {
      const selectedRows = this.props.selectedRows;
      const chooseButtonDisabled = selectedRows.length===0 || this.props.disabled ||this.props.inputDate;
      const chooseNum = selectedRows.length===0?null:selectedRows.length+"名人员排班";
      const { visible, confirmLoading, ModalText } = this.state;
      return (
        
      <div>
        <div>
        <div style={{float:'right',margin:0}}>

          <Button onClick={this.props.reloadData}>
            <Icon type="sync" spin={this.props.disabled}></Icon>
          </Button>
          <Button 
            type="danger"
            disabled={chooseButtonDisabled}
            onClick={this.showModal.bind(this)}
          >
          选择{chooseNum}
          </Button>
          <Modal
          title="生成排班信息"
          visible={visible}
          onOk={this.handleOk}
          confirmLoading={confirmLoading}
          onCancel={this.handleCancel}
        >
          <p>{ModalText}</p>
        </Modal>
          
        </div>
        </div>
      </div>)
  }
}

export default ToolBar;
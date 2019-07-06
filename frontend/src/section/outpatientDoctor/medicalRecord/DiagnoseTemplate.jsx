import React from 'react';
import {Tree, Modal,Table,Card,Button,Spin} from 'antd'
import Message from '../../../global/Message';
import DiagnoseTemplateEdit from './DiagnoseTemplateEdit'

const DirectoryTree = Tree.DirectoryTree;
const { TreeNode } = Tree;


class DiagnoseTemplate extends React.Component {

  state = {
    selectedTemplate:null,
    editTemplateModalVisible:false,
    editingTemplate:null,
    editMode:null //"new" | "update" 
  }

  componentDidMount=()=>{this.props.onRef(this)}

  onSelect=(selectkeys)=>{
    const {allDiagnoseTemplate} = this.props;
    const id = parseInt(selectkeys[0]);
    for(var type of ["personal","department","hospital"]) {
      var templates = allDiagnoseTemplate[type].filter(x=>x.id===id);
      if(templates.length===1) {
        this.setState({selectedTemplate:templates[0]})
        return;
      }
      //Message.showConfirm('使用模板','你确定要使用改')
    }
  }

  //引用模板
  handleApplyTemplate=()=>{
    const {selectedTemplate} = this.state;
    if(selectedTemplate===null) return;
    Message.showConfirm('诊断模板','你确定要使用该诊断模板吗?',()=>{
      this.props.applyTemplate(selectedTemplate.id);
    })
  }

  //删除模板
  handleDeleteTemplate=()=>{
    const {selectedTemplate} = this.state;
    if(selectedTemplate===null) return;
    Message.showConfirm('诊断模板','你确定要删除该诊断模板吗?',()=>{
      const id = selectedTemplate.id;
      this.props.deleteDiagnoseTemplate(id);
    })
  }

  //开启/关闭 模板编辑器
  hideEditTemplateModal=()=>{this.setState({editTemplateModalVisible:false})}

  showEditTemplateModal=(mode,editingTemplate)=>{
    this.setState({
      editTemplateModalVisible:true,
      editMode:mode,
      editingTemplate:editingTemplate
    })
  }

  //修改模板修改
  handleUpdateTemplateSubmit=(mode,values)=>{
    if(mode==="update")
      this.props.updateDiagnoseTemplate(values);
    else if(mode==="new")
      this.props.createDiagnoseTemplate(values)
  }

  render() {
    const disabled = this.props.disabled;
    const {allDiagnoseTemplate} = this.props;
    const {selectedTemplate,editMode,editingTemplate} = this.state;
    
    var personal,department,hospital = null;
    if(allDiagnoseTemplate) {
      personal = allDiagnoseTemplate.personal;
      department = allDiagnoseTemplate.department;
      hospital = allDiagnoseTemplate.hospital;
    }

    return (
    <div >
      <Card style={{minHeight:'300px'}}>
      {allDiagnoseTemplate===null?(<Spin style={{textAlign:'center'}}>载入中</Spin>):
        <DirectoryTree multiple defaultExpandAll onExpand={this.onExpand} showSearch onSelect={this.onSelect.bind(this)} >
          {personal===null?null:
          <TreeNode title="我的模板" key="0-0" selectable={false}>
            {personal.map(x=>(<TreeNode title={x.title} key={x.id} isLeaf/>))}
          </TreeNode>}
          {department===null?null:
          <TreeNode title="科室模板" key="0-1" selectable={false}>
            {department.map(x=>(<TreeNode title={x.title} key={x.id} isLeaf />))}
          </TreeNode>}
          {hospital===null?null:
          <TreeNode title="全院模板" key="0-2" selectable={false}>
            {hospital.map(x=>(<TreeNode title={x.title} key={x.id} isLeaf />))}
          </TreeNode>}
        </DirectoryTree>}
      </Card>
      
      <Card style={{height:'400px'}} title={
        <div>
          <span style={{float:'left'}}>详细</span>
          <Button
            style={{float:'right',marginRight:'10px'}}
            icon="plus" type="primary" size="small"
            onClick={()=>{this.showEditTemplateModal("new",{
              chinese_diagnose:[],
              western_diagnose:[]
            })}}
            >新建</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="delete" type="danger" size="small"
            onClick={this.handleDeleteTemplate.bind(this)} 
            disabled={selectedTemplate===null}>删除</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="edit" type="danger" size="small" 
            onClick={()=>{this.showEditTemplateModal("update",selectedTemplate)}} 
            disabled={selectedTemplate===null}>修改</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="check" type="primary" size="small"
            onClick={this.handleApplyTemplate.bind(this)} 
            disabled={selectedTemplate===null || disabled}>应用</Button>
        </div>
      }>
        {selectedTemplate===null?null:
        <div>
          <Table
            title={()=>"西医诊断"} bordered={false}
            size="small" pagination={false}
            dataSource={selectedTemplate.western_diagnose}
            columns={[
              {title:"ICD编码",dataIndex:"disease_code"},
              {title:"名称",dataIndex:"disease_name"}
            ]}
          />
          <br/>
          <Table
            title={()=>"中医诊断"} bordered={false}
            size="small" pagination={false}
            dataSource={selectedTemplate.chinese_diagnose}
            columns={[
              {title:"诊断编码",dataIndex:"disease_code"},
              {title:"名称",dataIndex:"disease_name"}
            ]}
          />
        </div>}
      </Card>


      <Modal 
        destroyOnClose closable
        visible={this.state.editTemplateModalVisible}
        footer={null}
        onCancel={this.hideEditTemplateModal.bind(this)}
        title={editMode==="update"?"编辑模板":"创建模板"} width={700}
        >
          {editingTemplate?
          <DiagnoseTemplateEdit
            mode={editMode}
            template={editingTemplate}
            allDiagnoses={this.props.allDiagnoses}
            handleUpdateTemplateSubmit={this.handleUpdateTemplateSubmit.bind(this)}
            />:null}
      </Modal>

    </div>)
  }
}

export default DiagnoseTemplate;

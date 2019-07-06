import React from 'react';
import {Row,Col,Modal, Form,Tree,Card,Button,Spin} from 'antd'
import Message from '../../../global/Message';
import MedicalRecordTemplateEdit from './MedicalRecordTemplateEdit'

const DirectoryTree = Tree.DirectoryTree;
const { TreeNode } = Tree;

class MedicalRecordTemplate extends React.Component {

  state = {
    selectedTemplate:null,
    editTemplateModalVisible:false,
    editingTemplate:null,
    editMode:null //update | new
  }


  componentDidMount=()=>{this.props.onRef(this)}

  onSelect=(selectkeys)=>{
    const {allMedicalRecordTemplate} = this.props;
    const id = parseInt(selectkeys[0]);
    for(var type of ["personal","department","hospital"]) {
      var templates = allMedicalRecordTemplate[type].filter(x=>x.id===id);
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
    Message.showConfirm('病历模板','你确定要使用该模板吗?',()=>{
      this.props.applyTemplate(selectedTemplate.id);
    })
  }

  //删除模板
  handleDeleteTemplate=()=>{
    const {selectedTemplate} = this.state;
    if(selectedTemplate===null) return;
    Message.showConfirm('病历模板','你确定要删除该模板吗?',()=>{
      const id = selectedTemplate.id;
      this.props.deleteMedicalRecordTemplate(id);
    })
  }

  //开启/关闭 模板编辑器
  showEditTemplateModal=(mode,editingTemplate)=>{
    this.setState({
      editTemplateModalVisible:true,
      editMode:mode,
      editingTemplate:editingTemplate
    })
  }
    
  hideEditTemplateModal=()=>{
    this.setState({
      editTemplateModalVisible:false,
      editingTemplate:null
  })}

  //修改模板修改
  handleUpdateTemplateSubmit=(mode,values)=>{
    if(mode==="update")
      this.props.updateMedicalRecordTemplate(values);
    else if(mode==="new")
      this.props.createMedicalRecordTemplate(values)
  }

  render() {
    const disabled = this.props.disabled;
  
    const {allMedicalRecordTemplate} = this.props;
    const {selectedTemplate,editMode,editingTemplate} = this.state;
    var personal,department,hospital = null;
    if(allMedicalRecordTemplate) {
      personal = allMedicalRecordTemplate.personal;
      department = allMedicalRecordTemplate.department;
      hospital = allMedicalRecordTemplate.hospital;
    }
  
    if(editingTemplate && editingTemplate.type===undefined) editingTemplate.type = 0;
    if(editingTemplate && editingTemplate.title===undefined) editingTemplate.title = '未命名';

    return (
    <div >
      <Card style={{minHeight:'300px'}}>
      {allMedicalRecordTemplate===null?(<Spin style={{textAlign:'center'}}>载入中</Spin>):
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
            onClick={()=>{this.showEditTemplateModal("new",{})}}
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
            <Row><Col span={10}><b>主诉：</b></Col><Col span={14}>{selectedTemplate.chief_complaint}</Col></Row>
            <Row><Col span={10}><b>现病史：</b></Col><Col span={14}>{selectedTemplate.current_medical_history}</Col></Row>
            <Row><Col span={10}><b>现病治疗情况：</b></Col><Col span={14}>{selectedTemplate.current_treatment_situation}</Col></Row>
            <Row><Col span={10}><b>既往史：</b></Col><Col span={14}>{selectedTemplate.past_history}</Col></Row>
            <Row><Col span={10}><b>过敏史：</b></Col><Col span={14}>{selectedTemplate.allergy_history}</Col></Row>
            <Row><Col span={10}><b>体格诊断</b></Col><Col span={14}>{selectedTemplate.physical_examination}</Col></Row>
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
          <MedicalRecordTemplateEdit
            mode={editMode}
            template={editingTemplate}
            handleUpdateTemplateSubmit={this.handleUpdateTemplateSubmit.bind(this)}
            />:null}
      </Modal>
    </div>)
  }
}

export default Form.create({name:"template_update"})(MedicalRecordTemplate);

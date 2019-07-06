import React from 'react';
import {Tree,Button, Table} from 'antd'
import Message from '../../../global/Message';

const { TreeNode, DirectoryTree } = Tree;

class TemplateSelector extends React.Component {

  state={
    selectedTemplate:null
  }

  resolveTemplates=(all)=>{
    var templates = {};
    all.forEach(x=>x.key = x.id)
    templates.personal = all.filter(x=>x.display_type===0);
    templates.department = all.filter(x=>x.display_type===1);
    templates.hospital = all.filter(x=>x.display_type===2);
    return templates;
  }


  handleSelect=async(key)=>{
    const id = parseInt(key);
    this.props.getTemplatesDetail(id,(selectedTemplate)=>{
      this.setState({selectedTemplate})
    })
  }

  render() {
    const {allTemplates,editorOpen} = this.props;
    //const {disabled} = this.props;
    const {selectedTemplate} = this.state;
    const templates = this.resolveTemplates(allTemplates);
    const toolBarDisabled = selectedTemplate===null || selectedTemplate===undefined;
    if(selectedTemplate) console.warn(selectedTemplate)
    return(
    <div>
      <DirectoryTree multiple defaultExpandAll onSelect={(key)=>this.handleSelect(key)} >
      {[{title:"个人",key:"personal"},{title:"科室",key:"department"},{title:"全院",key:"hospital"}].map(type=>(
        <TreeNode title={type.title} key={type.key} selectable={false}>
          {templates[type.key].map(x=>(
          <TreeNode title={x.template_name} key={x.id} isLeaf />))}
        </TreeNode>
      ))}
      </DirectoryTree>

      <br/>
      <div style={{float:'right'}}>
          <Button
            style={{float:'right',marginRight:'10px'}}
            icon="plus" type="primary" size="small"
            onClick={()=>this.props.openEditor("newTemplate")}
            >新建</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="delete" type="danger" size="small"
            onClick={()=>{
              Message.showConfirm('删除','你确认要删除吗？',()=>{
                this.setState({selectedTemplate:null})
                this.props.deleteTemplate([selectedTemplate.id])
              })
            }} 
            disabled={toolBarDisabled}>删除</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="edit" type="danger" size="small"
            onClick={()=>this.props.openEditor("updateTemplate",selectedTemplate.items.map(x=>x.non_drug_item),selectedTemplate.id,selectedTemplate.display_type,selectedTemplate.template_name)} 
            disabled={toolBarDisabled}>修改</Button>
          <Button 
            style={{float:'right',marginRight:'10px'}} 
            icon="check" type="primary" size="small"
            onClick={()=>{
              Message.showConfirm('使用组套','你确定要使用该组套吗？',()=>{
                this.props.applyTemplate(selectedTemplate)
              })
            }} 
            disabled={!editorOpen || selectedTemplate===null}>应用</Button>
      </div>

      {selectedTemplate?
        <div>
          <br/><br/>
          <Table
            style={{marginTop:'10px'}}
            title={()=>"详情"}
            size="small"
            dataSource={selectedTemplate.items}
            columns={[
              {title:"项目名称",dataIndex:"non_drug_item.name"},
              {title:"规格",dataIndex:"non_drug_item.format"},
              {title:"费用",dataIndex:"non_drug_item.fee"}
            ]}
          />
        </div>
       :null}
      
    </div>)
  }
}

export default TemplateSelector;
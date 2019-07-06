import React from 'react';
import {Table, Collapse, Select, Button, Input,Radio} from 'antd'
import Message from '../../../global/Message';

const {Option} = Select;
const {Panel} = Collapse;

class IADEditTable extends React.Component {


  state={
    visible:false,
    mode:"init",
    activeKey:null,

    id:-1,
    non_drug_items:[],
    templateDisplayType:0,
    templateName:"",
  }
  
  componentDidMount=()=>{this.props.onRef(this)}

  open=(mode,non_drug_items=[],id=-1,templateDisplayType=0,templateName="新模板")=>{
    non_drug_items.forEach(x=>x.key=x.id)
    this.setState({
      visible:true,
      activeKey:['1'],
      mode:mode,
      non_drug_items,
      id,
      templateDisplayType,
      templateName
    })
  }

  close=()=>{
    this.setState({
      visible:false,
      activeKey:null
    })
  }

  checkRowExist=(id)=>{
    const {non_drug_items} = this.state;
    const matched = non_drug_items.filter(x=>x.id===id)
    return matched.length>0;
  }

  addItem=(id)=>{
    if(!this.checkRowExist(id)) {
      const {allItems} = this.props;
      const newItem = allItems.filter(x=>x.id===id)[0]
      const {non_drug_items} = this.state;
      var newData = non_drug_items;
      newData.push(newItem)
      this.setState({non_drug_items:newData})
    } else {
      Message.error('该项目已存在！')
    }
  }

  deleteRow=(index)=>{
    const {non_drug_items} = this.state;
    var newData = non_drug_items;
    newData.splice(index,1);
    this.setState({non_drug_items:newData})
  }

  apply=(non_drug_items)=>{
    non_drug_items.forEach(x=>x.key=x.id)
    this.setState({non_drug_items})
  }

  getData=()=>{
    return {
      id:this.state.id,
      non_drug_items:this.state.non_drug_items
    }
  }

  clear=()=>{
    this.setState({non_drug_items:[]})
  }

  cancel=()=>{
    this.props.closeEditor();
  }

  changeTemplateDisplayType=(e)=>{
    this.setState({
      templateDisplayType: e.target.value,
    });
  }

  changeTemplateName=(e)=>{
    this.setState({
      templateName:e.target.value
    })
  }

  handleSubmit=()=>{
    const {mode,non_drug_items,id,templateName,templateDisplayType} = this.state;
    const non_drug_id_list = non_drug_items.map(x=>x.id)
    switch(mode) {
      case "new":{
        this.props.createIAD(non_drug_id_list);
        break;
      }
      case "update":{
        this.props.updateIAD(id,non_drug_id_list);
        break;
      }
      case "newTemplate":{
        this.props.createTemplate(templateDisplayType,templateName,non_drug_id_list)
        break;
      }
      case "updateTemplate":{
        this.props.updateTemplate(id,templateDisplayType,templateName,non_drug_id_list)
        break;
      }
      default:{
        console.error('unknown mode')
      }
    }
  }

  render() {
    const {allItems} = this.props;
    const {visible,non_drug_items,mode,activeKey} = this.state;
    var modeName = "编辑器";
    if(mode==="new") modeName="创建";
    else if(mode==="update") modeName="修改";
    else if(mode==="newTemplate") modeName="创建组套";
    else if(mode==="updateTemplate") modeName="修改组套";

    return(
      <Collapse activeKey={activeKey} destroyInactivePanel bordered={false}>
        <Panel key={'1'} header={modeName} disabled={!visible}>
          
          <Select
            showSearch
            style={{ width: 200 }}
            placeholder="添加项目"
            optionFilterProp="children"
            onSelect={(id)=>this.addItem(id)}
            filterOption={(input, option) =>
              option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            >
              {allItems.map(item=>(
                <Option key={item.id} value={item.id}>{item.name}</Option>
              ))}
          </Select>

          <Button style={{marginLeft:'20px'}} type="primary" onClick={this.handleSubmit.bind(this)} disabled={non_drug_items.length===0}>提交</Button>
          <Button style={{marginLeft:'20px'}} type="default" onClick={this.clear.bind(this)} disabled={non_drug_items.length===0}>清空</Button>
          <Button style={{marginLeft:'20px'}} type="danger" onClick={this.cancel.bind(this)}>关闭</Button>
          <br/><br/>

          {(mode==="newTemplate"||mode==="updateTemplate")?(
            <div style={{marginTop:'10px',marginBottom:'10px'}}>
             <Radio.Group 
              onChange={this.changeTemplateDisplayType} 
              value={this.state.templateDisplayType}>
              <Radio value={0}>个人</Radio>
              <Radio value={1}>科室</Radio>
              <Radio value={2}>全医院</Radio>
           </Radio.Group>
            <Input 
              style={{marginLeft:'10px',width:'200px'}}
              placeholder="输入组套名称" 
              value={this.state.templateName} 
              onChange={this.changeTemplateName.bind(this)}/>
            </div>
          ):null} 

          <Table
            size="small"
            dataSource={non_drug_items}
            columns={[
              {title:"项目名称",dataIndex:"name"},
              {title:"项目编码",dataIndex:"code"},
              {title:"拼音",dataIndex:"pinyin"},
              {title:"规格",dataIndex:"format"},
              {title:"费用",dataIndex:"fee"},
              {title:"操作",dataIndex:"id",
                render:(text,record,index)=>(<Button type="link" onClick={()=>{this.deleteRow(index)}}>删除</Button>)
              }
            ]}
          />
             
        </Panel>
      </Collapse>
    )
  }

}

export default IADEditTable;
import React from 'react';
import {Table, Collapse, Select, Button, Input,Radio} from 'antd'
import Message from '../../../global/Message';

const {Option} = Select;
const {Panel} = Collapse;

class PrescriptionEditTable extends React.Component {

  state={
    visible:false,
    mode:"init",
    activeKey:null,

    id:-1,
    prescription_item_list:[],
    templateDisplayType:0,
    templateName:"",
  }

  /**
prescription_item_list:[
{drug_id:9, amount:2 note usage dosage frequency day_count times 
], */
  
  componentDidMount=()=>{this.props.onRef(this)}

  open=(mode,prescription_item_list=[],id=-1,templateDisplayType=0,templateName="新模板")=>{
    console.warn(mode,prescription_item_list,id,templateDisplayType,templateName)
    prescription_item_list.forEach(x=>x.key=x.id)
    this.setState({
      visible:true,
      activeKey:['1'],
      mode:mode,
      prescription_item_list,
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
    const {prescription_item_list} = this.state;
    const matched = prescription_item_list.filter(x=>x.drug_id===id)
    return matched.length>0;
  }

  addItem=(id)=>{
    if(!this.checkRowExist(id)) {
      const {allDrugs} = this.props;
      const drug = allDrugs.filter(x=>x.id===id)[0]
      const newItem = {
        name:drug.name,
        code:drug.code,
        key:drug.id,
        drug_id:drug.id,
        amount:1,
        note:"无",
        usage:"默认",
        dosage:"默认",
        frequency:"默认",
        day_count:1,
        times:1
      }
      const {prescription_item_list} = this.state;
      var newData = prescription_item_list;
      newData.push(newItem)
      this.setState({prescription_item_list:newData})
    } else {
      Message.error('该项目已存在！')
    }
  }

  deleteRow=(index)=>{
    const {prescription_item_list} = this.state;
    var newData = prescription_item_list;
    newData.splice(index,1);
    this.setState({prescription_item_list:newData})
  }

  apply=(template)=>{
    console.warn(template)
    var drug_codes = template.items.map(x=>x.drug_item).map(x=>x.code);
    var drug_ids = template.items.map(x=>x.drug_item).map(x=>x.id);
    var drug_names = template.items.map(x=>x.drug_item).map(x=>x.name);
    var prescription_item_list = [];
    for(var i=0;i<drug_ids.length;i++) 
      prescription_item_list.push({
        drug_id:drug_ids[i],
        name:drug_names[i],
        code:drug_codes[i],
        key:drug_ids[i],
        amount:1,
        note:"无",
        usage:"默认",
        dosage:"默认",
        frequency:"默认",
        day_count:1,
        times:1
      })
    this.setState({
      id:template.id,
      prescription_item_list
    })
  }

  getData=()=>{
    return {
      id:this.state.id,
      prescription_item_list:this.state.prescription_item_list
    }
  }

  clear=()=>{
    this.setState({prescription_item_list:[]})
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

  makeArray=(s,e)=>{
    var res = [];
    for(var i=s;i<e;i++)
      res.push(i);
    return res;
  }

  handleSubmit=()=>{
    const {mode,prescription_item_list,id,templateName,templateDisplayType} = this.state;
    switch(mode) {
      case "new":{
        console.warn(prescription_item_list)
        this.props.createPrescription(prescription_item_list);
        break;
      }
      case "update":{
        this.props.updatePrescription(id,prescription_item_list);
        break;
      }
      case "newTemplate":{
        this.props.createTemplate(templateDisplayType,templateName,prescription_item_list)
        break;
      }
      case "updateTemplate":{
        this.props.updateTemplate(id,templateDisplayType,templateName,prescription_item_list)
        break;
      }
      default:{
        console.error('unknown mode')
      }
    }
  }

  handleInputChange=(index,column,e)=>{
    var {prescription_item_list} = this.state;
    const value = e.target.value;
    //console.warn(index,column,value);
    prescription_item_list[index][column] = value;
    this.setState({prescription_item_list});
  }

  handleSelectChange=(index,column,value)=>{
    var {prescription_item_list} = this.state;
    //console.warn(index,column,value);
    prescription_item_list[index][column] = value;
    this.setState({prescription_item_list});
  }

  //表单验证
  tableValidate=()=>{
    /**
     *    visible:false,
    mode:"init",
    activeKey:null,

    id:-1,
    prescription_item_list:[],
    templateDisplayType:0,
    templateName:"",
     */
    const {prescription_item_list,mode,templateDisplayType,templateName} = this.state;
    if(prescription_item_list.length===0) return false;
    if(mode==="newTemplate" || mode==="updateTemplate") {
      const b1 = templateDisplayType>=0 && templateDisplayType <=2;
      const b2 = templateName!=="" && templateName!==undefined && templateName!==null;
      return b1&&b2;
    }
    return true;
  }

  render() {
    const {allDrugs} = this.props;
    const {visible,prescription_item_list,mode,activeKey} = this.state;
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
              {allDrugs.map(item=>(
                <Option key={item.id} value={item.id}>{item.name}</Option>
              ))}
          </Select>

          <Button style={{marginLeft:'20px',marginBottom:'10px'}} type="primary" onClick={this.handleSubmit.bind(this)} disabled={!this.tableValidate()}>保存</Button>
          <Button style={{marginLeft:'20px',marginBottom:'10px'}} type="dashed" onClick={this.clear.bind(this)}>清空</Button>
          <Button style={{marginLeft:'20px',marginBottom:'10px'}} type="danger" onClick={this.cancel.bind(this)}>关闭</Button>
          

          {(mode==="newTemplate"||mode==="updateTemplate")?(
            <div style={{marginTop:'10px',marginBottom:'10px'}}>
             <Radio.Group 
              defaultValue={0}
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
            dataSource={prescription_item_list}
            columns={[
              {title:"药品名称",dataIndex:"name"},
              {title:"编号",dataIndex:"code"},
              {title:"数量",dataIndex:"amount",
                render:(text,record,index)=>(
                <Select style={{width:'50px'}} size="small" value={text} onChange={(e)=>this.handleSelectChange(index,"amount",e)} >
                  {this.makeArray(1,100).map(x=>(<Select.Option key={x} value={x}>{x}</Select.Option>))}
                </Select>)
              },
              {title:"用法",dataIndex:"usage",
                render:(text,record,index)=>(
                  <Input defaultValue={text} style={{width:'120px'}} size="small" value={text} onChange={(e)=>this.handleInputChange(index,"usage",e)}/>)
              },
              {title:"剂量",dataIndex:"dosage",
                render:(text,record,index)=>(
                  <Input defaultValue={text} style={{width:'100px'}} size="small" value={text} onChange={(e)=>this.handleInputChange(index,"dosage",e)}/>)
              },
              {title:"频次",dataIndex:"frequency",
                render:(text,record,index)=>(
                  <Input defaultValue={text} style={{width:'100px'}} size="small" value={text} onChange={(e)=>this.handleInputChange(index,"frequency",e)}/>)
              },
              {title:"天数",dataIndex:"day_count",
                render:(text,record,index)=>(
                  <Select style={{width:'50px'}} size="small" value={text} onChange={(e)=>this.handleSelectChange(index,"day_count",e)}>
                    {this.makeArray(1,100).map(x=>(<Select.Option key={x} value={x}>{x}</Select.Option>))}
                  </Select>)  
              },
              {title:"次数",dataIndex:"times",
                render:(text,record,index)=>(
                  <Select style={{width:'50px'}} size="small" value={text} onChange={(e)=>this.handleSelectChange(index,"times",e)}>
                    {this.makeArray(1,100).map(x=>(<Select.Option key={x} value={x}>{x}</Select.Option>))}
                  </Select>)
              },
              {title:"医嘱",dataIndex:"note",
                render:(text,record,index)=>(
                  <Input defaultValue={text} style={{width:'120px'}} size="small" value={text} onChange={(e)=>this.handleInputChange(index,"note",e)}/>)
              },
              {title:"操作",
                render:(text,record,index)=>(<Button type="link" onClick={()=>{this.deleteRow(index)}}>删除</Button>)
              }
            ]}
          />
             
        </Panel>
      </Collapse>
    )
  }

}

export default PrescriptionEditTable;
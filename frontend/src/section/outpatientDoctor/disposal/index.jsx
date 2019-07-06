import React from 'react';
import {Row,Col,Card,Tabs, Typography, Divider} from 'antd'
import IADDisplay from './IADDisplay';
import TemplateSelector from './TemplateSelector'
import IADEditTable from './IADEditTable'
import API from '../../../global/ApiConfig';
import Message from '../../../global/Message';

const IAD_TYPE = 2;

class DisposalSection extends React.Component {
  state={
    allItems:[],
    currentList:[],
    allTemplates:[],
    loading:true,
    editorOpen:false
  }
  
  componentDidMount=async()=>{
    this.props.onRef(this)
    await this.loadAllItems();
    await this.asyncAllTemplates();
    await this.syncCurrentList();
    this.setState({loading:false})
  }

  reload=async()=>{
    await this.asyncAllTemplates();
    await this.syncCurrentList();
    Message.success('已刷新')
  }

  applyTemplate=(template)=>{
    this.IADEditTable.apply(template.items.map(x=>x.non_drug_item))
  }
  

  //加载所有项目
  loadAllItems=async()=>{
    API.request(API.outpatientDoctor.IAD.allItems,{type:IAD_TYPE})
    .ok(allItems=>{
      this.setState({allItems})
    }).submit();
  }

  //同步当前的项目
  syncCurrentList=async()=>{
    const {medical_record_id} = this.props.currentPatient.registration;
    API.request(API.outpatientDoctor.IAD.list,{
      type:IAD_TYPE,
      medical_record_id
    }).ok(currentList=>{
      this.setState({currentList})
    }).submit();
  }

  //查看结果
  getIADResult=(exam_item_id,callback)=>{
    API.request(API.doctorOfTechnology.IADExcute.getResult,{exam_item_id})
    .ok(result=>{
      callback(result)
    }).submit();
  }

  //创建
  createIAD=(non_drug_id_list)=>{
    const {medical_record_id} = this.props.currentPatient.registration;
    API.request(API.outpatientDoctor.IAD.create,{
      type:IAD_TYPE,
      medical_record_id,
      non_drug_id_list
    }).ok(data=>{
      Message.success('创建成功！');
      this.syncCurrentList();
    }).submit();
  }

  //删除
  deleteIAD=(idArr)=>{
    API.request(API.outpatientDoctor.IAD.delete,{
      id:idArr
    }).ok(data=>{
      Message.success('删除成功！');
      this.syncCurrentList();
    }).submit();
  }

  //更新
  updateIAD=(id,non_drug_id_list)=>{
    API.request(API.outpatientDoctor.IAD.update,{
      type:IAD_TYPE,
      id,
      non_drug_id_list
    }).ok(data=>{
      Message.success('更新成功！');
      this.syncCurrentList();
    }).submit();
  }

  //发送
  sendIAD=(idArr)=>{
    API.request(API.outpatientDoctor.IAD.send,{id:idArr})
    .ok(data=>{
      Message.success('项目已申请！');
      this.syncCurrentList();
    }).submit()
  }

  //废弃
  cancelIAD=(idArr)=>{
    API.request(API.outpatientDoctor.IAD.cancel,{id:idArr})
    .ok(data=>{
      Message.success('项目已废弃！');
      this.syncCurrentList();
    }).submit()
  }

  /********************* 组套 *********************** */
  //获取全部组套
  asyncAllTemplates=async()=>{
    API.request(API.outpatientDoctor.IADTemplate.all,{
      type:IAD_TYPE
    }).ok(allTemplates=>{
      this.setState({allTemplates})
    }).submit()
  }

  //获取组套详情
  getTemplatesDetail=(exam_template_id,callback)=>{
    API.request(API.outpatientDoctor.IADTemplate.detail,{
      exam_template_id
    }).ok(data=>{
      callback(data)
    }).submit();
  }

  //存为组套 创建
  createTemplate=(display_type,template_name,non_drug_id_list)=>{
    API.request(API.outpatientDoctor.IADTemplate.create,{
      type:IAD_TYPE,
      display_type,
      template_name,
      non_drug_id_list
    }).ok(data=>{
      Message.success('组套创建成功！')
      this.asyncAllTemplates();
    }).submit();
  }

  //删除组套
  deleteTemplate=(idArr)=>{
    API.request(API.outpatientDoctor.IADTemplate.delete,{id:idArr})
    .ok(data=>{
      Message.success('组套删除成功！')
      this.asyncAllTemplates();
    }).submit();
  }

  //更新组套 暂存
  updateTemplate=(id,display_type,template_name,non_drug_id_list)=>{
    API.request(API.outpatientDoctor.IADTemplate.update,{
      id,
      type:IAD_TYPE,
      display_type,
      template_name,
      non_drug_id_list
    }).ok(data=>{
      Message.success('组套更新成功！')
      this.asyncAllTemplates();
    }).submit();
  }

  /************************************************************* */

  openEditor=(mode,non_drug_items,id,templateDisplayType,templateName)=>{
    this.setState({editorOpen:true})
    this.IADDisplay.disableToolBar();
    this.IADEditTable.open(mode,non_drug_items,id,templateDisplayType,templateName)
  }

  closeEditor=()=>{
    this.setState({editorOpen:false})
    this.IADDisplay.enableToolBar();
    this.IADEditTable.close()
  }

  render() {
    const {currentPatient} = this.props;
    const {currentList,allItems,allTemplates,loading,editorOpen} = this.state;
    const disabled = (currentPatient.registration===null || currentPatient.registration===undefined || loading);
  
    allItems.forEach(x=>x.key=x.id);

    return (
      <div style={{minHeight:'700px'}}>
        <Row>
          <Col span={16}>
            <div style={{marginRight:'20px'}}>

              <Typography.Title level={4}>处置编辑</Typography.Title>
              <IADEditTable 
                  allItems={allItems}
                  createIAD={this.createIAD.bind(this)}
                  updateIAD={this.updateIAD.bind(this)}
                  createTemplate={this.createTemplate.bind(this)}
                  updateTemplate={this.updateTemplate.bind(this)}
                  closeEditor={this.closeEditor.bind(this)}
                  onRef={ref=>this.IADEditTable=ref}/>
              <Divider/>

              <IADDisplay 
                getIADResult={this.getIADResult.bind(this)}
                deleteIAD={this.deleteIAD.bind(this)}
                updateIAD={this.updateIAD.bind(this)}
                cancelIAD={this.cancelIAD.bind(this)}
                sendIAD={this.sendIAD.bind(this)}
                currentList={currentList}
                list = {currentList}
                disabled={disabled}
                onRef={(ref)=>{this.IADDisplay = ref}}
                openEditor={this.openEditor.bind(this)}/>
            </div>
          </Col>
          <Col span={8}>
            <Card>
              <Tabs defaultActiveKey={'1'}>
                <Tabs.TabPane tab="组套管理" key={'1'}>
                  <TemplateSelector
                    deleteTemplate={this.deleteTemplate.bind(this)}
                    openEditor={this.openEditor.bind(this)}
                    getTemplatesDetail={this.getTemplatesDetail.bind(this)}
                    allTemplates={allTemplates}
                    disabled={disabled}
                    loading={loading}
                    editorOpen={editorOpen}
                    applyTemplate={this.applyTemplate.bind(this)}
                  />
                </Tabs.TabPane>
              </Tabs>
            </Card>
          </Col>
        </Row>

      </div>
    )
  }
}

export default DisposalSection;
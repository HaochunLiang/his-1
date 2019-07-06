import React from 'react';
import {Row,Col, Divider} from 'antd'
import PrescriptionDisplay from './PrescriptionDisplay';
import TemplateSelector from './TemplateSelector'
import PrescriptionEdit from './PrescriptionEdit'
import API from '../../../../global/ApiConfig';
import Message from '../../../../global/Message';

const PRESCRIPTION_TYPE = 2;

class PatentMedcinePrescription extends React.Component {
  state={
    allDrugs:[],//全部药 只读取（只有指定分类）
    currentList:[],//当前所有的处方
    allTemplates:{personal:[],department:[],hospital:[]},//所有的模板
    loading:true,
    editorOpen:false
  }
  
  componentDidMount=async()=>{
    await this.loadAllDrugs();
    await this.asyncAllTemplates();
    await this.syncCurrentList();
    this.setState({loading:false})
  }

  applyTemplate=(template)=>{
    this.PrescriptionEdit.apply(template)
  }

  //加载所有药（根基类型）
  loadAllDrugs=async()=>{
    API.request(API.outpatientDoctor.prescription.allDrugs,{type:PRESCRIPTION_TYPE})
    .ok(allDrugs=>{
      this.setState({allDrugs})
    }).submit();
  }

  //同步当前的处方
  syncCurrentList=async()=>{
    const {medical_record_id} = this.props.currentRegistration;
    //const medical_record_id = 1;
    //console.warn('this is a temp debug choice!')
    API.request(API.outpatientDoctor.prescription.allPrescription,{
      type:PRESCRIPTION_TYPE,
      medical_record_id
    }).ok(currentList=>{
      this.setState({currentList})
    }).submit();
  }

  //创建
  createPrescription=(prescription_item_list)=>{
    //{drug_id:9, amount:2 note usage dosage frequency day_count times }
    const {medical_record_id} = this.props.currentRegistration;
    API.request(API.outpatientDoctor.prescription.create,{
      type:PRESCRIPTION_TYPE,
      medical_record_id,
      prescription_item_list
    }).ok(data=>{
      Message.success('创建成功！');
      this.syncCurrentList();
    }).submit();
  }

  //删除
  deletePrescription=(idArr)=>{
    API.request(API.outpatientDoctor.prescription.delete,{
      id:idArr
    }).ok(data=>{
      Message.success('删除成功！');
      this.syncCurrentList();
    }).submit();
  }

   //作废
   cancelPrescription=(idArr)=>{
    API.request(API.outpatientDoctor.prescription.cancel,{
      id:idArr
    }).ok(data=>{
      Message.success('处方已作废！');
      this.syncCurrentList();
    }).submit();
  }

  //更新
  updatePrescription=(id,prescription_item_list)=>{
    API.request(API.outpatientDoctor.prescription.update,{
      type:PRESCRIPTION_TYPE,
      id,
      prescription_item_list
    }).ok(data=>{
      Message.success('更新成功！');
      this.syncCurrentList();
    }).submit();
  }

  //发送
  sendPrescription=(idArr)=>{
    API.request(API.outpatientDoctor.prescription.send,{id:idArr})
    .ok(data=>{
      Message.success('处方已发送！');
      this.syncCurrentList();
    }).submit()
  }

  /********************* 组套 *********************** */
  //获取全部组套
  asyncAllTemplates=async()=>{
    API.request(API.outpatientDoctor.prescriptionTemplate.list,{
      type:PRESCRIPTION_TYPE
    }).ok(allTemplates=>{
      this.setState({allTemplates})
    }).submit()
  }

  //获取组套详情
  getTemplatesDetail=(id,callback)=>{
    API.request(API.outpatientDoctor.prescriptionTemplate.detail,{id})
    .ok(data=>{
      callback(data)
    }).submit();
  }

  //存为组套 创建
  createTemplate=(display_type,template_name,prescription_item_list)=>{
    API.request(API.outpatientDoctor.prescriptionTemplate.create,{
      type:PRESCRIPTION_TYPE,
      display_type,
      template_name,
      prescription_item_list
    }).ok(data=>{
      Message.success('组套创建成功！')
      this.asyncAllTemplates();
    }).submit();
  }

  //删除组套
  deleteTemplate=(idArr)=>{
    API.request(API.outpatientDoctor.prescriptionTemplate.delete,{id:idArr})
    .ok(data=>{
      Message.success('组套删除成功！')
      this.asyncAllTemplates();
    }).submit();
  }

  //更新组套 暂存
  updateTemplate=(id,display_type,template_name,prescription_item_list)=>{
    API.request(API.outpatientDoctor.prescriptionTemplate.update,{
      id,
      type:PRESCRIPTION_TYPE,
      display_type,
      template_name,
      prescription_item_list
    }).ok(data=>{
      Message.success('组套更新成功！')
      this.asyncAllTemplates();
    }).submit();
  }

  /************************************************************* */

  openEditor=(mode,prescription_item_list,id,templateDisplayType,templateName)=>{
    this.setState({editorOpen:true})
    this.PrescriptionDisplay.disableToolBar();
    this.PrescriptionEdit.open(mode,prescription_item_list,id,templateDisplayType,templateName)
  }

  closeEditor=()=>{
    this.setState({editorOpen:false})
    this.PrescriptionDisplay.enableToolBar();
    this.PrescriptionEdit.close()
  }

  render() {
    const {currentRegistration} = this.props;
    const {currentList,allDrugs,allTemplates,loading,editorOpen} = this.state;
    const disabled = (currentRegistration===null || currentRegistration ===undefined || loading);
   
    return (
      <div style={{minHeight:'700px'}}>
         <PrescriptionEdit 
            allDrugs={allDrugs}
            createPrescription={this.createPrescription.bind(this)}
            updatePrescription={this.updatePrescription.bind(this)}
            createTemplate={this.createTemplate.bind(this)}
            updateTemplate={this.updateTemplate.bind(this)}
            closeEditor={this.closeEditor.bind(this)}
            onRef={ref=>this.PrescriptionEdit=ref}/>
        <Divider/>

        <Row>
          <Col span={18}>
            <div style={{marginRight:'20px'}}>
              <PrescriptionDisplay 
                deletePrescription={this.deletePrescription.bind(this)}
                updatePrescription={this.updatePrescription.bind(this)}
                sendPrescription={this.sendPrescription.bind(this)}
                cancelPrescription={this.cancelPrescription.bind(this)}
                currentList={currentList}
                list = {currentList}
                disabled={disabled}
                onRef={(ref)=>{this.PrescriptionDisplay = ref}}
                openEditor={this.openEditor.bind(this)}/>
            </div>
          </Col>
          <Divider type="vertical" style={{marginLeft:'5px',marginRight:'5px'}}/>
          <Col span={6}>
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
          </Col>
        </Row>

      </div>
    )
  }
}

export default PatentMedcinePrescription;
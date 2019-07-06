import React from 'react';
import {Descriptions, Table, Button, Modal, Checkbox} from 'antd'

class HistoryMedicalRecord extends React.Component {

  state = {
    displayModalVisible:false,
    displayRecord:null,
  }

  displayRecord=(record)=>{
    this.setState({displayModalVisible:true,displayRecord:record})
  }

  hideDisplayRecord=()=>{this.setState({displayModalVisible:false})}

  checkAndResolveKey=(diagnose)=>{
    diagnose.chinese_diagnose.forEach(x=>x.key=x.disease_id);
    diagnose.western_diagnose.forEach(x=>x.key=x.disease_id);
    return diagnose;
  }

  render() {
    var {allHistoryMedicalRecord} = this.props;
    const {displayRecord,displayModalVisible} = this.state;
    //容错
    if(allHistoryMedicalRecord===null) allHistoryMedicalRecord = [];
    allHistoryMedicalRecord.forEach(x=>x.key = x.id)
    if(displayRecord) {
      displayRecord.diagnose = this.checkAndResolveKey(displayRecord.diagnose)
    }

    return (
      <div>
        <Table
          columns={[
            {title:"创建时间",dataIndex:"create_time"},
            {title:"病历号",dataIndex:"id"},
            {title:"操作",render:(text,record,index)=>(
              <Button
                size="small"
                type="primary"
                onClick={()=>{this.displayRecord(record)}}>
                  详情
              </Button>)}
          ]}
          dataSource={allHistoryMedicalRecord}
        />

        <Modal 
          closable destroyOnClose
          title="历史病历详情"
          visible={displayModalVisible}
          onCancel={this.hideDisplayRecord.bind(this)}
          width={1000}
          footer={null}
          >
            {displayRecord?(<div>
              <Descriptions bordered>
                <Descriptions.Item label="病历号">{displayRecord.id}</Descriptions.Item>
                <Descriptions.Item label="创建时间">{displayRecord.create_time}</Descriptions.Item>
                <Descriptions.Item label="主诉">{displayRecord.chief_complaint}</Descriptions.Item>
                <Descriptions.Item label="现病史">{displayRecord.current_medical_history}</Descriptions.Item>
                <Descriptions.Item label="现病治疗情况">{displayRecord.current_treatment_situation}</Descriptions.Item>
                <Descriptions.Item label="过敏史">{displayRecord.allergy_history}</Descriptions.Item>
                <Descriptions.Item label="既往史">{displayRecord.past_history}</Descriptions.Item>
                <Descriptions.Item label="体格检查">{displayRecord.physical_examination}</Descriptions.Item>
              </Descriptions>

              <Table
                title={()=>"西医诊断"}
                columns={[
                  {title:"ICD编码",dataIndex:"disease_id"},
                  {title:"疾病名称",dataIndex:"disease_name"},
                  {title:"主诊",dataIndex:"main_symptom",render:(data)=>(<Checkbox checked={data}/>)},
                  {title:"疑似",dataIndex:"suspect",render:(data)=>(<Checkbox checked={data}/>)}
                ]}
                dataSource={displayRecord.diagnose.western_diagnose}
              />

              <Table
                title={()=>"中医诊断"}
                columns={[
                  {title:"疾病编码",dataIndex:"disease_id"},
                  {title:"疾病名称",dataIndex:"disease_name"},
                  {title:"辩证分型",dataIndex:"syndrome_differentiation"},
                ]}
                dataSource={displayRecord.diagnose.chinese_diagnose}
              />
            </div>):null}
        </Modal>
      </div>
    );
  }

}

export default HistoryMedicalRecord;
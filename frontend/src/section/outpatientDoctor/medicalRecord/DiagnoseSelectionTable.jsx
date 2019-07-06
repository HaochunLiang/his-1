import React from 'react';
import {Input,Table,Radio,Checkbox,Select,Button} from 'antd'
import Message from "../../../global/Message";
const {Option} = Select;

class DiagnoseSelectionTable extends React.Component {

  componentDidMount=()=>{this.props.onRef(this)}

  state={
    patientDiagnose:{
      western_diagnose:[],
      chinese_diagnose:[]
    }
  }

  chineseDiagnoseColumns = [
    {
      title: '诊断编码',
      dataIndex: 'disease_code',
      key: 'disease_code',
    },{
      title: '诊断名称',
      dataIndex: 'disease_name',
      key: 'disease_name',
    },{
      title: '辩证分型',
      key: 'syndrome_differentiation',
      dataIndex:'syndrome_differentiation',
      render:(syndrome_differentiation,record,index)=>{
        return(
        <Input
          value={syndrome_differentiation}
          onChange={(e)=>{
            const {patientDiagnose} = this.state;
            const input = e.target.value;
            var editedRecord = record;
            editedRecord.syndrome_differentiation = input;
            var newData = patientDiagnose;
            newData.chinese_diagnose[index] = editedRecord;
            this.setPatientDiagnose(newData)
          }}
        />)
      }
    },{
      title: '操作',
      key:'operate',
      render:(text,record,index)=>(<span>
        <Button type="link" onClick={()=>{
            const {patientDiagnose} = this.state;
            const key = record.key;
            var newData = patientDiagnose;
            newData.chinese_diagnose =  newData.chinese_diagnose.filter(x=>x.key!==key);
            this.setPatientDiagnose(newData)
          }}>删除</Button>
        </span>)
    }
  ];

  westernDiagnoseColumns = [
    {
      title: 'ICD编码',
      dataIndex: 'disease_code',
      key: 'disease_code',
    },{
      title: '名称',
      dataIndex: 'disease_name',
      key: 'disease_name',
    },{
      title: '主诊',
      key: 'main_symptom',
      dataIndex:'main_symptom',
      render:(main_symptom,record,index)=>{
        return(
        <Radio 
          checked={main_symptom}
          onClick={()=>{
            const {patientDiagnose} = this.state;
            var newValue = !main_symptom;
            var newData = patientDiagnose;
            newData.western_diagnose.forEach(ele=>ele.main_symptom=false)
            newData.western_diagnose[index].main_symptom = newValue;
            this.setPatientDiagnose(newData)
          }}
        />)
      }
    },{
      title: '疑似',
      dataIndex: 'suspect',
      key: 'suspect',
      render:(suspect,record,index)=>{
        return(
        <Checkbox 
          checked={suspect}
          onClick={()=>{
            const {patientDiagnose} = this.state;
            var editedRecord = record;
            editedRecord.suspect = !suspect;
            var newData = patientDiagnose;
            newData.western_diagnose[index] = editedRecord;
            this.setPatientDiagnose(newData)
          }}
          />)
      }
    },{
      title: '操作',
      key:'operate',
      render:(text,record,index)=>(<span>
        <Button type="link" onClick={()=>{
            const key = record.key;
            const {patientDiagnose} = this.state;
            var newData = patientDiagnose;
            newData.western_diagnose = newData.western_diagnose.filter(x=>x.key!==key);
            this.setPatientDiagnose(newData)
          }}>删除</Button>
        </span>)
    }
  ];
  
  checkAndResolveKey=(diagnose)=>{
    diagnose.chinese_diagnose.forEach(x=>x.key=x.disease_id);
    diagnose.western_diagnose.forEach(x=>x.key=x.disease_id);
    return diagnose;
  }

  applyDiagnose=(patientDiagnose)=>{
    this.setState({patientDiagnose:this.checkAndResolveKey(patientDiagnose)})
  }

  setPatientDiagnose=(patientDiagnose)=>{
    this.setState({patientDiagnose:this.checkAndResolveKey(patientDiagnose)})
  }

  clear=()=>{
    this.setState({
      patientDiagnose:{
        western_diagnose:[],
        chinese_diagnose:[]
      }
    });
  }

  patientDiagnoseData=()=>{return this.state.patientDiagnose}

  addNewWesternDiagnoseRow=(disease_id)=>{
    const {patientDiagnose} = this.state;
    const {western_diagnose} = patientDiagnose;
    const {westernDiagnoseDiseases} = this.props.diagnoses;
    if(western_diagnose.filter(x=>x.disease_id===disease_id).length>0) {
      Message.error('该诊断已经存在！')
      return;
    }
    const disease = westernDiagnoseDiseases.filter(x=>x.id===disease_id)[0];
    var newRow = {
      disease_id:disease_id,
      key:disease_id,
      disease_name:disease.name,
      disease_code:disease.code,
      main_symptom:false,
      suspect:false
    }
    if(western_diagnose.length===0) newRow.main_symptom = true;
    var newData = patientDiagnose;
    newData.western_diagnose.push(newRow);
    this.setPatientDiagnose(newData);
  }

  addNewChineseDiagnoseRow=(disease_id)=>{
    const {patientDiagnose} = this.state;
    const {chinese_diagnose} = patientDiagnose;
    const {chineseDiagnoseDiseases} = this.props.diagnoses;
    if(chinese_diagnose.filter(x=>x.disease_id===disease_id).length>0) {
      Message.error('该诊断已经存在！')
      return;
    }
    const disease = chineseDiagnoseDiseases.filter(x=>x.id===disease_id)[0];
    var newRow = {
      disease_id:disease_id,
      key:disease_id,
      disease_name:disease.name,
      disease_code:disease.code,
      syndrome_differentiation:''
    }
    var newData = patientDiagnose;
    newData.chinese_diagnose.push(newRow);
    this.setPatientDiagnose(newData);
  }

  render() {
    const {patientDiagnose} = this.state;
    const {western_diagnose,chinese_diagnose} = patientDiagnose;
    var {westernDiagnoseDiseases,chineseDiagnoseDiseases} = this.props.diagnoses;
    westernDiagnoseDiseases = westernDiagnoseDiseases.slice(0,100)
    chineseDiagnoseDiseases = chineseDiagnoseDiseases.slice(0,100)
    console.warn('temporary select 100')
    
    return (
      <div>
         <Table
            title={()=>
            (<div>
              <span style={{marginRight:'10px'}}>西医诊断</span>
              <Select style={{width:'200px'}} 
                showSearch
                placeholder="选择一个西医诊断"
                optionFilterProp="children"
                onChange={(id)=>{
                  this.addNewWesternDiagnoseRow(id)
                }}
                filterOption={(input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
              }>
              {westernDiagnoseDiseases.map((x)=>
                (<Option value={x.id} key={x.id}>{x.name}</Option>)
                )}
              </Select>
            </div>)}
            columns={this.westernDiagnoseColumns}
            dataSource={western_diagnose}
            pagination={false}
        />

        <Table
           title={()=>
            (<div>
              <span style={{marginRight:'10px'}}>中医诊断</span>
              <Select style={{width:'200px'}} 
                showSearch
                placeholder="选择一个中医诊断"
                optionFilterProp="children"
                onChange={(id)=>{
                  this.addNewChineseDiagnoseRow(id)
                }}
                filterOption={(input, option) =>
                option.props.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
              }>
              {chineseDiagnoseDiseases.map((x)=>
                (<Option value={x.id} key={x.id}>{x.name}</Option>)
                )}
              </Select>
            </div>)}
          columns={this.chineseDiagnoseColumns}
          dataSource={chinese_diagnose}
          pagination={false}
        />
      </div>
    )
  }
}

export default DiagnoseSelectionTable;
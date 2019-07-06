import React from 'react';
import {Card,Badge,Table, Button, Icon,Input, Spin,Descriptions, Divider} from 'antd';
import Message from '../../global/Message';
import Highlighter from 'react-highlight-words';


class SiderPatientSelector extends React.Component {

  /*该模块管理当前的看诊病人的病历信息 */

  state = {
    searchText:''
  }

  /**
   * 
   * currentPatient{
   *  registration:
   *  medicalRecord:
   * }
   */

  getColumnSearchProps = dataIndex => ({
    filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
      <div style={{ padding: 8 }}>
        <Input
          ref={node => {
            this.searchInput = node;
          }}
          placeholder={`Search ${dataIndex}`}
          value={selectedKeys[0]}
          onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
          onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
          style={{ width: 188, marginBottom: 8, display: 'block' }}
        />
        <Button
          type="primary"
          onClick={() => this.handleSearch(selectedKeys, confirm)}
          icon="search"
          size="small"
          style={{ width: 90, marginRight: 8 }}
        >
          搜索
        </Button>
        <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
          重置
        </Button>
      </div>
    ),
    filterIcon: filtered => (
      <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />
    ),
    onFilter: (value, record) =>
      record[dataIndex]
        .toString()
        .toLowerCase()
        .includes(value.toLowerCase()),
    onFilterDropdownVisibleChange: visible => {
      if (visible) {
        setTimeout(() => this.searchInput.select());
      }
    },
    render: text => (
      <Highlighter
        highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
        searchWords={[this.state.searchText]}
        autoEscape
        textToHighlight={text.toString()}
      />
    ),
  });


  handleSearch = (selectedKeys, confirm) => {
    confirm();
    this.setState({ searchText: selectedKeys[0] });
  };

  handleReset = clearFilters => {
    clearFilters();
    this.setState({ searchText: '' });
  };

  columns = [{
    title: '病历号',
    dataIndex: 'medical_record_id',
    ...this.getColumnSearchProps('medical_record_id'),
  },{
    title: '患者姓名',
    ...this.getColumnSearchProps('patient_name'),
    render:(registraion)=>
      (<span style={{cursor:'pointer'}}>
        {this.isRegistrationCurrent(registraion)?
        <Badge color="#f50" text={registraion.patient_name} />:
        <span>{registraion.patient_name}</span>}
      </span>)
  }]

  isRegistrationCurrent(registraion) {
    if(this.props.currentPatient.registration===undefined) return false;
    const curRegistration = this.props.currentPatient.registration;
    return curRegistration.medical_record_id === registraion.medical_record_id;
  }

  render() {
    //变量
    const {patientList,currentPatient,loading} = this.props;
    //方法
    const {selectPatient} = this.props;
    const {waiting,pending} = patientList;

    return (
      <Card style={{minHeight:'870px'}}>
        <div>
          {loading?<Spin style={{float:'right'}}/>:null}
          {currentPatient.registration?
            <Descriptions title="当前患者信息" column={1} size="small">
              <Descriptions.Item label="姓名">{currentPatient.registration.patient_name}</Descriptions.Item>
              <Descriptions.Item label="性别">{currentPatient.registration.gender==='male'?'男':'女'}</Descriptions.Item>
              <Descriptions.Item label="年龄">{currentPatient.registration.age}</Descriptions.Item>
              <Descriptions.Item label="病历号">{currentPatient.registration.medical_record_id}</Descriptions.Item>
              <Descriptions.Item label="病历状态">{currentPatient.medicalRecord.status}</Descriptions.Item>
            </Descriptions>
            :"请选择患者"}
        </div>
        
        <Divider/>
        
        <Table
          onRow={registraion=>{
            return {
              onDoubleClick:event=>{
                Message.showConfirm(
                '确认',
                `你确定要看诊${registraion.patient_name}吗？未保存的信息会丢失。`,
                ()=>{selectPatient(registraion)},
                ()=>{})
              }
            }
          }}
          title={()=>`待诊（共${waiting.length}名患者)`}
          columns={this.columns} dataSource={waiting} 
          pagination={{pageSize:6}} size="small"/>

        <Table 
          onRow={registraion=>{
            return {
              onDoubleClick:event=>{
                Message.showConfirm(
                '确认',
                `你确定要看诊${registraion.patient_name}吗？未保存的信息会丢失。`,
                ()=>{selectPatient(registraion)},
                ()=>{})
              }
            }
          }}
          title={()=>`诊断中（共${pending.length}名患者)`}
          columns={this.columns} dataSource={pending} 
          pagination={{pageSize:6}} 
          size="small" />
      </Card>
    )
  }
}

export default SiderPatientSelector;
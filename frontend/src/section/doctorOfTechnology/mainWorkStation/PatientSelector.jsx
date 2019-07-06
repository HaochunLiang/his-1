import React from 'react';
import {Collapse,Table, Button, Icon,Input, Select,Typography, Form, Spin, Drawer,Modal,Divider} from 'antd';
import Highlighter from 'react-highlight-words';
import Message from '../../../global/Message';

const {Option} = Select;
const Panel = Collapse.Panel;

class PatientSelector extends React.Component {

  state = {
    searchText:'',
    currentRegistration:null,
    registrationInfoList:[],
    projectGroupList:[],
    loading:false,
    childrenVisble:false,
    visible:false,

    resultModalVisible:false,
    resultData:null
  }

  componentDidMount=()=>{this.props.onRef(this)}
  

  open=()=>{this.setState({visible:true,childrenVisble:false})}
  close=()=>{this.setState({visible:false,childrenVisble:false})}
  closeChildrenDrawer=()=>{this.setState({childrenVisble:false})}
  openChildrenDrawer=()=>{this.setState({childrenVisble:true})}


  openResultView=(result,project)=>{
    result.project = project;
    this.setState({resultModalVisible:true,resultData:result})
  }
  closeResultView=()=>this.setState({resultModalVisible:false})


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

  isRegistrationCurrent(registraion) {
    const {currentRegistrationInfo} = this.props;
    if(currentRegistrationInfo===undefined) return false;
    return currentRegistrationInfo.medical_record_id === registraion.medical_record_id;
  }

  columns = [{
    title: '病历号',
    dataIndex: 'medical_record_id',
    ...this.getColumnSearchProps('medical_record_id'),
  },{
    title: '姓名',
    ...this.getColumnSearchProps('patient_name'),
    dataIndex:'patient_name'
  },{
    title: '年龄',
    dataIndex:'age',
    ...this.getColumnSearchProps('age'),
  },{
    title:'操作',
    render:(text,record,index)=>(
      <Button
        type="primary"
        icon="right-circle"
        onClick={()=>{
          //this.props.selectCurrentRegistrationInfo(registraionInfo)
          this.props.searchExcuteProject(record.medical_record_id,(list)=>{
            this.setState({
              childrenVisble:true,
              currentRegistration:record,
              projectGroupList:list
            })
          })
        }}
      >详情</Button>)
    }
  ]

  syncProjectList=()=>{
    const {currentRegistration} = this.state;
    this.props.searchExcuteProject(currentRegistration.medical_record_id,(list)=>{
      this.setState({
        projectGroupList:list
      })
    })
  }

  handleSubmit = e => {
    e.preventDefault();
    this.props.form.validateFields(async (err, values) => {
      if (!err) {
        await this.setState({loading:true})
        this.props.searchRegistration(values,(registrationInfoList)=>{
          //console.log(registrationInfoList)
          registrationInfoList.forEach(x=>x.key = x.medical_record_id)
          this.setState({
            registrationInfoList,
            childrenVisble:false,
            loading:false
          })
        })
      }
    });
  };

  //点击table中的按钮 具体操作
  operate=(type,record)=>{
    //登记 录入 查看
    switch (type) {
      case "登记":{
        this.props.register(record.id,(succ)=>{
          if(succ) {
            Message.success('登记成功！')
            this.syncProjectList();
          }
        })
        break;
      }
      case "录入":{
        const {currentRegistration} = this.state;
        this.props.startLog(record,currentRegistration)
        this.close();
        break;
      }
      case "查看":{
        console.warn(record)
        this.props.getIADResult(record.id,(resultData)=>{
          this.openResultView(resultData,record)
        })
        break;
      }
      default:{
        console.error('unknown error')
      }
    }
  }

  render() {
    //变量
    const {currentRegistrationInfo} = this.props;
    const {registrationInfoList,loading,visible,childrenVisble,projectGroupList} = this.state;
    const { getFieldDecorator } = this.props.form;
    const {resultData,resultModalVisible} = this.state;

    projectGroupList.forEach(projectGroup=>{
      projectGroup.items.forEach(x=>x.key = x.id)
    });

    return (
      <Drawer 
        destroyOnClose
        placement="right" 
        width={500} 
        visible={visible}
        closable 
        onClose={this.close.bind(this)}>
        <div>
          <p style={{float:'left'}}>当前患者：<b>{currentRegistrationInfo?currentRegistrationInfo.patient_name:"未选择"}</b></p>
        </div>

        <Form onSubmit={this.handleSubmit.bind(this)}>
          <Form.Item>
            {getFieldDecorator('input', {
              rules: [{ required: true, message: '输入医疗证号' }],
            })(
              <Input
                addonBefore={
                  getFieldDecorator('type',{
                    initialValue:3
                  })(
                    <Select style={{ width: 90 }}>
                      <Option value={0}>姓名</Option>
                      <Option value={1}>身份证</Option>
                      <Option value={2}>医保卡</Option>
                      <Option value={3}>病历号</Option>
                    </Select>
                  )
                }
                placeholder="医疗证号"
              />
            )}
          </Form.Item>
        </Form>

        {loading?<Spin style={{textAlign:'center'}}/>:
        <Table
          columns={this.columns} 
          dataSource={registrationInfoList} 
          />}

         <Drawer
            destroyOnClose
            title="项目列表详情"
            width={400}
            closable={false}
            onClose={this.closeChildrenDrawer}
            visible={childrenVisble}
          >
            {projectGroupList.length?
              <Typography.Title level={4}>
                共查询到{projectGroupList.length}组项目
              </Typography.Title>:null}

              <Collapse bordered={false}>
              {projectGroupList.map(
                projectGroup=>
                <Panel header={
                  (<span>
                    <span style={{marginRight:'5px'}}>编号: {projectGroup.id}</span>
                    <span>创建时间: {projectGroup.create_time}</span>
                  </span>)
                } key={projectGroup.id}>
                  <Table
                    size="small"
                    columns={[
                      {title:"名称",dataIndex:'non_drug_item.name'},
                      {title:"编号",dataIndex:'non_drug_item.code'},
                      {title:"状态",dataIndex:'status'},
                      {title:"操作",render:(text,record,index)=>{
                        const status = record.status;
                        //#未登记, 已登记, 已完成
                        var buttonText = "";
                        var buttonType = "primary";
                        if(status==="未登记") {
                          buttonText = "登记";
                          buttonType = "primary"
                        } else if(status==="已登记") {
                          buttonText = "录入"; 
                          buttonType = "default"
                        } else if(status==="已完成") {
                          buttonText = "查看"; 
                          buttonType = "dashed"
                        }
                        return (
                          <Button size="small" type={buttonType} onClick={()=>this.operate(buttonText,record)}>{buttonText}</Button>
                        )
                      }
                    }
                  ]}
                    dataSource={projectGroup.items}
                  />
                </Panel>)}
              </Collapse>

              <Modal
                width={900} footer={null}
                destroyOnClose closable visible={resultModalVisible}
                onCancel={this.closeResultView.bind(this)}
              >
                {resultData?
                <div>
                  <div style={{textAlign:"center"}}>
                    <Typography.Title level={3}>{resultData.project.non_drug_item.name}检查报告</Typography.Title>
                    <Divider/>
                    <span style={{marginRight:'20px'}}>操作员工号：<b>{resultData.user_id}</b></span>
                  </div>

                  <Typography.Title level={4}>检查结果：</Typography.Title>
                  <Typography.Paragraph>{resultData.result}</Typography.Paragraph>
                  {resultData.images.map(image=>(
                    <img src={image.url} alt={image.name} key={image.url} style={{width:'50%',padding:'5px'}}></img>
                  ))}

                  <Typography.Title level={4}>建议：</Typography.Title>
                  <Typography.Paragraph>{resultData.advice}</Typography.Paragraph>
                </div>:null}
              </Modal>

          </Drawer>
      </Drawer>
    )
  }
}

export default Form.create({name:'search'})(PatientSelector);
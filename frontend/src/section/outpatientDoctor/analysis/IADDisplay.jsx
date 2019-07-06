import React from 'react';
import { Collapse, Table, Button,Typography,Modal,Divider} from 'antd';

const Panel = Collapse.Panel;

class IADDisplay extends React.Component {

  state={
    toolBarDisable:false,
    resultModalVisible:false,
    resultData:null
  }

  componentDidMount=()=>{this.props.onRef(this)}
  disableToolBar=()=>this.setState({toolBarDisable:true})
  enableToolBar=()=>this.setState({toolBarDisable:false})

  openResultView=(result,project)=>{
    result.project = project;
    this.setState({resultModalVisible:true,resultData:result})
  }
  closeResultView=()=>this.setState({resultModalVisible:false})

  //查看结果
  viewResult=(data)=>{
    this.props.getIADResult(data.id,(result)=>{
      this.openResultView(result,data)
    })
  }

  //计算费用
  calCost=(exam_item)=>{
    var cost = 0;
    for(var i=0;i<exam_item.length;i++){
      cost += exam_item[i].non_drug_item.fee;
    }
    return cost;
  }

  render() {
    const {openEditor,list,disabled} = this.props;
    const {toolBarDisable,resultModalVisible,resultData} = this.state;

    list.forEach(data=>{
      data.key = data.id;
      data.sum = this.calCost(data.exam_item);//data.exam_item.map(x=>x.non_drug_item.fee).reduce((prev, curr, idx, arr)=>prev + curr);
      data.exam_item.forEach(x=>x.key=x.id)
    })
    return(
      <div>
        <Typography.Title level={4}>
          当前共开具 <b>{list.length}</b> 组检验
          <Button 
            style={{float:'right',marginRight:20}}
            type="primary" 
            icon="plus"
            disabled={disabled || toolBarDisable}
            onClick={()=>{this.props.openEditor("new",[])}}
            >新建</Button>
        </Typography.Title>

        <Collapse accordion bordered={false}>
          {list.map(data=>(
            <Panel header={
              <span>
                <span style={{marginRight:'10px'}}>检验编号：{data.id}</span>
                <span style={{marginRight:'10px'}}>状态：{data.status}</span>
              </span>
            } key={data.id}>
              <Table
                size="small"
                title={()=>(
                  <span>
                    <span style={{marginRight:'30px'}}>创建时间：{data.create_time}</span>
                    <span style={{marginRight:'30px'}}>总费用：{data.sum}</span>              
                    <span style={{marginRight:'30px'}}>
                    {data.status==="暂存"?
                      <Button 
                        size="small" 
                        disabled={toolBarDisable}
                        style={{marginRight:'10px'}}
                        onClick={()=>openEditor("update",data.exam_item.map(x=>x.non_drug_item),data.id)} 
                        type="primary">
                        编辑
                      </Button>:null}
                      {data.status==="暂存"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.deleteIAD([data.id])} 
                        type="danger">
                        删除
                      </Button>:null}
                      {data.status==="暂存"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.sendIAD([data.id])} 
                        type="primary">
                        发送
                      </Button>:null}
                      {data.status==="已提交"?
                      <Button 
                        hidden
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.cancelIAD([data.id])} 
                        type="danger">
                        作废
                      </Button>:null}
                    </span>
                  </span>
                )}
                dataSource={data.exam_item}
                columns={[
                  {title:"项目名称",dataIndex:"non_drug_item.name"},
                  {title:"项目编码",dataIndex:"non_drug_item.code"},
                  {title:"拼音",dataIndex:"non_drug_item.pinyin"},
                  {title:"规格",dataIndex:"non_drug_item.format"},
                  {title:"费用",dataIndex:"non_drug_item.fee"},
                  {title:"状态",dataIndex:"status",render:(text,record,index)=>(
                    text==='已完成'?<Button type="primary" size="small" onClick={()=>this.viewResult(record)}>查看结果</Button>:<span>{text}</span>
                  )},
                ]}
              />
            </Panel>
          ))}
        </Collapse>

        <Modal
          width={900} footer={null}
          destroyOnClose closable visible={resultModalVisible}
          onCancel={this.closeResultView.bind(this)}
        >
          {resultData?
          <div>
             <div style={{textAlign:"center"}}>
              <Typography.Title level={3}>{resultData.project.non_drug_item.name}检验报告</Typography.Title>
              <Divider/>
              <span style={{marginRight:'20px'}}>操作员工号：<b>{resultData.user_id}</b></span>
            </div>

            <Typography.Title level={4}>检验结果：</Typography.Title>
            <Typography.Paragraph>{resultData.result}</Typography.Paragraph>
            {resultData.images.map(image=>(
              <img src={image.url} alt={image.name} key={image.url} style={{width:'50%',padding:'5px'}}></img>
            ))}

            <Typography.Title level={4}>建议：</Typography.Title>
            <Typography.Paragraph>{resultData.advice}</Typography.Paragraph>
          </div>:null}
        </Modal>
      </div>
    )
  }

}

export default IADDisplay;
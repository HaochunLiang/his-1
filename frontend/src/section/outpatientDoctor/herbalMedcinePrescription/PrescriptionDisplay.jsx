import React from 'react';
import { Collapse, Table, Button,Typography,Empty} from 'antd';

const Panel = Collapse.Panel;

class PrescriptionDisplay extends React.Component {

  state={
    toolBarDisable:false
  }

  componentDidMount=()=>{this.props.onRef(this)}
  disableToolBar=()=>this.setState({toolBarDisable:true})
  enableToolBar=()=>this.setState({toolBarDisable:false})

  render() {
    const {openEditor,list,disabled} = this.props;
    const {toolBarDisable} = this.state;

    list.forEach(prescription=>{
      prescription.key = prescription.id;
      prescription.prescription_item_list.forEach((x)=>{
        x.key=x.drug.id;
        x.name=x.drug.name;
        x.code = x.drug.code;
      })
      prescription.sum = prescription.prescription_item_list
        .map(x=>x.amount*x.drug.price)
        .reduce((prev, curr, idx, arr)=>prev + curr);
      prescription.prescription_item_list.forEach(x=>x.key=x.drug.id)
    })
  
    return(
      <div>
        <Typography.Title level={4}>
          当前共开具 <b>{list.length}</b> 组草药处方
          <Button 
            style={{float:'right',marginRight:20}}
            type="primary" 
            icon="plus"
            disabled={disabled || toolBarDisable}
            onClick={()=>{this.props.openEditor("new",[])}}
            >新建</Button>
        </Typography.Title>

        {list.length?null:<Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />}

        <Collapse accordion bordered={false}>
          {list.map(prescription=>(
            <Panel header={
              <span>
                <span style={{marginRight:'10px'}}>处方编号：{prescription.id}</span>
                <span style={{marginRight:'10px'}}>状态：{prescription.status}</span>
              </span>
            } key={prescription.id}>
              <Table
                title={()=>(
                  <span>
                    <span style={{marginRight:'30px'}}>创建时间：{prescription.create_time}</span>
                    <span style={{marginRight:'30px'}}>总费用：{prescription.sum}</span>              
                    <span style={{marginRight:'30px'}}>
                    {prescription.status==="暂存"?
                      <Button 
                        size="small" 
                        disabled={toolBarDisable}
                        style={{marginRight:'10px'}}
                        onClick={()=>openEditor("update",prescription.prescription_item_list,prescription.id)} 
                        type="primary">
                        编辑
                      </Button>:null}
                      {prescription.status==="暂存"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.deletePrescription([prescription.id])} 
                        type="danger">
                        删除
                      </Button>:null}
                      {prescription.status==="暂存"?
                      <Button 
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.sendPrescription([prescription.id])} 
                        type="primary">
                        发送
                      </Button>:null}
                      {prescription.status==="已提交"?
                      <Button 
                        hidden
                        style={{marginRight:'10px'}}
                        size="small" 
                        disabled={toolBarDisable}
                        onClick={()=>this.props.cancelPrescription([prescription.id])} 
                        type="danger">
                        作废
                      </Button>:null}
                    </span>
                  </span>
                )}
                size="small"
                pagination={false}
                bordered={false}
                dataSource={prescription.prescription_item_list}
                columns={[
                  {title:"编号",dataIndex:"drug.code"},
                  {title:"名称",dataIndex:"drug.name"},
                  {title:"用法",dataIndex:"usage"},
                  {title:"频次",dataIndex:"frequency"},
                  {title:"数量",dataIndex:"amount"},
                  {title:"天数",dataIndex:"day_count"},
                  {title:"次数",dataIndex:"times"},
                  {title:"状态",dataIndex:"status"},
                  {title:"医嘱",dataIndex:"note"},
                ]}
              />
            </Panel>
          ))}
        </Collapse>
      </div>
    )
  }

}

export default PrescriptionDisplay;
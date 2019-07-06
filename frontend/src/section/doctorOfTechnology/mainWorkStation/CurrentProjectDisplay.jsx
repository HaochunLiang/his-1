import React from 'react';
import {Descriptions, Divider,Empty} from 'antd';

class CurrentProjectDisplay extends React.Component {

  render() {
    const {registration,project } = this.props;
    return(
    <div>
    {registration?(<div>
      <Descriptions column={1}  title="患者信息">
        <Descriptions.Item label="患者姓名">{registration.patient_name}</Descriptions.Item>
        <Descriptions.Item label="年龄">{registration.age}</Descriptions.Item>
        <Descriptions.Item label="性别">{registration.gender==="male"?"男":"女"}</Descriptions.Item>
        <Descriptions.Item label="病历号">{registration.medical_record_id}</Descriptions.Item>
      </Descriptions>

      <Divider/>

      <Descriptions column={1}  title="项目信息">
        <Descriptions.Item label="项目名称">{project.non_drug_item.name}</Descriptions.Item>
        <Descriptions.Item label="拼音">{project.non_drug_item.pinyin}</Descriptions.Item>
        <Descriptions.Item label="规格">{project.non_drug_item.format}</Descriptions.Item>
        <Descriptions.Item label="项目编码">{project.non_drug_item.code}</Descriptions.Item>
        <Descriptions.Item label="执行编号">{project.id}</Descriptions.Item>
        <Descriptions.Item label="费用">{project.non_drug_item.fee}</Descriptions.Item>
      </Descriptions>
    </div>):<Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />}
    </div>
    )
  }

}

export default CurrentProjectDisplay;
import React from 'react';
import {Upload,message} from 'antd';
import { Typography, Button} from 'antd';


class BatchImportUpload extends React.Component {

  state = {
    msg:''
  }

  uploadProps = {
    name: 'file',
    action: 'https://www.mocky.io/v2/5cc8019d300000980a055e76',
    withCredentials:true
  };

  onChange(info) {
    if (info.file.status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === 'done') {
      message.success(`${info.file.name} 上传成功`);
      //如果后台解析正确
      this.setState({msg:'导入成功'});
      //this.props.reloadData();
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} 上传失败.`);
      this.state({msg:'导入失败'})
    }
  }

  render() {
    return(<div>
       <Upload {...this.uploadProps} onChange={this.onChange.bind(this)}>
          <Typography.Paragraph>
            选择xls,xlsx文件上传，导入科室信息
          </Typography.Paragraph>
          <Button type="dashed" icon="upload">上传文件</Button>
        </Upload>
        <Typography.Paragraph style={{color:'red'}}>
            {this.state.msg}
        </Typography.Paragraph>
    </div>)
  }
}

export default BatchImportUpload;
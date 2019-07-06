import React from 'react';
import {Upload,message} from 'antd';
import { Typography, Button} from 'antd';
import API from '../../../global/ApiConfig';
import Status from '../../../global/Status';

class BatchImportUpload extends React.Component {

  state = {
    msg:''
  }

  uploadProps = {
    name: 'file',
    action: API.bacisInfoManagement.diagnoseDirectory.import.url,
    withCredentials:true
  };

  onChange(info) {
    console.log(info)
    if (info.file.status !== 'uploading') {
      console.log(info.file, info.fileList);
    }
    if (info.file.status === 'done') {
      message.success(`${info.file.name} 上传成功`);
      //如果后台解析正确
      if(info.file.response.code===Status.Ok) {
        this.setState({msg:'导入成功'});
        this.props.handleUploadSuccess();
      } else {
        this.setState({msg:'导入失败'});
      }
      //this.props.reloadData();
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} 上传失败.`);
      this.setState({msg:'导入失败'})
    }
  }


  render() {
    return(<div>
       <Upload {...this.uploadProps} onChange={this.onChange.bind(this)}>
          <Typography.Paragraph>
            选择 *xlsx 文件上传，导入诊断目录信息
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
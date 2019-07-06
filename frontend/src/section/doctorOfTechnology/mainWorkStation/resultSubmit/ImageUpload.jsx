import React from 'react';
import {Upload,Icon,Modal} from 'antd';
import API from '../../../../global/ApiConfig';

class ImageUpload extends React.Component{

  componentDidMount=()=>{this.props.onRef(this)}

  state = {
    previewVisible: false,
    previewImage: '',
    fileList: [],
  };

  getBase64=(file)=> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  handleCancel = () => this.setState({ previewVisible: false });

  handlePreview = async file => {
    if (!file.url && !file.preview) {
      file.preview = await this.getBase64(file.originFileObj);
    }
    this.setState({
      previewImage: file.url || file.preview,
      previewVisible: true,
    });
  };

  handleChange = ({ fileList }) => this.setState({ fileList });

  //获取当前数据
  getData=()=>{
    return this.state.fileList.map(x=>x.response);
  }

  render() {
    const { previewVisible, previewImage, fileList } = this.state;
    const {disabled} = this.props;
    const uploadButton = (
      <div>
        <Icon type="plus" />
        <div className="ant-upload-text">上传</div>
      </div>
    );
    return (
      <div className="clearfix">
        <Upload
          action={API.fileUploadServer+'/upload'}
          listType="picture-card"
          fileList={fileList}
          onPreview={this.handlePreview}
          onChange={this.handleChange}
          disabled={disabled}
          withCredentials={true}
        >
          {fileList.length >= 5 ? null : uploadButton}
        </Upload>
        <Modal visible={previewVisible} footer={null} onCancel={this.handleCancel}>
          <img alt="example" style={{ width: '100%' }} src={previewImage} />
        </Modal>
      </div>
    );
  }
}
 
export default ImageUpload;
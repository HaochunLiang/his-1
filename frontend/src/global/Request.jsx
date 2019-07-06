import Status from './Status';
import Message from './Message';
import axios from 'axios';

class Request {
 
  constructor(api,reqData) {
    this.api = api;
    this.reqData = reqData;
    console.log(this.api.method+' to '+this.api.url,this.reqData);
  }

  //如果是mocky的api就警告
  checkMocky=()=>{
    const url = this.api.url;
    const mocky = this.api.mocky;
    if(mocky!==undefined && mocky!==null)
    {
      console.warn(`mocky [${this.api.url}] => [${this.api.mocky}] `)
      return mocky;
    } else 
      return url;
  }

  onOk = (data)=>{}

  onPermissionDenied = (msg)=>Message.showAuthExpiredMessage();

  onInternalError = (msg)=>Message.showConfirm('错误',msg)

  onNetworkError = (err)=>{
    Message.showNetworkErrorMessage();
    console.log('network/render Err',err,`api:${this.api.url}`)
  }; 

  //设置方法
  ok = (ok)=>{this.onOk=ok;return this;}

  permissionDenied = (permissionDenied)=>{this.onPermissionDenied = permissionDenied;return this;}

  internalError = (internalError)=>{this.onInternalError = internalError;return this;}

  networkError = (networkError)=>{this.onNetworkError = networkError;return this;}

  submit=()=>{
    const _this = this;
    const url = this.checkMocky();
    axios({
      method: _this.api.method,
      url: url,
      data: _this.reqData,
      crossDomain: true,
      withCredentials:true
    }).then((res)=>{
      //状态码
      const code = res.data.code;
      //数据
      const data = res.data.data;
      //消息
      const msg = res.data.msg;
      console.log('res data from '+_this.api.url,res.data);
      if(code===Status.Ok) {
        _this.onOk(data);
      } else if(code===Status.PermissionDenied) {
        _this.onPermissionDenied(msg)
      //服务器错误
      } else if(code===Status.InterorError) {
        _this.onInternalError(msg)
      }
    }).catch((err)=>{
      //网络错误
      _this.onNetworkError(err)
    });  
  }

}

export default Request;
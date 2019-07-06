//import React from 'react';
import {Modal,notification,message} from 'antd';

const confirm = Modal.confirm; 

export default {
  //确认对话框
  showConfirm:(title,content,onOk,onCancel)=> {
    confirm({
      title: title,
      content: content,
      onOk:onOk,
      onCancel:onCancel,
    });
  },
  //登录过期错误对话框
  showAuthExpiredMessage:()=> {
    confirm({
      title: '退出',
      content: '您的登录已过期，请重新登录',
      onOk() {
        return new Promise((resolve, reject) => {
          setTimeout(reject, 100);
        }).catch(() => {window.location.href="/login"});
      },
      onCancel() {
        return new Promise((resolve, reject) => {
          setTimeout(reject, 100);
        }).catch(() => {window.location.href="/login"});
      },
    });
  },
  //网络错误错误对话框
  showNetworkErrorMessage:()=> {
    confirm({
      title: '错误',
      content: '无法连接服务器，请检查你的网络配置',
      onOk() {},
      onCancel() {},
    });
  },
  //显示通知
  openNotification: (message,description) => {
    notification.open({
      message: message,
      description:description,
      duration:1,
      onClick: () => {
        console.log('Notification Clicked!');
      },
    });
  },
  //成功提示
  success:(msg) =>{
    message.success(msg)
  },
  //失败提示
  error:(msg)=>{
    message.error(msg)
  }
}
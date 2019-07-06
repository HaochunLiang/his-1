const proxy = require('http-proxy-middleware');

var restream = (proxyReq, req, res, options)=>{
  //除非是文件上传 其余都要重新设置json
  if (!req.url.endsWith('import') && !req.url.endsWith('upload') && req.body) {
      let bodyData = JSON.stringify(req.body);
      // incase if content-type is application/x-www-form-urlencoded -> we need to change to application/json
      proxyReq.setHeader('Content-Type','application/json');
      proxyReq.setHeader('Content-Length', Buffer.byteLength(bodyData));
      // stream the content
      proxyReq.write(bodyData);
  }
}

const apiProxyOptions = {
  target: process.env.API_SERVER, // 目标主机
  changeOrigin: true,               // 需要虚拟主机站点
  ws:true,
  pathRewrite: {
    '^/api' : '/'
  },
  onProxyReq:restream,
};

const fileProxyOptions = {
  target: process.env.FILE_SERVER, // 目标主机
  changeOrigin: true,               // 需要虚拟主机站点
  ws:true,
  pathRewrite: {
    '^/imagesUpload' : '/'
  },
  onProxyReq:restream,
};

const wsProxyOptions = {
  target: '/', // 目标主机
  changeOrigin: true,               // 需要虚拟主机站点
  ws:true,
  pathRewrite: {
    '^/' : '/websocket'
  },
  onProxyReq:restream,
};

const apiProxy = proxy(apiProxyOptions);  //开启代理功能，并加载配置
const fileProxy = proxy(fileProxyOptions);
const wsProxy = proxy(wsProxyOptions)

module.exports={
  apiProxy,
  fileProxy,
  wsProxy
}
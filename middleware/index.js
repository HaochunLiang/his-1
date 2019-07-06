require('dotenv').config()
const mysql = require('mysql')
const express = require('express')
const cookieSession = require('cookie-session');
const bodyParser = require('body-parser');
const crypto = require('crypto');
const cors = require('./cors')
const auth = require('./auth')
const proxy = require('./proxy')
const inject = require('./uidInject')

var DB = null;
var pingInterval;
const mysqlConf = {
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database:process.env.DB_NAME,
  port:process.env.DB_PORT
}

// 如果数据连接出错，则重新连接
const handleError=(err)=> {
  console.log(err.stack || err);
  connect();
}

// 建立数据库连接
const connect=()=> {
  if (DB !== null) {
    DB.destroy();
    DB = null;
  }
  DB = mysql.createConnection(mysqlConf);
  DB.connect((err)=> {
    if (err) {
      console.log("error when connecting to db,reConnecting after 2 seconds:", err);
      setTimeout(connect, 2000);
    }
  });
  DB.on("error", handleError);

  // 每个小时ping一次数据库，保持数据库连接状态
  clearInterval(pingInterval);
  pingInterval = setInterval(() => {
    console.log('ping...');
    DB.ping((err) => {
      if (err) {
        console.log('ping error: ' + JSON.stringify(err));
      }
    });
  }, 3600000);
}

connect();

/*
const checkDB=()=>{
  if(DB.state==='disconnected') {
    DB = mysql.createConnection({
      host: process.env.DB_HOST,
      user: process.env.DB_USER,
      password: process.env.DB_PASS,
      database:process.env.DB_NAME,
      port:process.env.DB_PORT
    })
    console.log('db status',DB.state,'reconnect db')
    return false;
  } else {
    return true;
  }
}*/

const hash=(pwd)=>{
  const obj=crypto.createHash('md5'); 
  obj.update(pwd);
  const str=obj.digest('hex');
  return str;
}

const server = express();
server.use(cookieSession({
  name:'session',
  keys:['boy ♂ next ♂ door','thank ♂ you ♂ sir','deep ♂ dark ♂ fantasy'],
  maxAge:400*60*100*100
}));

server.use(bodyParser.json({limit: '50MB'}));
server.use(bodyParser.urlencoded({limit: '50MB', extended: true}));
//cors
server.all('*',cors)

//提交登录
server.post('/login',(req,res)=>{
  const username = req.body.username;
  const password = req.body.password;
  console.log('login',{username:username,password:password});
  DB.query(`select * from user,user_info where username='${username}' and user.id=user_info.uid`,(err,data)=>{
    if(err) {
      console.log(err);
      res.json({code:500,msg:"服务器错误"}).end();
    } else {
      if(data.length!=1 || hash(password)!=data[0].password)  {
        console.log(data)
        console.log(hash(password))
        //console.log(hash(data[0].password),password)
        res.json({code:403,msg:"用户名或者密码错误"}).end();
      } else {
        req.session['uid'] = data[0].id;
        req.session['role_id'] = data[0].role_id;
        console.log('login success,uid:',data[0].uid,'role_id:',data[0].role_id);
        res.json({code:200}).end();
      }
    }
  })
});

//退出
server.post('/logout',(req,res)=>{
  req.session['uid']=null;
  req.session['role_id'] = null;
  res.json({code:200}).end();
});

//AuthFilter
server.all('*',auth)
//uid inject
server.all('*',inject)
//reverse Proxy
server.use('/api', proxy.apiProxy);
//fileserver proxy
server.use('/imagesUpload',proxy.fileProxy)
//wsProxy
server.use('/websocket',proxy.wsProxy)
//error api


//mysql conn check
server.use('/test',(req,res)=>{
  //const connect = checkDB();
  DB.query('select * from user',(err,data)=>{
    res.json({
      data:data
    }).end();
  })
})

server.all('*',(req,res)=>{res.json({code:404,msg:'his middleware:uknown api'}).end()})

console.log('======== Node Auth Procy Server Running ========');
console.log('DB_HOST: ',process.env.DB_HOST)
console.log('DB_NAME: ',process.env.DB_NAME)
console.log('RUNNING: ',process.env.PORT)
console.log('MODE: ',process.env.MODE)
console.log("================================================");
server.listen(process.env.PORT)


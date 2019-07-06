require('dotenv').config()
const express = require('express')

const inject = express.Router();

//此处session存在 注入_uid
inject.use('/',(req,res,next)=>{
  //debug mode
  uid = parseInt(process.env.DEFAULT_INJECT_UID);
  if(process.env.MODE=="DEV") {
    uid = req.session['uid']
  }
  req.body._uid = uid;
  console.log('injected json:',req.body)
  next();
});


module.exports=inject  



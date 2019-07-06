const express = require('express')
const auth = express.Router()

const Roles = {
  HospitialAdmin:1,
  RegisteredTollCollector:2,
  FinancialAdmin:3,
  PharmacyOperator:4,
  OutpatientDoctor:5,
  DoctorOfTechnology:6
}

//session验证
auth.use('/',(req,res,next)=>{
  console.log('enter auth',req.url,{uid:req.session.uid,role_id:req.session.role_id})
  if(req.session['uid']==null) 
    res.json({code:403,msg:"登录已过期"}).end()
  else 
    next();
});

//角色过滤
auth.use('/',(req,res,next)=>{
/*  if(req.url.startsWith("/api/hospitalAdmin") && res.session['role_id']!=Roles.HospitialAdmin)
    res.json({code:403}).end();
  else if(req.url.startsWith("/api/registeredTollCollector") && res.session['role_id']!=Roles.RegisteredTollCollector)
    res.json({code:403}).end();
  else if(req.url.startsWith("/api/outpatientDoctor") && res.session['role_id']!=Roles.OutpatientDoctor)
    res.json({code:403}).end();
  else if(req.url.startsWith("/api/doctorOfTechnology") && res.session['role_id']!=Roles.DoctorOfTechnology)
    res.json({code:403}).end();
  else if(req.url.startsWith("/api/pharmacyOperator") && res.session['role_id']!=Roles.PharmacyOperator)
    res.json({code:403}).end();
  else if(req.url.startsWith("/api/financialAdmin") && res.session['role_id']!=Roles.FinancialAdmin)
    res.json({code:403}).end();
  else //url is /api/public 不需要受到限制直接next
    next();*/
next();
});

module.exports = auth
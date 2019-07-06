const Roles = {
  HospitialAdmin:1,
  RegisteredTollCollector:2,
  FinancialAdmin:3,
  PharmacyOperator:4,
  OutpatientDoctor:5,
  DoctorOfTechnology:6,

  isDoctor:(value)=>{
    return value===5;
  }
}

export default Roles;

/*
挂号收费员    registered toll collector
门诊医生      outpatient doctor
医技医生      doctor of technology
药房操作员    pharmacy  operator    
财务管理员    financial adminstrator
医院管理员    hospotial adminstrator
 */
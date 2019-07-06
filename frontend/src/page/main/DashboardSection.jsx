import React from 'react';

import LogoDisplay from '../../global/LogoDisplay';

//3 hospitalAdmin
import DepartmentManagement from '../../section/hospitialAdmin/departmentManagement'
import UserManagement from '../../section/hospitialAdmin/userManagement'
import SettlementCategoryManagement from '../../section/hospitialAdmin/settlementCategoryManagement'
import DiagnosticDirectoryManagementSection from '../../section/hospitialAdmin/diagnosticDirectoryManagement'
import RegistrationLevelManagement from '../../section/hospitialAdmin/registrationLevelManagement'
import NonDrugChargeItemManagement from '../../section/hospitialAdmin/nonDrugChargeItemManagement'
//import SchedulingManagement from '../../section/hospitialAdmin/schedulingManagement'
import SchedulingManagement from '../../section/hospitialAdmin/doctorSchedulingManagement'

//4 registeredTollCollector
import OutpatientRegistration from '../../section/registeredTollCollector/outpatientRegistration'
import OutpatientCharge from '../../section/registeredTollCollector/outpatientCharge'
import DailyCollectSection from '../../section/registeredTollCollector/dailyCollect'

//5 outpatientDoctor
import OutpatientDoctorWorkspace from '../../section/outpatientDoctor'
import OutpatientDoctorWorkloadStatistic from '../../section/outpatientDoctor/WorkloadStatistic'

//6 pharmacyOperator
import OutpatientDispensingSection from '../../section/pharmacyOperator/outpatientDispensing'
import DrugCatalogueManagement from '../../section/pharmacyOperator/drugCatalogueManagement'

//7 doctorOfTechnology
import DoctorOfTechnologyMainWorkStation from '../../section/doctorOfTechnology/mainWorkStation'
import PersonalWorkloadStatistic from '../../section/doctorOfTechnology/personalWorkloadStatistic'

//8 financialAdmin
import ExpenseClassificationManagement from '../../section/financialAdmin/expenseClassificationManagement'
import OupatientDailyCheck from '../../section/financialAdmin/outpatientDailyCheck'
import OutpatientDepartmentsWorkloadStatistic from '../../section/financialAdmin/outpatientDepartmentsWorkloadStatistic'
import OutpatientPersonalWorkloadStatistic from '../../section/financialAdmin/outpatientPersonalWorkloadStatistic'


class DashboardSection extends React.Component {
  render() {
      const me = this.props.me;
      if(!me) {
        window.location.href="/login"
      }
      const {sectionKey} = this.props;
      return (
        <div>
          {sectionKey==="0"?<LogoDisplay me={this.props.me}/>:null}

          {sectionKey==="3-1"?<DepartmentManagement me={this.props.me}/>:null}
          {sectionKey==="3-2"?<UserManagement me={this.props.me}/>:null}
          {sectionKey==="3-3"?<RegistrationLevelManagement me={this.props.me}/>:null}
          {sectionKey==="3-4"?<SettlementCategoryManagement me={this.props.me}/>:null}
          {sectionKey==="3-5"?<DiagnosticDirectoryManagementSection me={this.props.me}/>:null}
          {sectionKey==="3-6"?<NonDrugChargeItemManagement me={this.props.me}/>:null}
          {sectionKey==="3-7"?<SchedulingManagement me={this.props.me}/>:null}

          {sectionKey==="4-1"?<OutpatientRegistration me={this.props.me}/>:null}
          {sectionKey==="4-2"?<OutpatientCharge me={this.props.me}/>:null}
          {sectionKey==="4-3"?<DailyCollectSection/>:null}

          {sectionKey==="5-1"?<OutpatientDoctorWorkspace me={this.props.me}/>:null}
          {sectionKey==="5-15"?<OutpatientDoctorWorkloadStatistic  me={this.props.me}/>:null}

          {sectionKey==="6-1"?<DoctorOfTechnologyMainWorkStation/>:null}
          {sectionKey==="6-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="6-3"?<PersonalWorkloadStatistic me={this.props.me}/>:null}

          {sectionKey==="7-1"?<OutpatientDispensingSection me={this.props.me}/>:null}
          {sectionKey==="7-2"?<div>ToBeImplement</div>:null}
          {sectionKey==="7-3"?<DrugCatalogueManagement me={this.props.me}/>:null}

          {sectionKey==="8-1"?<ExpenseClassificationManagement me={this.props.me}/>:null}
          {sectionKey==="8-2"?<OupatientDailyCheck me={this.props.me}/>:null}
          {sectionKey==="8-3"?<OutpatientDepartmentsWorkloadStatistic me={this.props.me}/>:null}
          {sectionKey==="8-4"?<OutpatientPersonalWorkloadStatistic me={this.props.me}/>:null}
        </div>);
  }
}

export default DashboardSection;
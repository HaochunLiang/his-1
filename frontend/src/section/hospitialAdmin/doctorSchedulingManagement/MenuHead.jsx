import React from 'react';
import PersonnelManagement from './PersonnelManagement';
import WorkforceManage from './WorkforceManage';
import SchedulingStatistics from './SchedulingStatistics';

class MenuHead extends React.Component {
  render() {
    const {sectionKey} = this.props;
    return (
      <div>
        {sectionKey==="setting:2"?<div><PersonnelManagement/></div>:null}
        {sectionKey==="setting:3"?<div><WorkforceManage/></div>:null}
        {sectionKey==="setting:4"?<div><SchedulingStatistics/></div>:null}
      </div>);
}
}

export default MenuHead;
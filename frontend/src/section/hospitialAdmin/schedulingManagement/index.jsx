import React from 'react';
import { Layout, Card} from 'antd';
const {Content} = Layout;


class SchedulingManagement
 extends React.Component {
    state = {
        loading:true//加载状态
    };    

    render() {

        return (
        <Content style={{ margin: '0 16px',paddingTop:5 }}>
          <Card title="排班">

          </Card>
        </Content>)
    }
}

export default SchedulingManagement;
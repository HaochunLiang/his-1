import React from 'react';
import {Table} from 'antd';

class DataTable extends React.Component {
    state = {
        searchText: '',
    };

    rowSelection = {
        onSelect: (record, selected, selectedRows) => {
          //console.log(record, selected, selectedRows);
          //this.setState({selectedRows:selectedRows})
        },
        onSelectAll:(record, selected, selectedRows) => {
            //console.log(record, selected, selectedRows);
            //this.setState({selectedRows:selectedRows})
        },
        onChange: (selectedRowKeys, selectedRows) => {
            this.props.setSelected(selectedRows);
            //console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        },
    };

    render() {
        return (
          <Table 
            columns={[
                {
                    title: '编号',
                    dataIndex: 'id'
                },{
                    title: '费用名称',
                    dataIndex: 'fee_name'
                },{
                    title: '拼音',
                    dataIndex: 'pinyin'
                }
            ]} 
            dataSource={this.props.data} 
            rowSelection={this.rowSelection}
            reloadData={this.props.reloadData}
      />)
    }
}

export default DataTable;
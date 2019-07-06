import React from 'react';
import {Button,Input,Icon, Table} from 'antd';
import Highlighter from 'react-highlight-words';

const paginationProps = {
    showQuickJumper: false,
    defaultPageSize:3,
  };

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
    
    getColumnSearchProps = dataIndex => ({
        filterDropdown: ({ setSelectedKeys, selectedKeys, confirm, clearFilters }) => (
            <div style={{ padding: 8 }}>
            <Input
                ref={node => { this.searchInput = node;}}
                placeholder={`Search ${dataIndex}`}
                value={selectedKeys[0]}
                onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
                style={{ width: 188, marginBottom: 8, display: 'block' }}
            />
            <Button
                type="primary"
                onClick={() => this.handleSearch(selectedKeys, confirm)}
                icon="search"
                size="small"
                style={{ width: 90, marginRight: 8 }}
            >
                Search
            </Button>
            <Button onClick={() => this.handleReset(clearFilters)} size="small" style={{ width: 90 }}>
                重置
            </Button>
            </div>
        ),
        filterIcon: filtered => (
            <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />
        ),
        onFilter: (value, record) =>
            record[dataIndex]
            .toString()
            .toLowerCase()
            .includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: visible => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: text => (
            <Highlighter
                highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                searchWords={[this.state.searchText]}
                autoEscape
                textToHighlight={text.toString()}
            />
        ),
    });

    columns = [
        {
            title: 'id',
            dataIndex: 'id',
            ...this.getColumnSearchProps('id'),
        },{
            title: '姓名',
            dataIndex: 'name',
            ...this.getColumnSearchProps('name'),
        },{
            title: '科室',
            dataIndex: 'department_name',
            ...this.getColumnSearchProps('department_name'),
        },{
            title: '职称',
            dataIndex: 'title',
            ...this.getColumnSearchProps('title'),
        },{
            title: '挂号级别',
            dataIndex: 'registration_Level',
            ...this.getColumnSearchProps('registration_Level'),
        },{
            title: '班次',
            dataIndex: 'shift',
            ...this.getColumnSearchProps('shift'),
        },{
            title: '有效期限',
            dataIndex: 'expiry_date',
            ...this.getColumnSearchProps('expiry_date'),
        },{
            title: '排班限额',
            dataIndex: 'scheduling_limit',
            ...this.getColumnSearchProps('scheduling_limit'),
        }
    ];

    //搜索
    handleSearch = (selectedKeys, confirm) => {
        confirm();
        this.setState({ searchText: selectedKeys[0] });
    };

    //搜索框重置
    handleReset = clearFilters => {
        clearFilters();
        this.setState({ searchText: '' });
    };

    render() {
        return (
          <Table 
            columns={this.columns}  
            pagination={ paginationProps}
            dataSource={this.props.data} 
            rowSelection={this.rowSelection}
            reloadData={this.props.reloadData}
      />)
    }
}

export default DataTable;
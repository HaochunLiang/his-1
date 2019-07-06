import React from 'react';
import {Button,Input,Icon, Table} from 'antd';
import Highlighter from 'react-highlight-words';

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
                Reset
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
            title: '统一编号',
            dataIndex: 'id',
            ...this.getColumnSearchProps('id'),
        },{
            title: '项目名称',
            dataIndex: 'name',
            ...this.getColumnSearchProps('name'),
        },{
            title: '拼音',
            dataIndex: 'pinyin',
            ...this.getColumnSearchProps('pinyin'),
        },{
            title: '规格',
            dataIndex: 'format',
            ...this.getColumnSearchProps('format'),
        },{
            title: '费用',
            dataIndex: 'fee',
            ...this.getColumnSearchProps('fee'),
        },{
            title: '费用类别',
            dataIndex: 'expense_classification_name',
            ...this.getColumnSearchProps('expense_classification_name'),
        },{
            title: '执行科室',
            dataIndex: 'department_name',
            ...this.getColumnSearchProps('department_name'),
        },
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
            dataSource={this.props.data} 
            rowSelection={this.rowSelection}
            reloadData={this.props.reloadData}
      />)
    }
}

export default DataTable;
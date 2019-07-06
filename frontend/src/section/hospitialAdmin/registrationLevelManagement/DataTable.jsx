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
                搜索
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
            title: '编号',
            dataIndex: 'id',
            ...this.getColumnSearchProps('id'),
        },{
            title: '名称',
            dataIndex: 'name',
            ...this.getColumnSearchProps('name'),
        },{
            title: '是否默认',
            dataIndex: 'is_default',
            ...this.getColumnSearchProps('is_default'),
            render:(data)=> (data?<span>是</span>:<span>否</span>)
        },{
            title: '显示顺序号',
            dataIndex: 'seq_num',
            ...this.getColumnSearchProps('seq_num'),
        },{
            title: '费用',
            dataIndex: 'fee',
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
            dataSource={this.props.data} 
            rowSelection={this.rowSelection}
            reloadData={this.props.reloadData}
      />)
    }
}

export default DataTable;
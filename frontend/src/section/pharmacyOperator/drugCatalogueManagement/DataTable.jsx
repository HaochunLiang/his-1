import React from 'react';
import { Card, Button,Input,Icon, Table,Descriptions} from 'antd';
import Highlighter from 'react-highlight-words';

class DataTable extends React.Component {
    state = {
        searchText: '',
        drugDetail:null
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
            title: '国际编码',
            dataIndex: 'code',
            ...this.getColumnSearchProps('code'),
        },{
            title: '药品名称',
            dataIndex: 'name',
            ...this.getColumnSearchProps('name'),
        },{
            title: '剂型',
            dataIndex: 'dosage_form',
            ...this.getColumnSearchProps('dosage_form'),
        },{
            title: '类型',
            dataIndex: 'type',
            ...this.getColumnSearchProps('type'),
        },{
            title: '价格',
            dataIndex: 'price',
            ...this.getColumnSearchProps('price'),
        },{
            title: '库存',
            dataIndex: 'stock',
            ...this.getColumnSearchProps('stock'),
        },{
            title:"操作",
            render:(text,record,index)=>(<Button type="primary" size="small" onClick={()=>{this.setState({drugDetail:record})}}>详细</Button>)
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
        const {drugDetail} = this.state;
        return (
        <div>
        <Card>
            <Table 
                pagination={{pageSize:6}} 
                columns={this.columns} 
                dataSource={this.props.data} 
                rowSelection={this.rowSelection}
                reloadData={this.props.reloadData}
                onRow={record => {
                    return {
                    onDoubleClick: event => {
                        this.setState({drugDetail:record})
                    }
                    };
                }}
            /> 
        </Card>
         
        {drugDetail?
          <Card>
          <Descriptions title="药品详情信息" bordered column={3}>
            <Descriptions.Item label="编号">{drugDetail.id}</Descriptions.Item>
            <Descriptions.Item label="药品名称">{drugDetail.name}</Descriptions.Item>
            <Descriptions.Item label="拼音">{drugDetail.pinyin}</Descriptions.Item>
            <Descriptions.Item label="国际编号">{drugDetail.code}</Descriptions.Item>
            <Descriptions.Item label="剂型">{drugDetail.dosage_form}</Descriptions.Item>
            <Descriptions.Item label="价格">{drugDetail.price}</Descriptions.Item>
            <Descriptions.Item label="类型">{drugDetail.type}</Descriptions.Item>
            <Descriptions.Item label="单位">{drugDetail.unit}</Descriptions.Item>
            <Descriptions.Item label="库存">{drugDetail.stock}</Descriptions.Item>
            <Descriptions.Item label="生产商">{drugDetail.manufacturer}</Descriptions.Item>
          </Descriptions>
          </Card>
          :null}
      </div>)
    }
}

export default DataTable;
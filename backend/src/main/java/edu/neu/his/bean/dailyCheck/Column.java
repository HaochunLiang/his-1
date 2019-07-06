package edu.neu.his.bean.dailyCheck;

public class Column {
    private String title;
    private String dataIndex;

    public Column(String title, String dataIndex) {
        this.title = title;
        this.dataIndex = dataIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }
}

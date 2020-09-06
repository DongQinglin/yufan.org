package me.dongqinglin.auto.bean;

import java.util.List;

public class Table {
    private String tableName;
    private List<Object> columns;
    private List createTable;

    public Table() {
    }

    public Table(String tableName, List<Object> columns, List createTable) {
        this.tableName = tableName;
        this.columns = columns;
        this.createTable = createTable;
    }

    public String getTableName() {
        return tableName;
    }

    public List<Object> getColumns() {
        return columns;
    }

    public List getCreateTable() {
        return createTable;
    }
}

package me.dongqinglin.auto.service;

import java.util.List;

public interface TableService {
    public List<String> showTables();
    public List showColumnsByTableName(String tableName);
    public List findAllByTableName(String tableName);
    public void dropByTableName(String tableName);
    public List showCreateTableBy(String tableName);
}

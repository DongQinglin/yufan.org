package me.dongqinglin.auto.DAO;

import java.util.List;

public interface TableDao {
    public List<String> showTables();
    public List showColumnsByTableName(String tableName);
    public List findAllByTableName(String tableName);
    public void dropByTableName(String tableName);
    public List showCreateTableBy(String tableName);
}

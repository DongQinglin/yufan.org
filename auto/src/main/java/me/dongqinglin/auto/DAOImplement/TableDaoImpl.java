package me.dongqinglin.auto.DAOImplement;

import me.dongqinglin.auto.DAO.TableDao;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class TableDaoImpl implements TableDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> showTables() {
        List resultList;
        String showTableSql = "show tables;";
        Query showTableQuery = entityManager.createNativeQuery(showTableSql);
        resultList = showTableQuery.getResultList();
        return resultList;
    }

    @Override
    public List showColumnsByTableName(String tableName) {
        List resultList;
        String showTableColumnsSql = "show columns from " + tableName + ";";
        Query showColumnQuery = entityManager.createNativeQuery(showTableColumnsSql);
        resultList = showColumnQuery.getResultList();
        return resultList;
    }

    @Override
    public List findAllByTableName(String tableName) {

        String showTableColumnsSql = "select * from " + tableName + ";";
        Query findAllQuery = entityManager.createNativeQuery(showTableColumnsSql);
        List resultList = findAllQuery.getResultList();
        return resultList;
    }

    @Override
    @Transactional
    public void dropByTableName(String tableName) {
        try {
            String dropTableSql = "drop table " + tableName + ";";
            System.out.println(dropTableSql);
            Query dropTableQuery = entityManager.createNativeQuery(dropTableSql);
            dropTableQuery.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    @Override
    public List showCreateTableBy(String tableName) {
        List results = showColumnsByTableName(tableName);
        System.out.println(results);
        String showTableColumnsSql = "show create table " + tableName + ";";
        Query findAllQuery = entityManager.createNativeQuery(showTableColumnsSql);
        List resultList = findAllQuery.getResultList();
        return resultList;
    }
}
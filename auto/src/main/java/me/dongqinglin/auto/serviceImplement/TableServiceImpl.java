package me.dongqinglin.auto.serviceImplement;

import me.dongqinglin.auto.DAO.TableDao;
import me.dongqinglin.auto.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TableServiceImpl implements TableService {
    @Autowired
    private TableDao tableDao;

    @Override
    public List<String> showTables() {
        return tableDao.showTables();
    }

    @Override
    public List showColumnsByTableName(String tableName) {
        return tableDao.showColumnsByTableName(tableName);
    }

    @Override
    public List findAllByTableName(String tableName) {
        return tableDao.findAllByTableName(tableName);
    }

    @Override
    public void dropByTableName(String tableName) {
        tableDao.dropByTableName(tableName);
    }

    @Override
    public List showCreateTableBy(String tableName) {
            return tableDao.showCreateTableBy(tableName);
    }
}

package me.dongqinglin.auto.controller.admin;

import me.dongqinglin.auto.bean.Table;
import me.dongqinglin.auto.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/admin/table")
public class TableController {
    @Autowired
    private TableService tableService;

    @GetMapping("showTables")
    public List<String> showTables(){
        return tableService.showTables();
    }

    @GetMapping("showColumnsBy/{tableName}")
    public List showColumnsByTableName(@PathVariable String tableName){
        return tableService.showColumnsByTableName(tableName);
    }

    @GetMapping("showCreateTableBy/{tableName}")
    public List showCreateTableBy(@PathVariable String tableName){
        return tableService.showCreateTableBy(tableName);
    }

    @GetMapping("showTablesColumnsAndCreateTable")
    public ResponseEntity<?> showTablesColumnsAndCreateTable(){
        List<Table> result = new ArrayList<>();
        List<String> tableNames = tableService.showTables();
//        System.out.println(tableNames);
        for(String tableName : tableNames){
            List columns = tableService.showColumnsByTableName(tableName);
            List createTable = tableService.showCreateTableBy(tableName);
            Table newTable = new Table(tableName, columns, createTable);
            result.add(newTable);
        }
        return ResponseEntity.ok(result);
    }



    @GetMapping("findAllBy/{tableName}")
    public List findAllByTableName(@PathVariable String tableName){
        return tableService.findAllByTableName(tableName);
    }
    @DeleteMapping("dropBy/{tableName}")
    public void dropByTableName(@PathVariable String tableName){
        tableService.dropByTableName(tableName);
    }
}

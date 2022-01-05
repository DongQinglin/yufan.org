package me.dongqinglin.flina.file.data.context.provider;

import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertConfig;
import me.dongqinglin.flina.rest.middleware.mysql.HibernateInsertStrategy;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class HibernateInsertConfigTest {

    @Test
    public void should_get_path_name_of_its_own_class () {
        String expectStr = HibernateInsertStrategy.class.getName();
        Assertions.assertEquals(expectStr, HibernateInsertConfig.STRATEGY_PATH);
    }

}
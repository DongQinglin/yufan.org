package me.dongqinglin.bootstarter.global.config;


import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;
import java.io.Serializable;

public class HibernateInsertConfig  extends IdentityGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        if (id != null && Integer.valueOf(id.toString()) > 0) {
            return id;
        }
        return super.generate(s, obj);
    }
}

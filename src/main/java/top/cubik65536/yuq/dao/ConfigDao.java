package top.cubik65536.yuq.dao;

import com.icecreamqaq.yudb.YuDao;
import com.icecreamqaq.yudb.jpa.annotation.Dao;
import com.icecreamqaq.yudb.jpa.annotation.Select;
import top.cubik65536.yuq.entity.ConfigEntity;

import java.util.List;

@Dao
public interface ConfigDao extends YuDao<ConfigEntity, Integer> {
    @Select("from ConfigEntity")
    List<ConfigEntity> findAll();

    ConfigEntity findByType(String type);
}
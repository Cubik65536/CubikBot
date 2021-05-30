package top.cubik65536.yuq.dao;

import com.icecreamqaq.yudb.YuDao;
import com.icecreamqaq.yudb.jpa.annotation.Dao;
import com.icecreamqaq.yudb.jpa.annotation.Select;
import top.cubik65536.yuq.entity.RecallEntity;

import java.util.List;

@Dao
public interface RecallDao extends YuDao<RecallEntity, Integer> {
    @Select("from RecallEntity where group = ?0 and qq = ?1 order by id desc")
    List<RecallEntity> findByGroupAndQQ(Long group, Long qq);
}
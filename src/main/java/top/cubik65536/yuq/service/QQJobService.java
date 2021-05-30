package top.cubik65536.yuq.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.yuq.entity.QQJobEntity;

import java.util.List;

@AutoBind
public interface QQJobService {
    QQJobEntity findByQQAndType(Long qq, String type);

    List<QQJobEntity> findByQQ(Long qq);

    List<QQJobEntity> findByType(String type);

    void delByQQ(Long qq);

    void save(QQJobEntity qqJobEntity);
}

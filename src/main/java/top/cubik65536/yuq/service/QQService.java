package top.cubik65536.yuq.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.yuq.entity.QQEntity;

import java.util.List;

@AutoBind
public interface QQService {
    QQEntity findByQQAndGroup(Long qq, Long group);

    void save(QQEntity qqEntity);

    @SuppressWarnings("UnusedReturnValue")
    int delByQQAndGroup(Long qq, Long group);

    List<QQEntity> findAll();
}
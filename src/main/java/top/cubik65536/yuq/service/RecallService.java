package top.cubik65536.yuq.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.yuq.entity.RecallEntity;

import java.util.List;

@AutoBind
public interface RecallService {
    List<RecallEntity> findByGroupAndQQ(Long group, Long qq);

    void save(RecallEntity recallEntity);
}

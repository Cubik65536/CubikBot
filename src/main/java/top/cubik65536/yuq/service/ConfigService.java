package top.cubik65536.yuq.service;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.yuq.entity.ConfigEntity;
import top.cubik65536.yuq.pojo.ConfigType;

import java.util.List;

@AutoBind
public interface ConfigService {
    List<ConfigEntity> findAll();

    void save(ConfigEntity configEntity);

    ConfigEntity findByType(String type);

    ConfigEntity findByType(ConfigType configType);
}

package top.cubik65536.yuq.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.yuq.dao.QQLoginDao;
import top.cubik65536.yuq.entity.QQLoginEntity;
import top.cubik65536.yuq.service.QQLoginService;

import javax.inject.Inject;
import java.util.List;

public class QQLoginServiceImpl implements QQLoginService {
    @Inject
    private QQLoginDao qqLoginDao;

    @Override
    public QQLoginEntity findByQQ(Long qq) {
        return qqLoginDao.findByQQ(qq);
    }

    @Override
    @Transactional
    public void save(QQLoginEntity qqLoginEntity) {
        qqLoginDao.saveOrUpdate(qqLoginEntity);
    }

    @Override
    @Transactional
    public void delByQQ(Long qq) {
        qqLoginDao.delByQQ(qq);
    }

    @Override
    @Transactional
    public List<QQLoginEntity> findByActivity() {
        return qqLoginDao.findByActivity();
    }
}

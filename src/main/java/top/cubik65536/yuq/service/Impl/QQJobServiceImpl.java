package top.cubik65536.yuq.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.yuq.dao.QQJobDao;
import top.cubik65536.yuq.entity.QQJobEntity;
import top.cubik65536.yuq.service.QQJobService;

import javax.inject.Inject;
import java.util.List;

public class QQJobServiceImpl implements QQJobService {
    @Inject
    private QQJobDao qqJobDao;

    @Override
    @Transactional
    public QQJobEntity findByQQAndType(Long qq, String type) {
        return qqJobDao.findByQQAndType(qq, type);
    }

    @Override
    @Transactional
    public List<QQJobEntity> findByQQ(Long qq) {
        return qqJobDao.findByQQ(qq);
    }

    @Override
    @Transactional
    public List<QQJobEntity> findByType(String type) {
        return qqJobDao.findByType(type);
    }

    @Override
    @Transactional
    public void delByQQ(Long qq) {
        qqJobDao.delByQQ(qq);
    }

    @Override
    @Transactional
    public void save(QQJobEntity qqJobEntity) {
        qqJobDao.saveOrUpdate(qqJobEntity);
    }
}

package top.cubik65536.yuq.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.yuq.dao.GroupDao;
import top.cubik65536.yuq.dao.QQDao;
import top.cubik65536.yuq.entity.GroupEntity;
import top.cubik65536.yuq.entity.QQEntity;
import top.cubik65536.yuq.service.QQService;

import javax.inject.Inject;
import java.util.List;

public class QQServiceImpl implements QQService {
    @Inject
    private QQDao qqDao;
    @Inject
    private GroupDao groupDao;

    @Override
    @Transactional
    public QQEntity findByQQAndGroup(Long qq, Long group) {
        GroupEntity groupEntity = groupDao.findByGroup(group);
        if (groupEntity == null) return null;
        return qqDao.findByQQAndGroup(qq, groupEntity.getId());
    }

    @Override
    @Transactional
    public void save(QQEntity qqEntity) {
        qqDao.saveOrUpdate(qqEntity);
    }

    @Override
    @Transactional
    public int delByQQAndGroup(Long qq, Long group) {
        GroupEntity groupEntity = groupDao.findByGroup(group);
        if (groupEntity == null) return 0;
        return qqDao.delByQQAndGroup(qq, groupEntity.getId());
    }

    @Override
    @Transactional
    public List<QQEntity> findAll() {
        return qqDao.findAll();
    }
}

package top.cubik65536.yuq.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.yuq.dao.GroupDao;
import top.cubik65536.yuq.entity.GroupEntity;
import top.cubik65536.yuq.service.GroupService;

import javax.inject.Inject;
import java.util.List;

public class GroupServiceImpl implements GroupService {

    @Inject
    private GroupDao groupDao;

    @Override
    @Transactional
    public void save(GroupEntity groupEntity) {
        groupDao.saveOrUpdate(groupEntity);
    }

    @Override
    @Transactional
    public GroupEntity findByGroup(Long group) {
        return groupDao.findByGroup(group);
    }

    @Override
    @Transactional
    public List<GroupEntity> findByOnTimeAlarm(Boolean onTimeAlarm) {
        return groupDao.findByOnTimeAlarm(onTimeAlarm);
    }

    @Override
    @Transactional
    public List<GroupEntity> findAll() {
        return groupDao.findAll();
    }

    @Override
    @Transactional
    public List<GroupEntity> findByLocMonitor(Boolean locMonitor) {
        return groupDao.findByLocMonitor(locMonitor);
    }
}

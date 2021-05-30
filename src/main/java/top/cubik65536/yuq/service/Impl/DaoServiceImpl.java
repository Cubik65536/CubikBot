package top.cubik65536.yuq.service.impl;

import com.icecreamqaq.yudb.jpa.annotation.Transactional;
import top.cubik65536.yuq.dao.QQJobDao;
import top.cubik65536.yuq.dao.QQLoginDao;
import top.cubik65536.yuq.service.DaoService;

import javax.inject.Inject;

public class DaoServiceImpl implements DaoService {
    @Inject
    private QQJobDao qqJobDao;
    @Inject
    private QQLoginDao qqLoginDao;

    @Override
    @Transactional
    public void delByQQ(Long qq) {
        qqJobDao.delByQQ(qq);
        qqLoginDao.delByQQ(qq);
    }
}

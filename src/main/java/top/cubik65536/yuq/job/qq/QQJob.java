package top.cubik65536.yuq.job.qq;

import com.IceCreamQAQ.Yu.annotation.Cron;
import com.IceCreamQAQ.Yu.annotation.JobCenter;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.message.Message;
import top.cubik65536.yuq.entity.QQLoginEntity;
import top.cubik65536.yuq.logic.QQLoginLogic;
import top.cubik65536.yuq.pojo.Result;
import top.cubik65536.yuq.service.QQLoginService;
import top.cubik65536.yuq.utils.QQPasswordLoginUtils;
import top.cubik65536.yuq.utils.QQUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JobCenter
@SuppressWarnings("unused")
public class QQJob {
    @Inject
    private QQLoginService qqLoginService;
    @Inject
    private QQLoginLogic qqLoginLogic;

    @Cron("30s")
    public void checkAndUpdate() {
        List<QQLoginEntity> list = qqLoginService.findByActivity();
        for (QQLoginEntity qqLoginEntity : list) {
            try {
                String result = qqLoginLogic.groupMemberInfo(qqLoginEntity, 123456L).getMessage();
                if (result.contains("失败，请更新QQ")) {
                    if (qqLoginEntity.getPassword() == null) {
                        qqLoginEntity.setStatus(false);
                        qqLoginService.save(qqLoginEntity);
                        FunKt.getYuq().getGroups().get(qqLoginEntity.getGroup())
                                .get(qqLoginEntity.getQq())
                                .sendMessage(Message.Companion.toMessage("您的QQ登录已失效！！"));
                    } else {
                        List<Integer> numList = new ArrayList<>();
                        numList.add(400);
                        numList.add(1);
                        numList.add(-1);
                        numList.add(7);
                        Result<Map<String, String>> loginResult = QQPasswordLoginUtils.login(qqLoginEntity.getQq(), qqLoginEntity.getPassword());
                        if (loginResult.getCode() == 200) {
                            QQUtils.saveOrUpdate(qqLoginService, loginResult.getData(), qqLoginEntity.getQq(), qqLoginEntity.getPassword(), null);
                        } else if (numList.contains(loginResult.getCode())) {
                            qqLoginEntity.setStatus(false);
                            qqLoginService.save(qqLoginEntity);
                            String msg = "您的QQ自动更新失败，" + loginResult.getMessage();
                            if (qqLoginEntity.getGroup() != null) {
                                FunKt.getYuq().getFriends().get(qqLoginEntity.getQq())
                                        .sendMessage(Message.Companion.toMessage(msg));
                            } else {
                                FunKt.getYuq().getGroups().get(qqLoginEntity.getGroup())
                                        .get(qqLoginEntity.getQq())
                                        .sendMessage(Message.Companion.toMessage(msg));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Cron("At::d::06:00")
    public void sVipMorn() {
        List<QQLoginEntity> list = qqLoginService.findByActivity();
        list.forEach(qqLoginEntity -> {
            try {
                qqLoginLogic.sVipMornClock(qqLoginEntity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

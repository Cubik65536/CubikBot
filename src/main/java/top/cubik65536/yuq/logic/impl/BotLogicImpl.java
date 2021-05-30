package top.cubik65536.yuq.logic.impl;

import com.IceCreamQAQ.Yu.annotation.Config;
import com.IceCreamQAQ.Yu.util.OkHttpWebImpl;
import com.icecreamqaq.yuq.mirai.MiraiBot;
import okhttp3.Cookie;
import top.cubik65536.yuq.entity.QQLoginEntity;
import top.cubik65536.yuq.logic.BotLogic;
import top.cubik65536.yuq.utils.QQUtils;

import javax.inject.Inject;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unused")
public class BotLogicImpl implements BotLogic {
    @Inject
    private OkHttpWebImpl web;
    @Inject
    private MiraiBot miraiBot;
    @Config("YuQ.Mirai.user.qq")
    private String qqStr;

    @Override
    public QQLoginEntity getQQLoginEntity() {
        try {
            ConcurrentHashMap<String, Map<String, Cookie>> map = web.getDomainMap();
            Map<String, Cookie> qunMap = map.get("qun.qq.com");
            String groupPsKey = qunMap.get("p_skey").value();
            Map<String, Cookie> qqMap = map.get("qq.com");
            String sKey = qqMap.get("skey").value();
            Map<String, Cookie> qZoneMap = map.get("qzone.qq.com");
            String psKey = qZoneMap.get("p_skey").value();
            return new QQLoginEntity(null, Long.valueOf(qqStr), 0L, "", sKey, psKey, groupPsKey, miraiBot.superKey,
                    QQUtils.getToken(miraiBot.superKey).toString(), null, true);
        } catch (Exception e) {
            return new QQLoginEntity();
        }
    }

    private String de(String data) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(data));
    }

}

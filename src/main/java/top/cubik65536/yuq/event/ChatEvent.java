package top.cubik65536.yuq.event;

import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.alibaba.fastjson.JSONArray;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import top.cubik65536.yuq.entity.GroupEntity;
import top.cubik65536.yuq.service.GroupService;
import top.cubik65536.yuq.utils.BotUtils;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChatEvent
 * top.cubik65536.yuq.event
 * CubikBot
 * <p>
 * Created by Cubik65536 on 2021-05-24.
 * Copyright © 2020-2021 Cubik Inc. All rights reserved.
 * <p>
 * Description: 机器人聊天事件
 * History:
 * 1. 2021-05-24 [Cubik65536]: Create file ChatEvent;
 */

@EventListener
@SuppressWarnings("unused")
public class ChatEvent {
    private final Map<Long, JSONArray> lastMessage = new ConcurrentHashMap<>();
    private final Map<Long, Long> lastQQ = new ConcurrentHashMap<>();
    private final Map<Long, JSONArray> lastRepeatMessage = new ConcurrentHashMap<>();
    @Inject
    private GroupService groupService;

    @Event
    public void grassSpammer(GroupMessageEvent e) {
        Message message = e.getMessage();
        if (message.toPath().size() == 0) return;
        long group = e.getGroup().getId();
        GroupEntity groupEntity = groupService.findByGroup(group);
        Boolean grassSpammer;
        if (groupEntity == null) grassSpammer = false;
        else grassSpammer = groupEntity.getRepeat();
        if (grassSpammer == null) {
            grassSpammer = false;
            groupEntity.setGrassSpammer(false);
            groupService.save(groupEntity);
        }
        if (grassSpammer && (message.toPath().get(0).startsWith("草") || message.toPath().get(0).startsWith("cao")
                || message.toPath().get(0).startsWith("艹")))
            e.getGroup().sendMessage(FunKt.getMif().text("稻 草 人").toMessage());
    }

    @Event(weight = Event.Weight.low)
    public void repeat(GroupMessageEvent e) {
        long group = e.getGroup().getId();
        GroupEntity groupEntity = groupService.findByGroup(group);
        Boolean repeat;
        if (groupEntity == null) repeat = false;
        else repeat = groupEntity.getRepeat();
        if (repeat == null) {
            repeat = false;
            groupEntity.setRepeat(false);
            groupService.save(groupEntity);
        }
        if (repeat) {
            long qq = e.getSender().getId();
            JSONArray nowJsonArray = BotUtils.messageToJsonArray(e.getMessage());
            if (lastMessage.containsKey(group)) {
//            synchronized (this) {
                JSONArray oldJsonArray = lastMessage.get(group);
                if (BotUtils.equalsMessageJsonArray(nowJsonArray, oldJsonArray) &&
                        !BotUtils.equalsMessageJsonArray(nowJsonArray, lastRepeatMessage.get(group))
                        && lastQQ.get(group) != qq) {
                    lastRepeatMessage.put(group, nowJsonArray);
                    e.getGroup().sendMessage(e.getMessage());
                }
//            }
            }
            lastMessage.put(group, nowJsonArray);
            lastQQ.put(group, qq);
        }
    }
}

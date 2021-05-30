package top.cubik65536.yuq.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.IceCreamQAQ.Yu.annotation.Config;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.annotation.QMsg;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.message.Message;
import top.cubik65536.mcdr.Connection.TCPClient;
import top.cubik65536.mcdr.Encrypt;
import top.cubik65536.mcdr.MCDReforgedRemote;
import top.cubik65536.mcdr.Resp;
import top.cubik65536.yuq.entity.GroupEntity;
import top.cubik65536.yuq.service.GroupService;

import javax.inject.Inject;

/**
 * MinecraftController
 * top.cubik65536.yuq.controller
 * CubikBot
 * <p>
 * Created by Cubik65536 on 2021-05-24.
 * Copyright © 2020-2021 Cubik Inc. All rights reserved.
 * <p>
 * Description:
 * History:
 * 1. 2021-05-24 [Cubik65536]: Create file MinecraftController;
 */

@GroupController
@SuppressWarnings("unused")
public class MinecraftController {
    @Inject
    private GroupService groupService;

    @Config("MCDR.Remote.host")
    private String MCDRRemoteHost;
    @Config("MCDR.Remote.port")
    private String MCDRRemotePort;
    @Config("MCDR.Remote.authKey")
    private String MCDRRemoteAuthKey;

    private String username;

    @Before
    public GroupEntity before(long group, Member qq) {
        GroupEntity groupEntity = groupService.findByGroup(group);
        if (groupEntity == null) groupEntity = new GroupEntity();
        if (groupEntity.getMCDRRemote()) return groupEntity;
        else throw FunKt.getMif().at(qq).plus("抱歉，MCDRRemote功能未开启！！").toThrowable();
    }

    @Action("!!list")
    public String list() {
        String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
        Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
        if (!response.getDesc().equals("success")) {
            return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
        }
        MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
        return mcdReforgedRemote.list();
    }

    @Action("!!mc")
    public String sendMessageToMC(Message message, Member qq) {
        if (message.toPath().size() == 0) return null;
        String msg = "";
        for (int i = 1; i < message.toPath().size(); i++) msg += (message.toPath().get(i) + " ");
        System.out.println("msg = " + msg);
        String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
        Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
        if (!response.getDesc().equals("success")) {
            return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
        }
        MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
        mcdReforgedRemote.say("QQ", qq.getName(), msg);
        return null;
    }

    @GroupController
    public static class MinecraftAdminController {
        @Inject
        private GroupService groupService;

        @Config("YuQ.Mirai.bot.master")
        private String master;

        @Config("MCDR.Remote.host")
        private String MCDRRemoteHost;
        @Config("MCDR.Remote.port")
        private String MCDRRemotePort;
        @Config("MCDR.Remote.authKey")
        private String MCDRRemoteAuthKey;

        @Before
        public GroupEntity before(long group, Member qq) {
            GroupEntity groupEntity = groupService.findByGroup(group);
            if (groupEntity == null) groupEntity = new GroupEntity();
            if (groupEntity.getMCDRRemote()) {
                if (groupEntity.isMCAdmin(qq.getId()) || groupEntity.isAdmin(qq.getId()) || groupEntity.isSuperAdmin(qq.getId())
                        || qq.getId() == Long.parseLong(master) || (qq.isAdmin() && Boolean.valueOf(true).equals(groupEntity.getGroupAdminAuth()))) {
                    return groupEntity;
                } else throw FunKt.getMif().at(qq).plus("您的权限不足，无法执行！！").toThrowable();
            } else throw FunKt.getMif().at(qq).plus("抱歉，MCDRRemote功能未开启！！").toThrowable();
        }

        @Action("!!brodcast")
        public String brodcast(Message message, Member qq) {
            if (message.toPath().size() == 0) return null;
            String msg = "";
            for (int i = 1; i < message.toPath().size(); i++) msg += (message.toPath().get(i) + " ");
            System.out.println("msg = " + msg);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            mcdReforgedRemote.broadcast(msg);
            return null;
        }

        @Action("!!execute")
        @QMsg(at = true)
        public String executeMC(Message message) {
            if (message.toPath().size() == 0) return null;
            String command = "";
            for (int i = 1; i < message.toPath().size(); i++) command += (message.toPath().get(i) + " ");
            System.out.println("command = " + command);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            String result = mcdReforgedRemote.execute(command);
            return "执行成功！输出：" + result;
        }

        @Action("!!whitelist")
        @QMsg(at = true)
        public String whitelist(Message message) {
            if (message.toPath().size() == 0) return null;
            String username = message.toPath().get(2);
            System.out.println("username = " + username);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            String result;
            if (message.toPath().get(1).equals("add")) result = mcdReforgedRemote.addWhiteList(username);
            else result = mcdReforgedRemote.removeWhiteList(username);
            return "执行成功！输出：" + result;
        }

        @Action("!!ban")
        @QMsg(at = true)
        public String ban(Message message) {
            if (message.toPath().size() == 0) return null;
            String username = message.toPath().get(1);
            System.out.println("username = " + username);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            String result;
            if (!message.toPath().get(2).isEmpty()) result = mcdReforgedRemote.ban(username, message.toPath().get(2));
            else result = mcdReforgedRemote.ban(username);
            return "执行成功！输出：" + result;
        }

        @Action("!!unban")
        @QMsg(at = true)
        public String unban(Message message) {
            if (message.toPath().size() == 0) return null;
            String username = message.toPath().get(1);
            System.out.println("username = " + username);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            String result = mcdReforgedRemote.unban(username);
            return "执行成功！输出：" + result;
        }

        @Action("!!banip")
        @QMsg(at = true)
        public String banIP(Message message) {
            if (message.toPath().size() == 0) return null;
            String identity = message.toPath().get(1);
            System.out.println("identity = " + identity);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            String result;
            if (!message.toPath().get(2).isEmpty()) result = mcdReforgedRemote.banIP(identity, message.toPath().get(2));
            else result = mcdReforgedRemote.banIP(identity);
            return "执行成功！输出：" + result;
        }

        @Action("!!unbanip")
        @QMsg(at = true)
        public String unbanIP(Message message) {
            if (message.toPath().size() == 0) return null;
            String identity = message.toPath().get(1);
            System.out.println("identity = " + identity);
            String authKey = Encrypt.SHA512(MCDRRemoteAuthKey);
            Resp<String> response = TCPClient.execute(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey, "authenticate");
            if (!response.getDesc().equals("success")) {
                return "连接失败！请检查MCDR端是否开启！请检查配置文件内的IP，端口，密码是否正确！";
            }
            MCDReforgedRemote mcdReforgedRemote = new MCDReforgedRemote(MCDRRemoteHost, Integer.parseInt(MCDRRemotePort), authKey);
            String result = mcdReforgedRemote.unbanIP(identity);
            return "执行成功！输出：" + result;
        }
    }
}

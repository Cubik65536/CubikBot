package top.cubik65536.yuq.controller;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Config;
import com.IceCreamQAQ.Yu.annotation.Synonym;
import com.IceCreamQAQ.Yu.util.IO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.FunKt;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.annotation.PathVar;
import com.icecreamqaq.yuq.annotation.QMsg;
import com.icecreamqaq.yuq.controller.ContextSession;
import com.icecreamqaq.yuq.entity.Group;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.job.RainInfo;
import com.icecreamqaq.yuq.message.Image;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItem;
import com.icecreamqaq.yuq.message.MessageItemFactory;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import top.cubik65536.yuq.entity.ConfigEntity;
import top.cubik65536.yuq.entity.GroupEntity;
import top.cubik65536.yuq.logic.ToolLogic;
import top.cubik65536.yuq.pojo.CodeType;
import top.cubik65536.yuq.pojo.ConfigType;
import top.cubik65536.yuq.pojo.Result;
import top.cubik65536.yuq.service.ConfigService;
import top.cubik65536.yuq.service.GroupService;
import top.cubik65536.yuq.service.MessageService;
import top.cubik65536.yuq.utils.*;

import javax.inject.Inject;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
@GroupController
public class ToolController {
    private final LocalDateTime startTime;
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    @Inject
    private ToolLogic toolLogic;
    @Inject
    private GroupService groupService;
    @Inject
    private ConfigService configService;
    @Inject
    private MessageService messageService;
    @Inject
    private RainInfo rainInfo;
    @Inject
    private MessageItemFactory mif;
    @Config("YuQ.Mirai.protocol")
    private String protocol;
    @Config("YuQ.Mirai.bot.master")
    private String master;
    @Config("YuQ.Mirai.bot.api")
    private String api;

    public ToolController() {
        startTime = LocalDateTime.now();
    }

    @QMsg(at = true)
    @Action("百度 {content}")
    public String teachYouBaidu(String content) throws IOException {
        return toolLogic.teachYou(content, "baidu");
    }

    @QMsg(at = true)
    @Action("谷歌 {content}")
    public String teachYouGoogle(String content) throws IOException {
        return toolLogic.teachYou(content, "google");
    }

    @QMsg(at = true)
    @Action("bing {content}")
    public String teachYouBing(String content) throws IOException {
        return toolLogic.teachYou(content, "bing");
    }

    @QMsg(at = true)
    @Action("搜狗 {content}")
    public String teachYouSouGou(String content) throws IOException {
        return toolLogic.teachYou(content, "sougou");
    }

    @QMsg(at = true, atNewLine = true)
    @Action("舔狗日记")
    public String dogLicking() throws IOException {
        return toolLogic.dogLicking();
    }

    @QMsg(at = true, atNewLine = true)
    @Action("百科 {params}")
    public String baiKe(String params) throws IOException {
        return toolLogic.baiKe(params);
    }

    @QMsg(at = true)
    @Action("毒鸡汤")
    public String poisonousChickenSoup() throws IOException {
        return toolLogic.poisonousChickenSoup();
    }

    @QMsg(at = true)
    @Action("名言")
    public String saying() throws IOException {
        return toolLogic.saying();
    }

    @QMsg(at = true)
    @Action("一言")
    public String hiToKoTo() throws IOException {
        return toolLogic.hiToKoTo().get("text");
    }

    @Action("缩短/{params}")
    @QMsg(at = true)
    public String shortUrl(String params) {
        return BotUtils.shortUrl(params);
    }

    @Action("ip/{params}")
    @QMsg(at = true, atNewLine = true)
    public String queryIp(String params) throws IOException {
        Result<List<Map<String, String>>> result = toolLogic.queryIp(params);
        if (result.isSuccess()) {
            List<Map<String, String>> list = result.getData();
            StringBuilder sb = new StringBuilder();
            list.forEach(map ->
                    sb.append(map.get("ip")).append("->").append(map.get("address")).append("\n")
            );
            return BotUtils.removeLastLine(sb);
        } else return "查询IP地址失败！";
    }

    @Action("whois/{params}")
    @QMsg(at = true, atNewLine = true)
    public String queryWhois(String params) throws IOException {
        return toolLogic.queryWhois(params);
    }

    @Action("icp/{params}")
    @QMsg(at = true, atNewLine = true)
    public String queryIcp(String params) throws IOException {
        return toolLogic.queryIcp(params);
    }

    @Action("知乎日报")
    @QMsg(at = true, atNewLine = true)
    public String zhiHuDaily() throws IOException {
        return toolLogic.zhiHuDaily();
    }

    @QMsg(at = true, atNewLine = true)
    @Action("测吉凶")
    public String qqGodLock(long qq) throws IOException {
        return toolLogic.qqGodLock(qq);
    }

    @QMsg(at = true, atNewLine = true)
    @Action("拼音/{params}")
    public String convertPinYin(String params) throws IOException {
        return toolLogic.convertPinYin(params);
    }

    @QMsg(at = true, atNewLine = true)
    @Action("笑话")
    public String jokes() throws IOException {
        return toolLogic.jokes();
    }

    @QMsg(at = true, atNewLine = true)
    @Action("垃圾/{params}")
    public String rubbish(String params) throws IOException {
        return toolLogic.rubbish(params);
    }

    @Action("解析/{url}")
    @QMsg(at = true, atNewLine = true)
    public String parseVideo(String url) throws IOException {
        return toolLogic.parseVideo(url);
    }

    @Action("ping/{domain}")
    @QMsg(at = true, atNewLine = true)
    public String ping(String domain) throws IOException {
        return toolLogic.ping(domain);
    }

    @Action("qr/{content}")
    @QMsg(at = true, atNewLine = true)
    public Message creatQrCode(String content) {
        InputStream is = null;
        try {
            is = toolLogic.creatQr(content);
            return FunKt.getMif().imageByInputStream(is).toMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.close(is);
        }
    }

    @QMsg(at = true)
    @Action("蓝奏 {url}")
    public String lanZou(String url, @PathVar(2) String pwd) throws UnsupportedEncodingException {
        String resultUrl;
        if (pwd == null)
            resultUrl = "https://v1.alapi.cn/api/lanzou?url=" + URLEncoder.encode(url, "utf-8");
        else resultUrl = "https://v1.alapi.cn/api/lanzou?url=" + URLEncoder.encode(url, "utf-8") + "&pwd=" + pwd;
        return BotUtils.shortUrl(resultUrl);
    }

    @Action("lol周免")
    @QMsg(at = true, atNewLine = true)
    public String lolFree() throws IOException {
        return toolLogic.lolFree();
    }

    @Action("缩写/{content}")
    @QMsg(at = true, atNewLine = true)
    public String abbreviation(String content) throws IOException {
        return toolLogic.abbreviation(content);
    }

    @Action("几点了")
    public Message time(long qq) throws IOException {
        String name = DateTimeFormatterUtils.formatNow("HH-mm") + ".jpg";
        String hour = name.split("-")[0];
        File file = new File("time" + File.separator + hour + File.separator + name);
        if (file.exists()) {
            return FunKt.getMif().imageByByteArray(IO.read(new FileInputStream(file), true)).toMessage();
        } else {
            return FunKt.getMif().at(qq).plus("请下载时间的压缩包：https://api.kuku.me/tb/pan/kuku/kuku-bot/time.zip，解压至程序根目录");
        }
    }

    @Action("\\^BV.*\\")
    @Synonym({"\\^bv.*\\"})
    @QMsg(at = true)
    public Message bvToAv(Message message) throws IOException {
        String bv = message.getBody().get(0).toPath();
        Result<Map<String, String>> result = toolLogic.bvToAv(bv);
        if (result.getCode() == 200) {
            Map<String, String> map = result.getData();
            MessageItemFactory mif = FunKt.getMif();
            return mif.imageByUrl(map.get("pic")).plus(
                    "标题：" + map.get("title") + "\n" +
                            "描述：" + map.get("desc") +
                            "链接：" + map.get("url")
            );
        } else return Message.Companion.toMessage(result.getMessage());
    }

    @Action("分词")
    @QMsg(at = true, atNewLine = true)
    public String wordSegmentation(long qq, ContextSession session, Group group) throws IOException {
        group.sendMessage(FunKt.getMif().at(qq).plus("请输入需要中文分词的内容！！"));
        Message nextMessage = session.waitNextMessage();
        return toolLogic.wordSegmentation(Message.Companion.firstString(nextMessage));
    }

    @Action("acg")
    public Image acgPic() throws IOException {
        return FunKt.getMif().imageByUrl(toolLogic.acgPic());
    }

    @Action("搜图 {img}")
    @QMsg(at = true)
    public Message searchImage(Image img) throws IOException {
        ConfigEntity configEntity = configService.findByType(ConfigType.SauceNao.getType());
        if (configEntity == null) return BotUtils.toMessage("机器人没有配置搜图（sauceNao）的apikey，无法搜图！！");
        String url = toolLogic.sauceNaoIdentifyPic(configEntity.getContent(), img.getUrl());
        if (url != null) return FunKt.getMif().imageByUrl(img.getUrl()).plus(url);
        else return Message.Companion.toMessage("没有找到这张图片！！！");
    }

    @Action("查发言数")
    public String queryMessage(Group group) {
        Map<Long, Long> map = messageService.findCountQQByGroupAndToday(group.getId());
        StringBuilder sb = new StringBuilder().append("本群今日发言数统计如下：").append("\n");
        for (Map.Entry<Long, Long> entry : map.entrySet()) {
            sb.append("@");
            if (group.getOrNull(entry.getKey()) == null) {
                sb.append("未在本群");
            } else sb.append(group.get(entry.getKey()).nameCardOrName());
            sb.append("（").append(entry.getKey()).append("）").append("：")
                    .append(entry.getValue()).append("条").append("\n");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    @Action("点歌 {name}")
    public Object musicFromQQ(String name) throws IOException {
        String xmlStr = toolLogic.songByQQ(name);
        return mif.xmlEx(2, xmlStr);
    }

    @Action("163点歌 {name}")
    public Object musicFrom163(String name) throws IOException {
        Result<String> xmlStr = toolLogic.songBy163(name);
        return mif.xmlEx(2, xmlStr.getData());
    }

    @Action("统计")
    @Synonym({"运行状态"})
    public String status() {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        // 睡眠1s
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        //总内存
        long totalByte = memory.getTotal();
        //剩余
        long acaliableByte = memory.getAvailable();
        Properties props = System.getProperties();
        //系统名称
        String osName = props.getProperty("os.name");
        //架构名称
        String osArch = props.getProperty("os.arch");
        Runtime runtime = Runtime.getRuntime();
        //jvm总内存
        long jvmTotalMemoryByte = runtime.totalMemory();
        //jvm最大可申请
        long jvmMaxMoryByte = runtime.maxMemory();
        //空闲空间
        long freeMemoryByte = runtime.freeMemory();
        //jdk版本
        String jdkVersion = props.getProperty("java.version");
        //jdk路径
        String jdkHome = props.getProperty("java.home");
        LocalDateTime nowTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, nowTime);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        String ss = days + "天" + hours + "小时" + minutes + "分钟";
        return "程序运行时长：" + ss + "\n" +
                "cpu核数：" + processor.getLogicalProcessorCount() + "\n" +
                "cpu当前使用率：" + new DecimalFormat("#.##%").format(1.0 - (idle * 1.0 / totalCpu)) + "\n" +
                "总内存：" + formatByte(totalByte) + "\n" +
                "已使用内存：" + formatByte(totalByte - acaliableByte) + "\n" +
                "操作系统：" + osName + "\n" +
                "系统架构：" + osArch + "\n" +
                "jvm内存总量：" + formatByte(jvmTotalMemoryByte) + "\n" +
                "jvm已使用内存：" + formatByte(jvmTotalMemoryByte - freeMemoryByte) + "\n" +
                "java版本：" + jdkVersion;
    }

    @Action("消息统计")
    public String message() {
        return "当前收发消息状态：\n" +
                "收：" + rainInfo.getCountRm() + " / 分钟\n" +
                "发：" + rainInfo.getCountSm() + " / 分钟\n" +
                "总计：\n" +
                "收：" + rainInfo.getCountRa() + " 条，\n" +
                "发：" + rainInfo.getCountSa() + " 条。";
    }

    private String formatByte(long byteNumber) {
        //换算单位
        double FORMAT = 1024.0;
        double kbNumber = byteNumber / FORMAT;
        if (kbNumber < FORMAT) {
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber / FORMAT;
        if (mbNumber < FORMAT) {
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber / FORMAT;
        if (gbNumber < FORMAT) {
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber / FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }

    @Action("kuku上传")
    @QMsg(at = true)
    public String uploadImage(Message message, ContextSession session, Group group, long qq) {
        group.sendMessage(FunKt.getMif().at(qq).plus("请发送需要上传的图片"));
        Message imageMessage = session.waitNextMessage();
        StringBuilder sb = new StringBuilder().append("您上传的图片链接如下：");
        int i = 1;
        for (MessageItem item : imageMessage.getBody()) {
            if (item instanceof Image) {
                sb.append(i++).append("、");
                try {
                    sb.append(toolLogic.uploadImage(OkHttpUtils.getByteStream(((Image) item).getUrl())))
                            .append("\n");
                } catch (IOException e) {
                    sb.append("图片上传失败，请稍后再试！！").append("\b");
                }
            }
        }
        return BotUtils.removeLastLine(sb);
    }

    @Action("抽象话 {word}")
    @QMsg(at = true)
    public String abstractWords(String word) {
        return "抽象话如下：\n" + toolLogic.abstractWords(word);
    }

    @Action("窥屏检测")
    public void checkPeeping(Group group) {
        String random = BotUtils.randomNum(4);
        group.sendMessage(FunKt.getMif().jsonEx("{\"app\":\"com.tencent.miniapp\",\"desc\":\"\",\"view\":\"notification\",\"ver\":\"1.0.0.11\",\"prompt\":\"QQ程序\",\"appID\":\"\",\"sourceName\":\"\",\"actionData\":\"\",\"actionData_A\":\"\",\"sourceUrl\":\"\",\"meta\":{\"notification\":{\"appInfo\":{\"appName\":\"三楼有只猫\",\"appType\":4,\"appid\":1109659848,\"iconUrl\":\"" + api + "\\/tool\\/peeping\\/check\\/" + random + "\"},\"button\":[],\"data\":[],\"emphasis_keyword\":\"\",\"title\":\"请等待15s\"}},\"text\":\"\",\"extraApps\":[],\"sourceAd\":\"\",\"extra\":\"\"}").toMessage());
        executorService.schedule(() -> {
            String msg;
            try {
                JSONObject jsonObject = OkHttpUtils.getJson(api + "/tool/peeping/result/" + random);
                if (jsonObject.getInteger("code") == 200) {
                    StringBuilder sb = new StringBuilder();
                    JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
                    sb.append("检测到共有").append(jsonArray.size()).append("位小伙伴在窥屏").append("\n");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject singleJsonObject = jsonArray.getJSONObject(i);
                        sb.append(singleJsonObject.getString("ip"))
                                .append("-").append(singleJsonObject.getString("address"))
                                /*.append("-").append(singleJsonObject.getString("simpleUserAgent"))*/.append("\n");
                    }
                    msg = BotUtils.removeLastLine(sb);
                } else msg = jsonObject.getString("message");
            } catch (IOException e) {
                e.printStackTrace();
                msg = "查询失败，请重试！！";
            }
            group.sendMessage(FunKt.getMif().text(msg).toMessage());
        }, 15, TimeUnit.SECONDS);
    }

    @Action("code {type}")
    @QMsg(at = true, atNewLine = true)
    public String codeExecute(ContextSession session, Group group, long qq, String type) throws IOException {
        CodeType codeType = CodeType.parse(type);
        if (codeType == null) return "没有找到这个语言类型！！";
        group.sendMessage(FunKt.getMif().at(qq).plus("请输入代码！！"));
        Message message = session.waitNextMessage();
        String code = Message.Companion.firstString(message);
        return toolLogic.executeCode(code, codeType);
    }

    @Action("妹子图")
    public Object girlImage(long qq) {
        byte[] bytes = toolLogic.girlImageGaNk();
        if (bytes != null) {
            return FunKt.getMif().imageByByteArray(bytes);
        } else return FunKt.getMif().at(qq).plus("图片获取失败，请重试！！");
    }

    @Action("shell {command}")
    @QMsg(at = true)
    public String shellCommand(String command, Group group, long qq, @PathVar(2) String ss) {
        GroupEntity groupEntity = groupService.findByGroup(group.getId());
        String errorMsg = "没有找到这个命令，请重试！！";
        if (groupEntity == null) return errorMsg;
        JSONArray jsonArray = groupEntity.getShellCommandJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("command").equals(command)) {
                //0为主人，1为超管，2为普管，3为用户
                Integer auth = jsonObject.getInteger("auth");
                boolean b;
                if (auth == 0) {
                    b = qq == Long.parseLong(master);
                } else if (auth == 1) {
                    b = groupEntity.isSuperAdmin(qq);
                } else if (auth == 2) {
                    b = groupEntity.isAdmin(qq);
                } else b = true;
                if (b) {
                    String shell = jsonObject.getString("shell");
                    ExecutorUtils.execute(() -> {
                        Runtime runtime = Runtime.getRuntime();
                        try {
                            String os = System.getProperty("os.name");
                            Process process = runtime.exec(shell);
                            if (ss == null) {
                                byte[] bytes = IO.read(process.getInputStream(), true);
                                String result;
                                if (os.contains("Windows")) result = new String(bytes, "gbk");
                                else result = new String(bytes, StandardCharsets.UTF_8);
                                group.sendMessage(FunKt.getMif().at(qq).plus("脚本执行成功，信息如下：\n").plus(result));
                            } else group.sendMessage(FunKt.getMif().at(qq).plus("脚本正在后台执行中！！"));
                        } catch (IOException e) {
                            e.printStackTrace();
                            group.sendMessage(FunKt.getMif().at(qq).plus("脚本执行失败！！"));
                        }
                    });
                    return "shell命令正在执行中，请稍后！！";
                } else return "您的权限不足，无法执行这个命令";
            }
        }
        return errorMsg;
    }

    @Action("抽签")
    @QMsg(at = true)
    public String random(long qq, Group group, ContextSession session) throws IOException {
        int num = Jrrp.get(qq);
        JSONObject jsonObject = toolLogic.luckJson(num);
        group.sendMessage(FunKt.getMif().at(qq).plus("今日运势：\n" + jsonObject.getJSONObject("fields").getString("texk_key") + "\n发送解签查看详解"));
        Message nextMessage = session.waitNextMessage();
        String ss = BotUtils.firstString(nextMessage);
        if (ss.equals("解签")) {
            return jsonObject.getJSONObject("fields").getString("text") + "\n凶签也不必气馁。人生势必起伏。";
        } else return "您错过解签了！！";
    }

    @Action("丢")
    @Synonym({"爬"})
    public Message diu(Member qq, @PathVar(1) String paramQQ, @PathVar(0) String type) {
        String url = qq.getAvatar();
        if (paramQQ != null) {
            if (!paramQQ.matches("[0-9]+"))
                return FunKt.getMif().at(qq).plus("您输入的不为qq号，请重试！！");
            url = "http://q1.qlogo.cn/g?b=qq&nk=" + paramQQ + "&s=640";
        }
        byte[] bytes;
        if ("丢".equals(type))
            bytes = toolLogic.diu(url);
        else bytes = toolLogic.pa(url);
        if (bytes == null) return FunKt.getMif().at(qq).plus("图片生成失败，请重试！！");
        return FunKt.getMif().imageByByteArray(bytes).toMessage();
    }

    @Action("\\.*\\")
    @QMsg(at = true, mastAtBot = true)
    public String readMessage(@PathVar(0) String str) throws IOException {
        return toolLogic.qinYunKeChat(str);
    }

    @Action("查企业 {name}")
    @QMsg(at = true, atNewLine = true)
    public String queryCompany(String name) throws IOException {
        Map<String, String> map = toolLogic.queryCompanyInfo(name);
        if (map == null) return "没有搜索到该企业！";
        return "企业名称：" + map.get("entName") + "\n" +
                "法定代表人：" + map.get("legalName") + "\n" +
                "注册资本：" + map.get("regCapital") + "\n" +
                "经营状态：" + map.get("regStatus") + "\n" +
                "成立日期：" + map.get("createTime") + "\n" +
                "注册号：" + map.get("regNo") + "\n" +
                "组织机构代码：" + map.get("orgNo") + "\n" +
                "统一社会信用代码：" + map.get("creditCode") + "\n" +
                "核准日期：" + map.get("approvedTime") + "\n" +
                "所属行业：" + map.get("industry") + "\n" +
                "公司类型：" + map.get("companyType") + "\n" +
                "登记机关：" + map.get("regInstitute") + "\n" +
                "所属地区：" + map.get("province") + "\n" +
                "注册地址：" + map.get("regLocation") + "\n" +
                "经营范围：" + map.get("opScope") + "\n" +
                "蓝鲸评分：" + map.get("score");
    }

    @Action("搜企业 {name}")
    @QMsg(at = true, atNewLine = true)
    public String searchCompany(String name) throws IOException {
        List<String> list = toolLogic.searchCompany(name);
        StringBuilder sb = new StringBuilder().append("您搜索的企业名称如下：").append("\n");
        list.forEach(s -> sb.append(s).append("\n"));
        return BotUtils.removeLastLine(sb);
    }

    @Action("妹子")
    public Image girls() throws IOException {
        return FunKt.getMif().imageByUrl(toolLogic.girl());
    }

}

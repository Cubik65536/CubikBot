package top.cubik65536.yuq.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "group_")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, name = "group_")
    private Long group;
    @Lob
    @Column(columnDefinition = "text")
    private String mcUsername;
    @Lob
    @Column(columnDefinition = "text")
    private String blackList;
    @Lob
    @Column(columnDefinition = "text")
    private String whiteList;
    @Lob
    @Column(columnDefinition = "text")
    private String violationList;
    @Lob
    @Column(columnDefinition = "text")
    private String qaList;
    @Lob
    @Column(columnDefinition = "text")
    private String adminList;
    @Lob
    @Column(columnDefinition = "text")
    private String superAdminList;
    @Lob
    @Column(columnDefinition = "text")
    private String mcAdminList;
    @Lob
    @Column(columnDefinition = "text")
    private String interceptList;
    @Lob
    @Column(columnDefinition = "text")
    private String commandLimitList;
    @Lob
    @Column(columnDefinition = "text")
    private String shellCommandList;
    private Boolean colorPic;
    private Boolean status;
    private Boolean recall;
    private Boolean pic;
    private Boolean leaveGroupBlack;
    private Boolean welcomeMsg;
    private Boolean autoReview;
    private Boolean onTimeAlarm;
    private String colorPicType;
    private Integer maxViolationCount;
    private Integer maxCommandCountOnTime;
    private Boolean locMonitor;
    private Boolean flashNotify;
    private Boolean repeat;
    private Boolean grassSpammer;
    private Boolean voiceIdentify;
    private Boolean groupAdminAuth;
    private Boolean kickWithoutSpeaking;
    private Boolean MCDRRemote;

    public GroupEntity(Long group) {
        this.group = group;
        this.colorPic = false;
        this.status = false;
        this.recall = false;
        this.pic = false;
        this.leaveGroupBlack = false;
        this.welcomeMsg = false;
        this.autoReview = false;
        this.onTimeAlarm = false;
        this.locMonitor = false;
        this.flashNotify = false;
        this.maxCommandCountOnTime = -1;
        this.maxViolationCount = 5;
        this.repeat = false;
        this.grassSpammer = false;
        this.voiceIdentify = false;
        this.groupAdminAuth = false;
        this.kickWithoutSpeaking = false;
        this.colorPicType = "quickly";
        this.MCDRRemote = false;
    }

    public JSONArray getMcUsernameArray() {
        if (blackList == null) return new JSONArray();
        else return JSON.parseArray(mcUsername);
    }

    public void setMcUsernameArray(JSONArray jsonArray) {
        this.mcUsername = jsonArray.toString();
    }

    public JSONArray getBlackJsonArray() {
        if (blackList == null) return new JSONArray();
        else return JSON.parseArray(blackList);
    }

    public void setBlackJsonArray(JSONArray jsonArray) {
        this.blackList = jsonArray.toString();
    }

    public JSONArray getWhiteJsonArray() {
        if (whiteList == null) return new JSONArray();
        else return JSON.parseArray(whiteList);
    }

    public void setWhiteJsonArray(JSONArray jsonArray) {
        this.whiteList = jsonArray.toString();
    }

    public JSONArray getViolationJsonArray() {
        if (violationList == null) return new JSONArray();
        else return JSON.parseArray(violationList);
    }

    public void setViolationJsonArray(JSONArray jsonArray) {
        this.violationList = jsonArray.toString();
    }

    public JSONArray getQaJsonArray() {
        if (qaList == null) return new JSONArray();
        else return JSON.parseArray(qaList);
    }

    public void setQaJsonArray(JSONArray jsonArray) {
        this.qaList = jsonArray.toString();
    }

    public JSONArray getAdminJsonArray() {
        if (adminList == null) return new JSONArray();
        else return JSON.parseArray(adminList);
    }

    public void setAdminJsonArray(JSONArray jsonArray) {
        this.adminList = jsonArray.toString();
    }

    public JSONArray getInterceptJsonArray() {
        if (interceptList == null) return new JSONArray();
        else return JSON.parseArray(interceptList);
    }

    public void setInterceptJsonArray(JSONArray jsonArray) {
        this.interceptList = jsonArray.toString();
    }

    public JSONArray getMCJsonArray() {
        if (superAdminList == null) return new JSONArray();
        else return JSON.parseArray(mcAdminList);
    }

    public void setMCJsonArray(JSONArray jsonArray) {
        this.mcAdminList = jsonArray.toString();
    }

    public JSONArray getSuperAdminJsonArray() {
        if (superAdminList == null) return new JSONArray();
        else return JSON.parseArray(superAdminList);
    }

    public void setSuperAdminJsonArray(JSONArray jsonArray) {
        this.superAdminList = jsonArray.toString();
    }

    public JSONObject getCommandLimitJsonObject() {
        if (commandLimitList == null) return new JSONObject();
        else return JSON.parseObject(commandLimitList);
    }

    public void setCommandLimitJsonObject(JSONObject jsonObject) {
        this.commandLimitList = jsonObject.toString();
    }

    public JSONArray getShellCommandJsonArray() {
        if (shellCommandList == null) return new JSONArray();
        else return JSON.parseArray(shellCommandList);
    }

    public void setShellCommandJsonArray(JSONArray jsonArray) {
        this.shellCommandList = jsonArray.toString();
    }

    public boolean isSuperAdmin(long qq) {
        if (isAdmin(qq)) return true;
        JSONArray superAdminJsonArray = getSuperAdminJsonArray();
        return superAdminJsonArray.contains(String.valueOf(qq));
    }

    public boolean isAdmin(long qq) {
        JSONArray adminJsonArray = getAdminJsonArray();
        return adminJsonArray.contains(String.valueOf(qq));
    }

    public boolean isMCAdmin(long qq) {
        JSONArray mcJsonArray = getMCJsonArray();
        return mcJsonArray.contains(String.valueOf(qq));
    }
}

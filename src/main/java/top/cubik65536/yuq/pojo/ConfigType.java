package top.cubik65536.yuq.pojo;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ConfigType {
    SauceNao("sauceNao"),
    IdentifyCode("identifyCode"),
    FateAdmCode("fateAdmCode"),
    OFFICE_USER("officeUser");


    private final String type;

    ConfigType(String type) {
        this.type = type;
    }
}

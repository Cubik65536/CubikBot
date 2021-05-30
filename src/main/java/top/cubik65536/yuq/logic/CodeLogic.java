package top.cubik65536.yuq.logic;

import com.IceCreamQAQ.Yu.annotation.AutoBind;
import top.cubik65536.yuq.pojo.Result;

import java.io.IOException;

@AutoBind
public interface CodeLogic {
    Result<String> identify(String type, byte[] bytes) throws IOException;
}

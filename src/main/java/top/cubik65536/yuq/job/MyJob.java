package top.cubik65536.yuq.job;

import com.IceCreamQAQ.Yu.annotation.Config;
import com.IceCreamQAQ.Yu.annotation.Cron;
import com.IceCreamQAQ.Yu.annotation.JobCenter;
import com.IceCreamQAQ.Yu.util.IO;
import top.cubik65536.yuq.service.GroupService;

import javax.inject.Inject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@JobCenter
@SuppressWarnings("unused")
public class MyJob {
    @Config("YuQ.Mirai.bot.master")
    public String master;
    @Inject
    private GroupService groupService;

    @Cron("1h")
    public void backUp() {
        File confFile = new File("conf");
        if (confFile.exists()) {
            String deviceName = "device.json";
            File rootDeviceFile = new File(deviceName);
            File confDeviceFile = new File("conf/" + deviceName);
            if (rootDeviceFile.exists()) {
                try {
                    IO.writeFile(confDeviceFile, IO.read(new FileInputStream(rootDeviceFile), true));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

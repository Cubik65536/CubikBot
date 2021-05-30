package top.cubik65536.yuq;

import com.IceCreamQAQ.Yu.util.IO;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import top.cubik65536.yuq.asm.MyClassLoader;
import top.cubik65536.yuq.asm.YuQStarterAdapter;
import top.cubik65536.yuq.utils.OkHttpUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Start {
    public static void main(String[] args) {
        // 从conf文件夹拉设备信息到根目录
        String deviceName = "device.json";
        File confDeviceFile = new File("conf/" + deviceName);
        File rootDeviceFile = new File(deviceName);
        if (confDeviceFile.exists() && !rootDeviceFile.exists()){
            try {
                IO.writeFile(rootDeviceFile, IO.read(new FileInputStream(confDeviceFile), true));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        // 如果没有配置文件，下载配置文件
        File confFile = new File("conf");
        if (!confFile.exists()) confFile.mkdir();
        File yuqFile = new File("conf/YuQ.properties");
        if (!yuqFile.exists()) {
            try {
                byte[] bytes = OkHttpUtils.downloadBytes("https://cdn.jsdelivr.net/gh/CuBitStudio/CubikBot@master/src/main/resources/conf/YuQ.properties");
                IO.writeFile(yuqFile, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ClassReader cr = new ClassReader("com.icecreamqaq.yuq.YuQStarter");
            ClassWriter cw = new ClassWriter(0);
            cr.accept(cw, 0);

            ClassReader ccr = new ClassReader("com.icecreamqaq.yuq.YuQStarter$Companion");
            ClassWriter ccw = new ClassWriter(ccr, 0);
            YuQStarterAdapter ccc = new YuQStarterAdapter(ccw);
            ccr.accept(ccc, 0);

            MyClassLoader classLoader = MyClassLoader.getInstance();
            Class<?> clazz = classLoader.defineClass("com.icecreamqaq.yuq.YuQStarter", cw.toByteArray());
            classLoader.defineClass("com.icecreamqaq.yuq.YuQStarter$Companion", ccw.toByteArray());
            Method startMethod = clazz.getDeclaredMethod("start");
            startMethod.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
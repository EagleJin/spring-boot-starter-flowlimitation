package com.king.limit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jinyp on 2017/8/23.
 */
public final class AutoLoadUtil extends FileWatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoLoadUtil.class);
    static {
        init();
        LOGGER.info("Method:static->timer..");
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new AutoLoadUtil();
            }
        },0,10000);
    }

    /**
     * 根据key获取yml文件中的value
     * @param key
     * @return
     */
    public static String getYamlValue(String key){
        return yamlProp.getObject().getProperty(key);
    }

    /**
     * 获取yml文件的property对象
     * @return
     */
    public static Properties getProperty() {
        return yamlProp.getObject();
    }

    private static void init() {
        yamlProp.setResources(new ClassPathResource(YAML_FILE_NAME));
    }
}

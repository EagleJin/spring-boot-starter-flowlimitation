package com.king.limit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.Properties;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * 热加载配置文件
 * 配置文件必须是：application.yml
 * Created by Jinyp on 2017/8/23.
 */
public abstract class FileWatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcher.class);
    private static WatchService watcher;
    protected static YamlPropertiesFactoryBean yamlProp = new YamlPropertiesFactoryBean();
    protected static final String YAML_FILE_NAME = "application.yml";

    public FileWatcher() {
        try {
            URL url = ResourceUtils.getURL(FileWatcher.class.getResource("/").getPath());
            watcher = FileSystems.getDefault().newWatchService();
            yamlProp.setResources(new ClassPathResource(YAML_FILE_NAME));
            Paths.get(url.toURI()).register(watcher, ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);
            handleEvents();
        } catch (IOException e) {
            LOGGER.error("Method:FileWatcher->IOException:",e);
        } catch (InterruptedException e) {
            LOGGER.error("Method:FileWatcher->InterruptedException:",e);
        }catch (URISyntaxException e) {
            LOGGER.error("Method:FileWatcher->URISyntaxException:",e);
        }
    }

    private void handleEvents() throws InterruptedException, IOException {
        while (true) {
            WatchKey key = watcher.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }
                WatchEvent<Path> e = (WatchEvent<Path>) event;

                if (e.context().toString().equals(YAML_FILE_NAME)) {
                    yamlProp.setResources(new ClassPathResource(YAML_FILE_NAME));
                }
            }
            if (!key.reset()) {
                break;
            }
        }
    }
}

package com.king.limit.auto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "system.limit")
public class LimitProperties {
    /**
     * 要过滤的路径
     */
    private String url;

    /**
     * 开启调试模式，调试模式下不过滤
     */
    private boolean debug = false;

    /**
     * 允许的最大请求量
     */
    private Integer maxRequest;

    /**
     * 拒绝请求时的返回信息
     */
    private String message;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Integer getMaxRequest() {
        return maxRequest;
    }

    public void setMaxRequest(Integer maxRequest) {
        this.maxRequest = maxRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

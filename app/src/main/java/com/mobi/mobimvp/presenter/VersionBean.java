package com.mobi.mobimvp.presenter;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/7/24 11:02
 * @Dec 略
 */
public class VersionBean {
    private String pkg_name;
    private String version;
    private String channel;

    public String getPkg_name() {
        return pkg_name;
    }

    public void setPkg_name(String pkg_name) {
        this.pkg_name = pkg_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}

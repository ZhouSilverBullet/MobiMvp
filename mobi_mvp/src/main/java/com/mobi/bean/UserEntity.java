package com.mobi.bean;

import android.text.TextUtils;

import com.mobi.global.MobiSession;
import com.mobi.util.SerializeSpUtil;

/**
 * author:zhaijinlu
 * date: 2019/10/28
 * desc:  单例保存用户信息
 */
public class UserEntity {
    private static final String USER_INFO = "userInfo";
    private static final String CONFIG = "config";
    private static final String TOKEN = "token";
    private static final String IS_RED = "isRed";
    private static final String NICKNAME = "nickname";
    private static final String USER_ID = "userId";
    private static final String POINTS = "points";
    private static final String CASH = "cash";
    private static final String IMEI = "imei";
    private static final String MAC = "mac";
    private static final String ANDROID_ID = "android_id";

    private String token;
    private String userId;
    private UserInfo userInfo;
    private ConfigBean configEntity;
    private int points;//当前用户金币数
    private float cash;//可兑换现金数
    private static UserEntity userEntity;
    private int target_step;//目标步数
    private String imei;
    private String mac;
    private String android_id;
    private String nickname;//游客昵称

    private boolean isRed;//是否领取红包

    private UserEntity() {

    }

    public static UserEntity getInstance() {
        if (userEntity == null) {
            synchronized (UserEntity.class) {
                if (userEntity == null) {
                    userEntity = new UserEntity();
                }
            }
        }
        return userEntity;
    }

    public UserInfo getUserInfo() {
        if (userInfo == null) {
            userInfo = (UserInfo) SerializeSpUtil.readObject(MobiSession.getInstance().getContext(), USER_INFO);
        }
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        SerializeSpUtil.saveObject(MobiSession.getInstance().getContext(), USER_INFO, userInfo);
        this.userInfo = userInfo;
    }


    public ConfigBean getConfigEntity() {
        if (configEntity == null) {
            configEntity = (ConfigBean) SerializeSpUtil.readObject(MobiSession.getInstance().getContext(), CONFIG);
        }
        return configEntity;
    }

    public void setConfigEntity(ConfigBean configEntity) {
        SerializeSpUtil.saveObject(MobiSession.getInstance().getContext(), CONFIG, configEntity);
        this.configEntity = configEntity;
    }

    public String getToken() {
        if (TextUtils.isEmpty(token)) {
            token = (String) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), TOKEN, "");
        }
        return token;
    }

    public void setToken(String token) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), TOKEN, token);
        this.token = token;
    }

    public boolean isRed() {
        isRed = (boolean) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), IS_RED, false);
        return isRed;
    }

    public void setRed(boolean red) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), IS_RED, red);
        this.isRed = red;
    }

    public String getNickname() {
        if (TextUtils.isEmpty(nickname)) {
            nickname = (String) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), NICKNAME, "");
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), NICKNAME, nickname);
        this.nickname = nickname;
    }

    public String getUserId() {
        if (TextUtils.isEmpty(userId)) {
            userId = (String) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), USER_ID, "");
        }
        return userId;
    }

    public void setUserId(String userId) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), USER_ID, userId);
        this.userId = userId;
    }

    public int getPoints() {
        points = (int) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), POINTS, 0);
        return points;
    }

    public void setPoints(int points) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), POINTS, points);
        this.points = points;
    }

    public float getCash() {
        cash = (float) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), CASH, 0f);
        return cash;
    }

    public void setCash(float cash) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), CASH, cash);
        this.cash = cash;
    }

    public int getTarget_step() {
        return target_step;
    }

    public void setTarget_step(int target_step) {
        this.target_step = target_step;
    }

    public String getImei() {
        if (TextUtils.isEmpty(imei)) {
            imei = (String) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), IMEI, "");
        }
        return imei;
    }

    public void setImei(String imei) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), IMEI, imei);
        this.imei = imei;
    }

    public String getMac() {
        if (TextUtils.isEmpty(mac)) {
            mac = (String) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), MAC, "");
        }
        return mac;
    }

    public void setMac(String mac) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), MAC, mac);
        this.mac = mac;
    }

    public String getAndroid_id() {
        if (TextUtils.isEmpty(android_id)) {
            android_id = (String) SerializeSpUtil.getParam(MobiSession.getInstance().getContext(), ANDROID_ID, "");
        }
        return android_id;
    }

    public void setAndroid_id(String android_id) {
        SerializeSpUtil.setParam(MobiSession.getInstance().getContext(), ANDROID_ID, android_id);
        this.android_id = android_id;
    }

    /**
     * 用于退出登录
     */
    public void clearInfo() {
        setUserInfo(null);
        setUserId("");
        setToken("");
        setCash(0);
        setPoints(0);

    }
}

package com.mobi.util;

import android.content.Context;

import com.mobi.R;
import com.mobi.global.MobiSession;

import java.lang.reflect.Field;

/**
 * @author zhousaito
 * @version 1.0
 * @date 2020/7/10 14:29
 * @Dec 略
 */
public class ResourceUtil {
    private static Context getContext() {
        return MobiSession.getInstance().getContext();
    }

    private static String getPackageName() {
        return getContext().getPackageName();
    }

    public static int getIdentifierId(String name) {
        return getContext().getResources().getIdentifier(name, "id", getPackageName());
    }

    public static int[] getResourceDeclareStyleableIntArray(String name) {
        Field[] allFields = R.styleable.class.getFields();
        for (Field field : allFields) {
            if (name.equals(field.getName())) {
                try {
                    return (int[]) field.get(R.styleable.class);
                } catch (IllegalAccessException ignore) {
                }
            }
        }

        return null;
    }

    public static final int getStyleableIntArrayIndex(String name) {
        try {
            if (getContext() == null)
                return 0;
            // use reflection to access the resource class
            Field field = Class.forName(getContext().getPackageName() + ".R$styleable").getDeclaredField(name);
            int ret = (Integer) field.get(null);
            return ret;
        } catch (Throwable t) {
        }
        return 0;
    }

    public static int getIdentifierLayout(String name) {
        return getContext().getResources().getIdentifier(name, "layout", getPackageName());
    }

    public static int getIdentifierColor(String name) {
        return getContext().getResources().getIdentifier(name, "color", getPackageName());
    }

    public static int getIdentifierAnim(String name) {
        return getContext().getResources().getIdentifier(name, "anim", getPackageName());
    }

    public static int getIdentifierDimen(String name) {
        if (getContext() == null)
            return 0;
        return getContext().getResources().getIdentifier(name, "dimen", getPackageName());
    }


    public static int getIdentifierString(String name) {
        return getContext().getResources().getIdentifier(name, "string", getPackageName());
    }

    public static int getIdentifierDrawable(String name) {
        return getContext().getResources().getIdentifier(name, "drawable", getPackageName());
    }

    public static int getIdentifierStyle(String name) {
        return getContext().getResources().getIdentifier(name, "style", getPackageName());
    }


}

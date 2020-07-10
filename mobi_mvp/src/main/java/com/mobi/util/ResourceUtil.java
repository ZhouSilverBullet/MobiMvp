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


    /**
     * 对于 context.getResources().getIdentifier 无法获取的数据 , 或者数组
     * <p>
     * 资源反射值
     *
     * @param name
     * @param type
     * @return
     * @paramcontext
     */

    private static Object getResourceId(Context context, String name, String type) {
        String className = context.getPackageName() + ".R";
        try {
            Class<?> cls = Class.forName(className);
            for (Class<?> childClass : cls.getClasses()) {
                String simple = childClass.getSimpleName();
                if (simple.equals(type)) {
                    for (Field field : childClass.getFields()) {
                        String fieldName = field.getName();
                        if (fieldName.equals(name)) {
                            System.out.println(fieldName);
                            return field.get(null);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * context.getResources().getIdentifier 无法获取到 styleable 的数据
     *
     * @param name
     * @return
     * @paramcontext
     */

    public static int getStyleable(String name) {
        return ((Integer) getResourceId(getContext(), name, "styleable")).intValue();

    }

    /**
     * 获取 styleable 的 ID 号数组
     *
     * @param name
     * @return
     * @paramcontext
     */

    public static int[] getStyleableArray(String name) {
        return (int[]) getResourceId(getContext(), name, "styleable");

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

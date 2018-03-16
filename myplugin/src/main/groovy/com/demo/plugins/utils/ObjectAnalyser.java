package com.demo.plugins.utils;

import com.android.annotations.NonNull;
import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by liushuo on 2018/3/16.
 */

public class ObjectAnalyser {
    public static void analyseObject(@NonNull Object object) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(object);
        
        System.out.println("类" + object.getClass().getSimpleName());
        
        Class clazz = object.getClass();
        if (clazz == Object.class) return;
        
        try {
            Method method = clazz.getDeclaredMethod("toString");
            if (method != null) {
                System.out.println(method.invoke(object));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(" ");
        
        Field[] fields = clazz.getFields();
        if (fields != null) {
            analyseFields(object, fields);
        }
        
        Method[] methods = clazz.getMethods();
        if (methods != null) {
            analyseMethods(object, methods);
        }
        
        
    }
    
    public static void analyseFields(@NonNull Object object, @NonNull Field... fields) {
        Preconditions.checkNotNull(fields);
        Preconditions.checkNotNull(object);
        
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            boolean isFinal = Modifier.isFinal(modifiers);
            boolean isPublic = Modifier.isPublic(modifiers);
            boolean isStatic = Modifier.isStatic(modifiers);
            
            if (!isFinal && !isStatic && isPublic) {
                //public 的成员变量
                System.out.println(field.getName() + ":");
                try {
                    field.setAccessible(true);
                    analyseObject(field.get(object));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        }
    }
    
    public static void analyseMethods(@NonNull Object object, @NonNull Method... methods) {
        Preconditions.checkNotNull(methods);
        Preconditions.checkNotNull(object);
        
        for (Method method : methods) {
            String name = method.getName();
            if (!name.startsWith("get")) continue;
            
            int modifiers = method.getModifiers();
            boolean isPrivate = Modifier.isPrivate(modifiers);
            boolean isStatic = Modifier.isStatic(modifiers);
            if (isPrivate || isStatic) continue;
            
            try {
                Object returnValue = method.invoke(object);
                System.out.println("方法" + name);
                analyseObject(returnValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
}

package com.demo.plugins.casedemo

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project;

/**
 * Created by liushuo on 2018/3/4.
 * 动态修改AlertDialog 子类的继承关系，使之继承DDAlertDialog
 */

public class InjectCase1 {
    private static final ClassPool sClassPool = ClassPool.getDefault()

    public static void inject(String path, Project project) {
        sClassPool.appendClassPath(path)

        project.android.bootClasspath.each {
            sClassPool.appendClassPath((String) it.absolutePath)
        }

        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                if (file.getName().equals("Test.class")) {

                    CtClass ctClass = sClassPool.getCtClass("com.javasist.liushuo.javasistdemo.Test")

                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }

                    CtMethod ctMethod = ctClass.getDeclaredMethod("test")

                    String str = """
        android.util.Log.d("Test", "insert before");
        """

                    ctMethod.insertBefore(str)
                    ctClass.writeFile(path)
                    ctClass.detach()

                    println("path：" + path + ",file parent:" + file.getParent())

                }
            }
        }

    }
}

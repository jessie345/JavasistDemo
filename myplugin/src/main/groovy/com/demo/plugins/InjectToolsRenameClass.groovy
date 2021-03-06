package com.demo.plugins

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project;

/**
 * Created by liushuo on 2018/3/4.
 * 用重命名的方式创建新类
 */

public class InjectToolsRenameClass {
    private static final ClassPool sClassPool = ClassPool.getDefault()

    public static void inject(String path, Project project) {
        sClassPool.appendClassPath(path)

        project.android.bootClasspath.each {
            sClassPool.appendClassPath((String) it.absolutePath)
        }

        println("开始注入代码")

        File dir = new File(path)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                if (file.getName().equals("Test.class")) {

                    println("文件夹path:" + path)
                    println("类文件的全部路径:" + file.getAbsolutePath());

                    CtClass ctClass = sClassPool.getCtClass("com.javasist.liushuo.javasistdemo.Test")

                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }

                    try {
                        CtClass checkCtClass = sClassPool.getCtClass("com.javasist.liushuo.javasistdemo.TestAlias1")
                        checkCtClass.defrost()
                    } catch (Exception e) {
                        e.printStackTrace()
                    }

                    ctClass.setName("com.javasist.liushuo.javasistdemo.TestAlias1")

                    CtClass oritinalTestClass = sClassPool.getCtClass("com.javasist.liushuo.javasistdemo.Test");

                    ctClass.writeFile(path)

                    ctClass.detach()
                    oritinalTestClass.detach()
                }
            }
        }

    }
}

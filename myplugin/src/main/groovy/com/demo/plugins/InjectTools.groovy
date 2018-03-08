package com.demo.plugins

import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.gradle.api.Project;

/**
 * Created by liushuo on 2018/3/4.
 */

public class InjectTools {
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

                    println("获取CtClass 实例：" + ctClass)

                    if (ctClass.isFrozen()) {
                        ctClass.defrost()
                    }

                    CtMethod ctMethod = ctClass.getDeclaredMethod("test")

                    println("获取CtMethod 实例：" + ctMethod)


                    String str = """
        android.util.Log.d("Test", "insert before");
        """

                    ctMethod.insertBefore(str)
                    ctClass.writeFile(path)
                    ctClass.detach()

                    println("path：" + path+",file parent:"+file.getParent())

                }
            }
        }

    }
}

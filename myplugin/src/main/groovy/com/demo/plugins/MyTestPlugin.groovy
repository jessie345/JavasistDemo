package com.demo.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.api.ApplicationVariant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.platform.base.Variant


class MyPuglinTestClass {
    def str = ""
}


public class MyTestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(new MyPreDexTransform(project))

        project.extensions.create("testCreatJavaConfig", MyPuglinTestClass)

        if (project.plugins.hasPlugin(AppPlugin)) {
            def config = project.extensions.getByName("testCreatJavaConfig")

            android.applicationVariants.all { variant ->

                def generateBuildConfigTask = variant.getGenerateBuildConfig()

                def createTask = project.task(variant.name + "_createTest")
                createTask.doLast {
                    createJavaTest(variant, config)
                }

//                createTask.dependsOn generateBuildConfigTask
                generateBuildConfigTask.finalizedBy createTask
            }
        }

    }

    def void createJavaTest(ApplicationVariant variant, MyPuglinTestClass config) {

        String packageName = variant.generateBuildConfig.buildConfigPackageName

        //要生成的内容
        def content = """package ${packageName};

                        /**
                         * Created by 刘镓旗 on 2017/8/30.
                         */

                        public class MyPlguinTestClass {
                            public static final String str = "${config.str}";
                        }
                        """

        def outputDir = new File(variant.generateBuildConfig.sourceOutputDir, packageName.replace(".","/"))
        def javaFile = new File(outputDir, "MyPlguinTestClass.java")

        javaFile.write(content, 'UTF-8')
    }

}
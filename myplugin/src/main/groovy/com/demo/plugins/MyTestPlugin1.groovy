package com.demo.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.demo.plugins.utils.ObjectAnalyser;
import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * Created by liushuo on 2018/3/16.
 */

public class MyTestPlugin1 implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        def extension = project.extensions.findByType(TestedExtension)

        if (extension instanceof AppExtension) {
            println("project extension:${project.name} is AppExtension")
        }

        if (extension instanceof LibraryExtension) {
            println("project extension:${project.name} is LibraryExtension")
        }

        println("start parameter task names:" + project.gradle.startParameter.getTaskNames())


        if (extension instanceof AppExtension) {
            AppExtension appExtension = extension
            appExtension.applicationVariants.all { variant ->
                // String,int,Object,boolean,map
                String content = "variant.description:" + variant.description + ",\n" +
                        "variant.name:" + variant.name + ",\n" +
                        "variant.versionName:" + variant.versionName + ",\n" +
                        "variant.baseName:" + variant.baseName + ",\n" +
                        "variant.versionCode:" + variant.versionCode + ",\n" +
                        "variant.applicationId:" + variant.applicationId + ",\n" +
                        "variant.dirName:" + variant.dirName + ",\n" +
                        "variant.flavorName:" + variant.flavorName + ",\n" +
                        "variant.outputsAreSigned:" + variant.outputsAreSigned + ",\n" +
                        "variant.properties:" + variant.properties + ",\n" +
                        "variant.signingReady:" + variant.signingReady + ",\n"

                println(content)

//                println("-----------------------------------------------")
//
//                // List<SourceProvider>
//                variant.sourceSets.each { provider ->
//                    println("provider.properties:" + provider.properties)
//
//                    provider.metaPropertyValues.each { value ->
//                    }
//                }
//
//                // SigningConfig
//                variant.signingConfig
//
//                // MergeSourceSetFolders
//                variant.mergeAssets
//
//                // ProductFlavor
//                variant.mergedFlavor
//
//                // MergeResources
//                variant.mergeResources
//
//                // MetaClass
//                variant.metaClass
//
//                // List<PropertyValue>
//                variant.metaPropertyValues
//
//                // File
//                variant.mappingFile
//
//                // JavaCompileOptions
//                variant.javaCompileOptions
//
//                // FileCollection
//                variant.dataBindingDependencyArtifacts
//
//                //Set<String>
//                variant.compatibleScreens
//
//                // buildType
//                variant.buildType
//
//                // Configuration
//                variant.compileConfiguration
//                variant.annotationProcessorConfiguration
//                variant.runtimeConfiguration

            }
        }
    }

}

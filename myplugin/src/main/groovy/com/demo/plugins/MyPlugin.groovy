package com.demo.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project


public class MyPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.task('hello'){
            doLast {
                println("hello from the MyPlugin")
            }
        }
    }
}
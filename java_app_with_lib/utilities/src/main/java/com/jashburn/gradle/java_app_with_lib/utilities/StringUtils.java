/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.jashburn.gradle.java_app_with_lib.utilities;

import com.jashburn.gradle.java_app_with_lib.list.LinkedList;

public class StringUtils {
    public static String join(LinkedList source) {
        return JoinUtils.join(source);
    }

    public static LinkedList split(String source) {
        return SplitUtils.split(source);
    }
}

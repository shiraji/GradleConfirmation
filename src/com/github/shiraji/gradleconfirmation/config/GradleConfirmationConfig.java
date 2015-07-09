package com.github.shiraji.gradleconfirmation.config;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

public class GradleConfirmationConfig {

    public static final String IS_ENABLE_CONF_KEY = "com.github.shiraji.gradleconfirmation.isenable";

    public static boolean isSelected(Project project) {
        return PropertiesComponent.getInstance(project).getBoolean(IS_ENABLE_CONF_KEY, true);
    }

    public static void setSelected(Project project, boolean isEnable) {
        PropertiesComponent.getInstance(project).setValue(IS_ENABLE_CONF_KEY, String.valueOf(isEnable));
    }
}

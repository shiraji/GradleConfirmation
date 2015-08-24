package com.github.shiraji.gradleconfirmation.config;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GradleConfirmationConfig {

    public static final String IS_ENABLE_CONF_KEY = "com.github.shiraji.gradleconfirmation.isenable";
    public static final String DISABLE_TASKS_LIST_KEY = "com.github.shiraji.gradleconfirmation.config.GradleConfirmationConfig.DISABLE_TASKS_LIST_KEY";

    public static boolean isSelected(Project project) {
        return PropertiesComponent.getInstance(project).getBoolean(IS_ENABLE_CONF_KEY, true);
    }

    public static void setSelected(Project project, boolean isEnable) {
        PropertiesComponent.getInstance(project).setValue(IS_ENABLE_CONF_KEY, String.valueOf(isEnable));
    }

    public static String[] getDisableTaskList(Project project) {
        return PropertiesComponent.getInstance(project).getValues(DISABLE_TASKS_LIST_KEY);
    }

    public static void setDisableTaskList(Project project, List<String>
            disableTaskList) {
        if (disableTaskList.size() > 0) {
            setDisableTaskList(project, disableTaskList.toArray(new String[0]));
        } else {
            clearDisableTaskList(project);
        }
    }

    public static void setDisableTaskList(Project project, String[]
            disableTaskList) {
        PropertiesComponent.getInstance(project).setValues(DISABLE_TASKS_LIST_KEY, disableTaskList);
    }

    public static void clearDisableTaskList(Project project) {
        PropertiesComponent.getInstance(project).setValues(DISABLE_TASKS_LIST_KEY, null);
    }

    public static void addDisableTaskList(Project project, String
            disableTaskName) {
        String[] taskArray = getDisableTaskList(project);
        List<String> taskList;
        if (taskArray == null || taskArray.length <= 0) {
            taskList = new ArrayList<String>();
        } else {
            taskList = new ArrayList<String>(Arrays.asList(taskArray));
        }
        taskList.add(disableTaskName);
        setDisableTaskList(project, taskList.toArray(new String[0]));
    }

    public static boolean isDistableTaskList(Project project, List<String> taskList) {
        String[] disableTaskArray = getDisableTaskList(project);
        if (disableTaskArray == null || disableTaskArray.length < taskList
                .size()) {
            return false;
        }
        List<String> disableTaskList = new ArrayList<String>(Arrays.asList
                (disableTaskArray));
        return disableTaskList.containsAll(taskList);
    }
}

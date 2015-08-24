package com.github.shiraji.gradleconfirmation.task;

import com.github.shiraji.gradleconfirmation.config.GradleConfirmationConfig;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.project.Project;

import org.jetbrains.plugins.gradle.service.task.GradleTaskManagerExtension;
import org.jetbrains.plugins.gradle.settings.GradleExecutionSettings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GradleConfirmationTaskManager implements GradleTaskManagerExtension {
    private static final Logger LOG = Logger.getInstance(GradleConfirmationTaskManager.class.getName());

    private static final boolean STOP_EXECUTE = true;
    private static final boolean START_EXECUTE = false;

    @Override
    public boolean executeTasks(ExternalSystemTaskId externalSystemTaskId, List<String> taskNames, String s,
                                GradleExecutionSettings gradleExecutionSettings, List<String> list1,
                                List<String> list2, String s1,
                                ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener)
            throws ExternalSystemException {
        LOG.debug("executeTasks");

        Project project = externalSystemTaskId.findProject();

        if (project == null || !isEnablePlugin(project)) {
            return START_EXECUTE;
        }

        if (isDisableTasks(project, taskNames)) {
            showDisableMessage(externalSystemTaskId,
                    externalSystemTaskNotificationListener);
            return START_EXECUTE;
        }

        String taskListNames = createTaskListNames(taskNames);
        JCheckBox jCheckBox = new JCheckBox(String.format("Do not show dialog for '%s'", taskNames.get(0)));

        int optionDialog = showConfirmationDialog(taskNames, taskListNames, jCheckBox);

        if (optionDialog == JOptionPane.YES_OPTION) {
            addToDisableList(taskNames, project, jCheckBox);
            return START_EXECUTE;
        } else {
            showCancelMessage(externalSystemTaskId, externalSystemTaskNotificationListener, taskListNames);
            return STOP_EXECUTE;
        }
    }

    private int showConfirmationDialog(List<String> taskNames, String taskListNames, JCheckBox jCheckBox) {
        String questionMessage = String.format("Are you sure you want to run %s?", taskListNames);
        List<JComponent> componentList = new ArrayList<JComponent>();
        componentList.add(new JLabel(questionMessage));
        if (taskNames.size() == 1) {
            componentList.add((JComponent) Box.createVerticalStrut(20));
            componentList.add(jCheckBox);
        }

        return JOptionPane.showOptionDialog(null, componentList.toArray(new JComponent[0]), "Gradle Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Run", "Cancel"}, null);
    }

    private void addToDisableList(List<String> taskNames, Project project, JCheckBox jCheckBox) {
        if (jCheckBox.isSelected()) {
            GradleConfirmationConfig.addDisableTaskList(project, taskNames.get(0));
        }
    }

    private boolean isEnablePlugin(Project project) {
        return GradleConfirmationConfig.isSelected(project);
    }

    private boolean isDisableTasks(Project project, List<String> taskList) {
        return GradleConfirmationConfig.isDistableTaskList(project, taskList);
    }

    private void showDisableMessage(ExternalSystemTaskId externalSystemTaskId,
                                    ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener) {
        externalSystemTaskNotificationListener.onTaskOutput(externalSystemTaskId,
                "To enable Gradle Confirmation dialog, go to Tools > Gradle Confirmation > Edit Disable Tasks...\n", true);
    }

    private void showCancelMessage(ExternalSystemTaskId externalSystemTaskId,
                                   ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener,
                                   String taskListNames) {
        externalSystemTaskNotificationListener.onTaskOutput(externalSystemTaskId,
                String.format("Stop running %s\n", taskListNames), true);
    }

    private String createTaskListNames(List<String> taskNames) {
        StringBuilder sb = new StringBuilder();
        int size = taskNames.size();
        for (int i = 0; i < size; i++) {
            appendSingleQuote(sb);
            sb.append(taskNames.get(i));
            appendSingleQuote(sb);
            appendCommaWithCondition(sb, i + 1 < size);
        }
        return sb.toString();
    }

    private void appendSingleQuote(StringBuilder sb) {
        sb.append("'");
    }

    private void appendCommaWithCondition(StringBuilder sb, boolean flag) {
        if (flag) {
            sb.append(", ");
        }
    }

    @Override
    public boolean cancelTask(ExternalSystemTaskId externalSystemTaskId,
                              ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener)
            throws ExternalSystemException {
        LOG.debug("cancelTasks");

        return false;
    }
}

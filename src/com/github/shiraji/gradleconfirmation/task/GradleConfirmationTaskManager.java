package com.github.shiraji.gradleconfirmation.task;

import com.github.shiraji.gradleconfirmation.config.GradleConfirmationConfig;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import com.intellij.openapi.project.Project;
import org.jetbrains.plugins.gradle.service.task.GradleTaskManagerExtension;
import org.jetbrains.plugins.gradle.settings.GradleExecutionSettings;

import javax.swing.JOptionPane;
import java.util.List;

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
        if (project != null && !isEnablePlugin(project)) {
            return START_EXECUTE;
        }

        String taskListNames = createTaskListNames(taskNames);
        String questionMessage = String.format("Are you sure you want to run %s?", taskListNames);
        int optionDialog = JOptionPane.showOptionDialog(null, questionMessage, "Gradle Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[]{"Yes", "No"}, null);

        if (optionDialog == JOptionPane.YES_OPTION) {
            return START_EXECUTE;
        } else {
            showCancelMessage(externalSystemTaskId, externalSystemTaskNotificationListener, taskListNames);
            return STOP_EXECUTE;
        }
    }

    private boolean isEnablePlugin(Project project) {
        return GradleConfirmationConfig.isSelected(project);
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

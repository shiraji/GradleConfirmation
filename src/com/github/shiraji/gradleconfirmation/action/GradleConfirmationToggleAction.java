package com.github.shiraji.gradleconfirmation.action;

import com.github.shiraji.gradleconfirmation.config.GradleConfirmationConfig;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;

/**
 * Created by Shiraji on 15/07/09.
 */
public class GradleConfirmationToggleAction extends ToggleAction {
    @Override
    public boolean isSelected(AnActionEvent anActionEvent) {
        GradleConfirmationConfig config = GradleConfirmationConfig.getInstance(anActionEvent.getProject());
        return config.isEnablePlugin();
    }

    @Override
    public void setSelected(AnActionEvent anActionEvent, boolean b) {
        GradleConfirmationConfig config = GradleConfirmationConfig.getInstance(anActionEvent.getProject());
        config.setDisablePlugin(b);
    }
}

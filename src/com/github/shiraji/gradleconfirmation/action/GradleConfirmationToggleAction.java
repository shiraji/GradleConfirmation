package com.github.shiraji.gradleconfirmation.action;

import com.github.shiraji.gradleconfirmation.config.GradleConfirmationConfig;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;

public class GradleConfirmationToggleAction extends ToggleAction {

    @Override
    public boolean isSelected(AnActionEvent anActionEvent) {
        return GradleConfirmationConfig.isSelected(anActionEvent.getProject());
    }

    @Override
    public void setSelected(AnActionEvent anActionEvent, boolean b) {
        GradleConfirmationConfig.setSelected(anActionEvent.getProject(), b);
    }
}

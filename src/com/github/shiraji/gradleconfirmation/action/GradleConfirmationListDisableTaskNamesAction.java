package com.github.shiraji.gradleconfirmation.action;

import com.github.shiraji.gradleconfirmation.config.GradleConfirmationConfig;
import com.github.shiraji.gradleconfirmation.view.GradleConfirmationDisableTaskListForm;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.StripeTable;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import com.intellij.ui.ToolbarDecorator;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

public class GradleConfirmationListDisableTaskNamesAction extends AnAction {

    final DefaultTableModel model = new DefaultTableModel(new String[]{"Task Names"}, 0);
    Logger mLogger = Logger.getLogger
            (GradleConfirmationListDisableTaskNamesAction.class.getName());
    GradleConfirmationDisableTaskListForm mGradleConfirmationDisableTaskListForm = new
            GradleConfirmationDisableTaskListForm();

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        initModel(project);
        if (new GradleConfirmationDisableTaskListDialog(project).showAndGet()) {
            saveToConfig(project);
        }
    }

    private void initModel(Project project) {
        clearModel();
        addRows(project);
    }

    private void addRows(Project project) {
        String[] disableTaskList = GradleConfirmationConfig.getDisableTaskList(project);
        if (disableTaskList == null) {
            return;
        }

        for (String distableTask : disableTaskList) {
            model.addRow(new String[]{distableTask});
        }
    }

    private void clearModel() {
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model.removeRow(0);
        }
    }

    private void saveToConfig(Project project) {
        List<String> taskList = new ArrayList<String>();
        int rowCount = model.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            String taskName = (String) model.getValueAt(i, 0);
            if (taskName != null && taskName.length() > 0) {
                taskList.add(taskName);
            }
        }

        GradleConfirmationConfig.setDisableTaskList(project, taskList);
    }

    class GradleConfirmationDisableTaskListDialog extends DialogWrapper {
        final StripeTable table = new StripeTable(model);

        protected GradleConfirmationDisableTaskListDialog(@Nullable Project project) {
            super(project);
            init();
            setTitle("Disable Task List - Gradle Confirmation");
            setSize(300, 500);
        }

        @Nullable
        @Override
        protected JComponent createCenterPanel() {
            ToolbarDecorator decorator = ToolbarDecorator.createDecorator(table).disableUpDownActions();
            initDecorator(decorator);
            initColumnWidth(table);
            mGradleConfirmationDisableTaskListForm.mDisableTaskListPanel.add(decorator.createPanel(), BorderLayout.CENTER);
            return mGradleConfirmationDisableTaskListForm.mRootPanel;
        }

        private void initDecorator(ToolbarDecorator decorator) {
            decorator.setAddAction(new AnActionButtonRunnable() {
                @Override
                public void run(AnActionButton anActionButton) {
                    model.addRow(new String[]{""});
                    table.setModel(model);
                    table.editCellAt(model.getRowCount() - 1, 0);
                }
            }).setRemoveAction(new AnActionButtonRunnable() {
                @Override
                public void run(AnActionButton anActionButton) {
                    int[] selectedColumns = table.getSelectedRows();
                    Arrays.sort(selectedColumns);
                    for (int i = selectedColumns.length - 1; i >= 0; i--) {
                        model.removeRow(selectedColumns[i]);
                        table.setModel(model);
                    }
                }
            });
        }

        private void initColumnWidth(StripeTable table) {
            DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(300);
        }

        @NotNull
        protected Action[] createActions() {
            return new Action[]{this.getOKAction(), this.getCancelAction()};
        }

    }
}

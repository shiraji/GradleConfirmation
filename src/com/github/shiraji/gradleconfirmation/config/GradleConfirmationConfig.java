package com.github.shiraji.gradleconfirmation.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

@State(
        name = "GradleConfirmationConfig",
        reloadable = true,
        storages = {
                @Storage(id = "default", file = "$PROJECT_FILE$"),
                @Storage(id = "dir",
                        file = "$PROJECT_CONFIG_DIR$/gradle_confirmation_plugin.xml",
                        scheme = StorageScheme.DIRECTORY_BASED)
        }
)
public class GradleConfirmationConfig implements PersistentStateComponent<GradleConfirmationConfig> {

    private boolean mIsEnablePlugin = true;

    @Nullable
    @Override
    public GradleConfirmationConfig getState() {
        return this;
    }

    @Override
    public void loadState(GradleConfirmationConfig config) {
        XmlSerializerUtil.copyBean(config, this);
    }

    @Nullable
    public static GradleConfirmationConfig getInstance(Project project) {
        return ServiceManager.getService(project, GradleConfirmationConfig.class);
    }

    public boolean isEnablePlugin() {
        return mIsEnablePlugin;
    }

    public void setDisablePlugin(boolean isDisablePlugin) {
        mIsEnablePlugin = isDisablePlugin;
    }
}

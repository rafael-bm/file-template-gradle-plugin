package uk.co.mulecode.filetemplate.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import uk.co.mulecode.filetemplate.plugin.model.TemplateConfig;

public class FileTemplatePlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {

        target.getExtensions().create(
                "templateConfig",
                TemplateConfig.class
        );

        target.getTasks().create(
                "templateFile",
                FileTemplateTask.class
        );
    }
}

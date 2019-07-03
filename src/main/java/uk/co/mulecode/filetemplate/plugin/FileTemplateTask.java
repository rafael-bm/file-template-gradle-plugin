package uk.co.mulecode.filetemplate.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import uk.co.mulecode.filetemplate.plugin.model.TemplateConfig;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.property.PropertySet;
import uk.co.mulecode.filetemplate.template.TemplateService;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

public class FileTemplateTask extends DefaultTask {

    @Input
    private String propertyFile = "";
    @Input
    private List<TemplateData> configDetails = new ArrayList<>();

    @TaskAction
    public void templateFile() throws Exception {

        var config = getProject().getExtensions().findByType(
                TemplateConfig.class
        );

        var propertySet = new PropertySet();
        propertySet.setPropertyFile(propertyFile);
        propertySet.setConfigDetails(configDetails);
        if (nonNull(config)) {
            propertySet.setPropertyFileDefault(config.getPropertyFile());
            propertySet.setConfigDetailsDefault(config.getConfigDetails());
        }
        propertySet.initialize();

        var templateService = new TemplateService(propertySet);
        templateService.templateFile();
    }

    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public List<TemplateData> getConfigDetails() {
        return configDetails;
    }

    public void setConfigDetails(List<TemplateData> configDetails) {
        this.configDetails = configDetails;
    }

}
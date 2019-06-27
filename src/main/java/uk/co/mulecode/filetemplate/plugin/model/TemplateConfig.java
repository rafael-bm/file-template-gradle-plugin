package uk.co.mulecode.filetemplate.plugin.model;

import org.gradle.api.tasks.Input;

import java.util.ArrayList;
import java.util.List;

public class TemplateConfig {

    @Input
    private String propertyFile;
    @Input
    private List<TemplateData> configDetails = new ArrayList<>();

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

    @Override
    public String toString() {
        return "TemplateConfig{" +
                "propertyFile='" + propertyFile + '\'' +
                ", configDetails=" + configDetails +
                '}';
    }
}

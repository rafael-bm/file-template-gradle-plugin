package uk.co.mulecode.filetemplate.plugin;

import org.apache.commons.lang3.StringUtils;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import uk.co.mulecode.filetemplate.file.MulecodeFileUtils;
import uk.co.mulecode.filetemplate.interpreter.FileInterpreter;
import uk.co.mulecode.filetemplate.interpreter.FileInterpreterFactory;
import uk.co.mulecode.filetemplate.plugin.model.TemplateConfig;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.template.TemplateService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        var valueFile = loadValueFile(config);

        var fileConfigs = loadConfigDetails(config);

        getLogger().info("Template configurations size: {}", fileConfigs.size());

        var templateService = new TemplateService(valueFile);
        templateService.templateFile(fileConfigs);
    }

    private List<TemplateData> loadConfigDetails(TemplateConfig config) {

        List<TemplateData> fileConfigs;

        if (!getConfigDetails().isEmpty()) {

            fileConfigs = getConfigDetails();

        } else {

            fileConfigs = config.getConfigDetails();
        }
        return fileConfigs;
    }

    private FileInterpreter loadValueFile(TemplateConfig config) throws IOException {

        var fileLoader = new MulecodeFileUtils();
        String contentLoaded;
        String fileType;

        if (StringUtils.isNotBlank(propertyFile)) {

            contentLoaded = fileLoader.loadFileAsString(
                    propertyFile
            );
            fileType = fileLoader.fileExtension(
                    propertyFile
            );

        } else {

            contentLoaded = fileLoader.loadFileAsString(
                    config.getPropertyFile()
            );
            fileType = fileLoader.fileExtension(
                    config.getPropertyFile()
            );

        }

        var valueFile = new FileInterpreterFactory()
                .get(fileType);

        valueFile.loadContent(contentLoaded);

        return valueFile;
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
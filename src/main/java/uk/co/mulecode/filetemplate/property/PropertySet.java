package uk.co.mulecode.filetemplate.property;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import uk.co.mulecode.filetemplate.file.MulecodeFileUtils;
import uk.co.mulecode.filetemplate.interpreter.FileInterpreter;
import uk.co.mulecode.filetemplate.interpreter.FileInterpreterFactory;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.plugin.model.TemplateDataItem;

import java.util.List;
import java.util.Objects;

public class PropertySet {

    private FileInterpreter valueFile;
    private FileInterpreter valueFileDefault;

    private String propertyFile;
    private List<TemplateData> configDetails;
    private String propertyFileDefault;
    private List<TemplateData> configDetailsDefault;

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }

    public void setConfigDetails(List<TemplateData> configDetails) {
        this.configDetails = configDetails;
    }

    public void setPropertyFileDefault(String propertyFileDefault) {
        this.propertyFileDefault = propertyFileDefault;
    }

    public void setConfigDetailsDefault(List<TemplateData> configDetailsDefault) {
        this.configDetailsDefault = configDetailsDefault;
    }

    public void initialize() {

        this.valueFile = loadValueFile(propertyFile);
        this.valueFileDefault = loadValueFile(propertyFileDefault);
    }

    public List<TemplateData> getTemplateDetails() {

        if (CollectionUtils.isNotEmpty(configDetails)) {

            return configDetails;

        } else {

            return configDetailsDefault;
        }
    }

    public String getReplaceValue(TemplateDataItem replace) {

        if (Objects.nonNull(valueFile)) {

            var valueFileProp = valueFile.getProperty(
                    replace.getPropertyPath()
            );

            if (StringUtils.isNotBlank(valueFileProp)) {

                return valueFileProp;
            }

        }

        if (StringUtils.isNotBlank(replace.getDefaultValue())) {

            return replace.getDefaultValue();
        }

        if (Objects.nonNull(valueFileDefault)) {

            var valueFilePropDefault = valueFileDefault.getProperty(
                    replace.getPropertyPath()
            );

            if (StringUtils.isNotBlank(valueFilePropDefault)) {

                return valueFilePropDefault;
            }
        }

        return "";
    }

    private FileInterpreter loadValueFile(String propertyFile) {

        if (StringUtils.isBlank(propertyFile)) {
            return null;
        }

        var fileLoader = new MulecodeFileUtils();

        var contentLoaded = fileLoader.loadFileAsString(
                propertyFile
        );

        var fileType = fileLoader.fileExtension(
                propertyFile
        );

        var valueFileInterpreter = new FileInterpreterFactory()
                .get(fileType);

        valueFileInterpreter.loadContent(contentLoaded);

        return valueFileInterpreter;
    }


}

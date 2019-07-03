package uk.co.mulecode.filetemplate.template;

import org.apache.commons.lang3.StringUtils;
import uk.co.mulecode.filetemplate.file.MulecodeFileUtils;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.plugin.model.TemplateDataItem;
import uk.co.mulecode.filetemplate.property.PropertySet;

import java.io.IOException;

public class TemplateService {

    private final PropertySet propertySet;

    public TemplateService(PropertySet propertySet) {
        this.propertySet = propertySet;
    }

    public void templateFile() throws IOException {

        for (TemplateData template : propertySet.getTemplateDetails()) {

            templateFile(template);
        }
    }

    public void templateFile(TemplateData template) throws IOException {

        var templateFile = new MulecodeFileUtils().loadFileAsString(
                template.getTemplateFileInput()
        );

        for (TemplateDataItem replace : template.getReplaces()) {

            templateFile = StringUtils.replace(
                    templateFile,
                    replace.getPlaceHolder(),
                    propertySet.getReplaceValue(replace)
            );

        }

        new MulecodeFileUtils().writeStringToFile(
                template.getTemplateFileOutput(),
                templateFile
        );

    }
}

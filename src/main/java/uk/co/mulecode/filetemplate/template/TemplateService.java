package uk.co.mulecode.filetemplate.template;

import org.apache.commons.lang3.StringUtils;
import uk.co.mulecode.filetemplate.file.MulecodeFileUtils;
import uk.co.mulecode.filetemplate.interpreter.FileInterpreter;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.plugin.model.TemplateDataItem;

import java.io.IOException;
import java.util.List;

public class TemplateService {

    private final FileInterpreter valueFile;

    public TemplateService(FileInterpreter valueFile) {
        this.valueFile = valueFile;
    }

    public void templateFile(List<TemplateData> templates) throws IOException {

        for (TemplateData template : templates) {

            templateFile(template);
        }
    }

    public void templateFile(TemplateData template) throws IOException {

        var templateFile = new MulecodeFileUtils().loadFileAsString(
                template.getTemplateFileInput()
        );

        for (TemplateDataItem replace : template.getReplaces()) {

            var valueToReplace = valueFile.getProperty(
                    replace.getPropertyPath()
            );

            templateFile = StringUtils.replace(
                    templateFile,
                    replace.getPlaceHolder(),
                    valueToReplace
            );

        }

        new MulecodeFileUtils().writeStringToFile(
                template.getTemplateFileOutput(),
                templateFile
        );

    }
}

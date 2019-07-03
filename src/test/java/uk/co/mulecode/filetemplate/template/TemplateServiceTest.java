package uk.co.mulecode.filetemplate.template;

import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import uk.co.mulecode.filetemplate.interpreter.impl.JsonFileInterpreter;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.plugin.model.TemplateDataItem;
import uk.co.mulecode.filetemplate.property.PropertySet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TemplateServiceTest {

    @Test
    public void templateFile() throws Exception {

        var destinationFilePath = givenDestinationFilePath();
        var templateFilePath = "src/test/resources/templateSample.json";
        var oneTemplateConfiguration = givenMainValueFileConfig(
                templateFilePath,
                destinationFilePath,
                List.of(
                        new TemplateDataItem("{{COUNTRY}}", "uk.country"),
                        new TemplateDataItem("{{CITY}}", "uk.city")
                )
        );

        var valueFilePath = "src/test/resources/valuesSample.json";

        PropertySet propertySet = new PropertySet();
        propertySet.setPropertyFile(valueFilePath);
        propertySet.setConfigDetails(List.of(oneTemplateConfiguration));
        propertySet.initialize();

        whenExecuteTemplateFile(propertySet);

        var createdFile = thenCreatedFileMustExists(destinationFilePath);
        thenPropertyMustExists(createdFile, "country", "united kingdom");
        thenPropertyMustExists(createdFile, "city", "london");
    }

    private void thenPropertyMustExists(Path createdFile, String propertyPath, String expectedValue) throws IOException {

        var jsonInterpreterReader = loadOneJsonInterpreter(createdFile.toString());
        MatcherAssert.assertThat(jsonInterpreterReader.getProperty(propertyPath), is(expectedValue));
    }

    private void whenExecuteTemplateFile(PropertySet propertySetService) throws IOException {

        var templateService = new TemplateService(propertySetService);
        templateService.templateFile();
    }

    private JsonFileInterpreter loadOneJsonInterpreter(String jsonFilePath) throws IOException {

        var valueFileFileInterpreter = new JsonFileInterpreter();
        valueFileFileInterpreter.loadContent(
                FileUtils.readFileToString(Paths.get(jsonFilePath).toFile(), "UTF-8")
        );
        return valueFileFileInterpreter;
    }

    private String givenDestinationFilePath() throws IOException {

        return File.createTempFile(
                "finalFile",
                Long.toString(System.nanoTime())
        ).getAbsolutePath();
    }

    private TemplateData givenMainValueFileConfig(String templateFilePath, String destinationFilePath, List<TemplateDataItem> templates) {

        return new TemplateData(
                templateFilePath,
                destinationFilePath,
                templates
        );
    }

    private Path thenCreatedFileMustExists(String tempFilePath) {

        var createdFile = Paths.get(tempFilePath);
        assertThat(Files.exists(createdFile), is(true));
        return createdFile;
    }
}
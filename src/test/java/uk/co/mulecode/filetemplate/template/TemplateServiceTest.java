package uk.co.mulecode.filetemplate.template;

import org.apache.commons.io.FileUtils;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import uk.co.mulecode.filetemplate.interpreter.impl.JsonFileInterpreter;
import uk.co.mulecode.filetemplate.plugin.model.TemplateData;
import uk.co.mulecode.filetemplate.plugin.model.TemplateDataItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

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
                Map.of(
                        "{{COUNTRY}}", "uk.country",
                        "{{CITY}}", "uk.city"
                )
        );

        var valueFilePath = "src/test/resources/valuesSample.json";

        whenExecuteTemplateFile(
                oneTemplateConfiguration,
                valueFilePath
        );

        var createdFile = thenCreatedFileMustExists(destinationFilePath);
        thenPropertyMustExists(createdFile, "country", "united kingdom");
        thenPropertyMustExists(createdFile, "city", "london");
    }

    private void thenPropertyMustExists(Path createdFile, String propertyPath, String expectedValue) throws IOException {

        var jsonInterpreterReader = loadOneJsonInterpreter(createdFile.toString());
        MatcherAssert.assertThat(jsonInterpreterReader.getProperty(propertyPath), is(expectedValue));
    }

    private void whenExecuteTemplateFile(TemplateData oneTemplateConfiguration, String valueFilePath) throws IOException {

        var valueFileFileInterpreter = loadOneJsonInterpreter(valueFilePath);
        var templateService = new TemplateService(valueFileFileInterpreter);
        templateService.templateFile(oneTemplateConfiguration);
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

    private TemplateData givenMainValueFileConfig(String templateFilePath, String destinationFilePath, Map<String, String> templates) {

        return new TemplateData(
                templateFilePath,
                destinationFilePath,
                templates.entrySet()
                        .stream()
                        .map(e -> new TemplateDataItem(e.getKey(), e.getValue()))
                        .collect(Collectors.toList())
        );
    }

    private Path thenCreatedFileMustExists(String tempFilePath) {

        var createdFile = Paths.get(tempFilePath);
        assertThat(Files.exists(createdFile), is(true));
        return createdFile;
    }
}
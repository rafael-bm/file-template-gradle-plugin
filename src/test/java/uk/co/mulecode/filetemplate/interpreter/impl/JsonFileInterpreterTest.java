package uk.co.mulecode.filetemplate.interpreter.impl;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonFileInterpreterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void interpreterType_jsonType() throws Exception {

        var jsonInterpreter = new JsonFileInterpreter();

        assertThat(jsonInterpreter.type(), is("json"));
    }

    @Test
    public void loadJsonContentProperty_validJson() throws Exception {

        var content = FileUtils.readFileToString(
                Paths.get("src/test/resources/sampleFile.json").toFile(),
                "UTF-8");

        var jsonInterpreter = new JsonFileInterpreter();
        jsonInterpreter.loadContent(content);
        var propertyLoaded = jsonInterpreter.getProperty("city");

        assertThat(propertyLoaded, is("london"));
    }

    @Test
    public void loadJsonContentProperty_invalidJson() throws Exception {

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(startsWith("Content must be a valid json format"));

        var content = FileUtils.readFileToString(
                Paths.get("src/test/resources/simpleFile.txt").toFile(),
                "UTF-8");

        var jsonInterpreter = new JsonFileInterpreter();
        jsonInterpreter.loadContent(content);
    }
}
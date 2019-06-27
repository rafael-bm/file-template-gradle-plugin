package uk.co.mulecode.filetemplate.file;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

public class MulecodeFileUtilsTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void loadFileAsString_validFile() {

        var validFilePath = "src/test/resources/simpleFile.txt";
        var fileUtils = new MulecodeFileUtils();

        var contentLoaded = fileUtils.loadFileAsString(validFilePath);

        assertThat(contentLoaded, is(notNullValue()));
        assertThat(contentLoaded, is("Lorem ipsum dolor sit amet"));
    }

    @Test
    public void loadFileAsString_invalidFile() {

        var unknownFilePath = "src/test/resources/unknownFile.txt";

        exception.expect(IllegalStateException.class);
        exception.expectMessage("Cannot read file " + unknownFilePath + ", it might not exists.");

        var fileUtils = new MulecodeFileUtils();

        fileUtils.loadFileAsString(unknownFilePath);
    }

    @Test
    public void fileExtension_validExtension() {

        var validFilePath = "src/test/resources/simpleFile.txt";
        var fileUtils = new MulecodeFileUtils();

        var extension = fileUtils.fileExtension(validFilePath);

        assertThat(extension, is("txt"));
    }

    @Test
    public void fileExtension_noExtension() {

        var validFilePath = "src/test/resources/Dockerfile";
        var fileUtils = new MulecodeFileUtils();

        var extension = fileUtils.fileExtension(validFilePath);

        assertThat(extension, is(""));
    }

    @Test
    public void writeStringToFile_validLocation() throws Exception {

        var tempFilePath = File.createTempFile(
                "temp",
                Long.toString(System.nanoTime())
        ).getAbsolutePath();

        var fileContent = "Lorem ipsum dolor sit amet";

        var fileUtils = new MulecodeFileUtils();
        fileUtils.writeStringToFile(
                tempFilePath,
                fileContent
        );

        var createdFile = Paths.get(tempFilePath);

        assertThat(Files.exists(createdFile), is(true));
        assertThat(FileUtils.readFileToString(createdFile.toFile(), "UTF-8"), is(fileContent));
    }
}
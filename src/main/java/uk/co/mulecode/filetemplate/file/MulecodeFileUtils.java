package uk.co.mulecode.filetemplate.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;

public class MulecodeFileUtils {

    public String loadFileAsString(String filePath) {

        var loadedFile = FileUtils.getFile(filePath);

        if (!loadedFile.exists()) {

            throw new IllegalStateException("Cannot read file " + filePath + ", it might not exists.");
        }

        String loadedContent = "";

        try {

            loadedContent = FileUtils.readFileToString(
                    loadedFile,
                    "UTF-8"
            );

        } catch (IOException e) {

            throw new IllegalStateException(e.getMessage());
        }

        return loadedContent;
    }

    public String fileExtension(String fileName) {

        return FilenameUtils.getExtension(fileName);
    }

    public void writeStringToFile(String destinationFilePath, String content) throws IOException {

        var outFile = new File(destinationFilePath);

        FileUtils.touch(outFile);

        FileUtils.writeStringToFile(
                outFile,
                content,
                "UTF-8"
        );
    }
}

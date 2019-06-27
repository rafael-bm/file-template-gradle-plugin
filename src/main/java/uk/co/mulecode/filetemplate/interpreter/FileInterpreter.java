package uk.co.mulecode.filetemplate.interpreter;

import java.io.IOException;

public interface FileInterpreter {

    String type();

    void loadContent(String content);

    String getProperty(String node);

}

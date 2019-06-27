package uk.co.mulecode.filetemplate.interpreter;

import uk.co.mulecode.filetemplate.interpreter.impl.JsonFileInterpreter;

public class FileInterpreterFactory {

    public FileInterpreter get(String type) {

        if (type.equalsIgnoreCase("json")) {

            return new JsonFileInterpreter();
        }

        throw new IllegalArgumentException("No implementation found for the given type: " + type);
    }

}

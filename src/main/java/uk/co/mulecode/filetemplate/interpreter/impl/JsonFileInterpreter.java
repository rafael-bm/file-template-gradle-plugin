package uk.co.mulecode.filetemplate.interpreter.impl;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.mulecode.filetemplate.interpreter.FileInterpreter;

import java.util.Objects;

public class JsonFileInterpreter implements FileInterpreter {

    private static Logger log = LoggerFactory.getLogger(JsonFileInterpreter.class);

    private JSONObject jsonObject;

    @Override
    public String type() {
        return "json";
    }

    @Override
    public void loadContent(String content) {

        log.debug("loadContent {}", content);

        if (StringUtils.isBlank(content)) {

            throw new IllegalArgumentException("Content should not be null or empty");
        }

        try {

            this.jsonObject = new JSONObject(content);

            log.debug("content successfully loaded {}", content);

        } catch (JSONException e) {

            throw new IllegalArgumentException("Content must be a valid json format. Message: " + e.getMessage());
        }
    }

    @Override
    public String getProperty(String node) {

        if (Objects.isNull(this.jsonObject)) {

            throw new IllegalStateException("JsonFileInterpreter was not properly initialised");
        }

        return JsonPath.read(this.jsonObject.toString(), node);
    }
}

package uk.co.mulecode.filetemplate.plugin.model;

import java.util.List;

public class TemplateData {

    private String templateFileInput;
    private String templateFileOutput;
    private List<TemplateDataItem> replaces;

    public TemplateData(String templateFileInput, String templateFileOutput, List<TemplateDataItem> replaces) {
        this.templateFileInput = templateFileInput;
        this.templateFileOutput = templateFileOutput;
        this.replaces = replaces;
    }

    public String getTemplateFileInput() {
        return templateFileInput;
    }

    public void setTemplateFileInput(String templateFileInput) {
        this.templateFileInput = templateFileInput;
    }

    public String getTemplateFileOutput() {
        return templateFileOutput;
    }

    public void setTemplateFileOutput(String templateFileOutput) {
        this.templateFileOutput = templateFileOutput;
    }

    public List<TemplateDataItem> getReplaces() {
        return replaces;
    }

    public void setReplaces(List<TemplateDataItem> replaces) {
        this.replaces = replaces;
    }
}

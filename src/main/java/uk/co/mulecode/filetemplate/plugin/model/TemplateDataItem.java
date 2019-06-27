package uk.co.mulecode.filetemplate.plugin.model;

public class TemplateDataItem {

    private String placeHolder;
    private String propertyPath;

    public TemplateDataItem(String placeHolder, String propertyPath) {
        this.placeHolder = placeHolder;
        this.propertyPath = propertyPath;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public void setPropertyPath(String propertyPath) {
        this.propertyPath = propertyPath;
    }

    @Override
    public String toString() {
        return "TemplateDataItem{" +
                "placeHolder='" + placeHolder + '\'' +
                ", propertyPath='" + propertyPath + '\'' +
                '}';
    }
}

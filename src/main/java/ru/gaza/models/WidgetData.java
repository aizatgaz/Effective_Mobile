package ru.gaza.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WidgetData {
    private String widgetName;
    private long widgetId;
    private String widgetType;
    private WidgetSizeData widgetSizeData;
    private WidgetPositionData widgetPosition;
    private WidgetOptionsData widgetOptions;
}

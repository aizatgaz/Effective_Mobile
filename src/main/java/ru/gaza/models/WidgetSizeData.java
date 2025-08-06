package ru.gaza.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WidgetSizeData {
    private int width;
    private int height;
}

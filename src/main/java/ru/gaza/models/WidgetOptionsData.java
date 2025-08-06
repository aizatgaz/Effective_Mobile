package ru.gaza.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WidgetOptionsData {
    private Object additionalProp1;
    private Object additionalProp2;
    private Object additionalProp3;
}

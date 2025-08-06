package ru.gaza.models;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WidgetPositionData {
    private int positionX;
    private int positionY;
}

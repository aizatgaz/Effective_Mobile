package ru.gaza.models;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class DashboardData {
    private String description;
    private String owner;
    private long id;
    private String name;
    private List<WidgetData> widgets;
}

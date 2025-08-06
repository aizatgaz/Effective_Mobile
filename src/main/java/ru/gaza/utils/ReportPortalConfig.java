package ru.gaza.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReportPortalConfig {
    private static final String PROPERTIES_FILE = "reportportal.properties";
    private static Properties properties = new Properties();

    static {
        try (InputStream input = ReportPortalConfig.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Не найден файл " + PROPERTIES_FILE + " в ресурсах");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла " + PROPERTIES_FILE, e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}

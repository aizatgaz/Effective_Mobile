package ru.gaza.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.gaza.models.DashboardData;
import ru.gaza.models.ErrorResponse;
import ru.gaza.utils.ReportPortalConfig;

import java.util.UUID;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PostDashboardTest {
    private static final String BASE_URL = ReportPortalConfig.get("rp.endpoint");
    private static final String API_KEY = ReportPortalConfig.get("rp.api.key");
    private static final String PROJECT_NAME = ReportPortalConfig.get("rp.project");
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.requestSpecification = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", "Bearer " + API_KEY)
                .log()
                .all();
        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    @DisplayName("Создание нового Dashboard'а")
    @Description("Создаёт проект, а затем создаёт в нём Dashboard и проверяет, что он существует")
    void createDashboardTest() {
        var dashboard = getDashboard();
        var dashboardId = createDashboard(dashboard);
        assertDashboardInList(dashboardId, dashboard);
    }

    private static Stream<Arguments> getBadRequest() {
        return Stream.of(
                Arguments.of(
                        getDashboard()
                                .setName(null),
                        "Incorrect Request. [Field 'name' should not be null.] "
                ),
                Arguments.of(
                        getDashboard()
                                .setName(""),
                        "Incorrect Request. [Field 'name' should not contain only white spaces and shouldn't be empty. " +
                                "Field 'name' should have size from '3' to '128'.] "
                ),
                Arguments.of(
                        getDashboard()
                                .setName("   "),
                        "Incorrect Request. [Field 'name' should not contain only white spaces and shouldn't be empty. " +
                                "Field 'name' should have size from '3' to '128'.] "
                ),
                Arguments.of(
                        getDashboard()
                                .setName("a".repeat(2)),
                        "Incorrect Request. [Field 'name' should have size from '3' to '128'.] "
                ),
                Arguments.of(
                        getDashboard()
                                .setName("a".repeat(129)),
                        "Incorrect Request. [Field 'name' should have size from '3' to '128'.] "
                )
        );
    }

    @ParameterizedTest
    @MethodSource("getBadRequest")
    @DisplayName("Проверка ошибки при неправильном теле")
    @Description("Попытка создания Dashboard'а с неправильными телами запроса")
    void negativeCreateDashboardTest(DashboardData dashboard, String errorMessage) {
        negativeCreateDashboard(dashboard, errorMessage);
    }

    @SneakyThrows
    @Step("Создание Dashboard '{dashboardName}'")
    private String createDashboard(DashboardData dashboard) {
        var request = OBJECT_MAPPER.writeValueAsString(dashboard);
        return given()
                .body(request)
                .when()
                .post("/api/v1/" + PROJECT_NAME + "/dashboard")
                .then()
                .extract()
                .response()
                .path("id")
                .toString();
    }

    @SneakyThrows
    @Step("Негативное создание Dashboard")
    private void negativeCreateDashboard(DashboardData dashboard, String errorMessage) {
        var request = OBJECT_MAPPER.writeValueAsString(dashboard);
        var errorResponse = given()
                .body(request)
                .when()
                .post("/api/v1/" + PROJECT_NAME + "/dashboard")
                .then()
                .extract()
                .response()
                .body()
                .as(ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).as("errorCode").isEqualTo(4001);
        assertThat(errorResponse.getMessage()).as("message").isEqualTo(errorMessage);
    }

    @Step("Проверка, что Dashboard '{dashboardName}' с ID {dashboardId} существует'")
    private void assertDashboardInList(String dashboardId, DashboardData expectedDashboard) {
        var actualDashboard = given()
                .when()
                .get("/api/v1/" + PROJECT_NAME + "/dashboard/" + dashboardId)
                .then()
                .statusCode(200)
                .extract()
                .response()
                .body()
                .as(DashboardData.class);
        assertThat(actualDashboard.getName()).as("name").isEqualTo(expectedDashboard.getName());
        assertThat(actualDashboard.getDescription()).as("descriptions").isEqualTo(expectedDashboard.getDescription());
    }

    @Step("Создания тела запроса для POST /dashboard")
    private static DashboardData getDashboard() {
        return new DashboardData()
                .setDescription("description_" + UUID.randomUUID().toString().substring(0, 8))
                .setName("name_" + UUID.randomUUID().toString().substring(0, 8));
    }
}

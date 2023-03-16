package com.example.reqres.tests;

import com.example.reqres.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static io.qameta.allure.Allure.step;
import static com.example.reqres.specs.Specs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("api")
public class ReqResInTests {

    @Test
    @DisplayName("Проверка информации о пользователе по id")
    void checkSingleUserInfo() {
        step("Запрос информации о пользвателе с id=2 и проверка данных", () -> {
            UserInfoResponseModel response = given()
                    .spec(basicRequestSpec)
                    .when()
                    .get("/users/2")
                    .then()
                    .spec(basicResponseSpec200)
                    .extract().as(UserInfoResponseModel.class);

            assertThat(response.getData().getId(), notNullValue());
            assertEquals("Janet", response.getData().getFirstName());
            assertEquals("Weaver", response.getData().getLastName());
        });
    }

    @Test
    @DisplayName("Проверка существования id для созданного пользователя")
    void checkCreatedUserIdIsNotNull() {
        step("Проверка существования id для созданного пользователя", () -> {
            CreateUserModel request = new CreateUserModel();
            request.setName("morpheus");
            request.setJob("leader");
            CreateUserModel response = given()
                    .spec(basicRequestSpec)
                    .when()
                    .body(request)
                    .post("/users")
                    .then()
                    .spec(basicResponseSpec201)
                    .extract().as(CreateUserModel.class);

            assertEquals(request.getName(), response.getName());
            assertEquals(request.getJob(), response.getJob());
            assertThat(response.getId(), notNullValue());
            assertThat(response.getCreatedAt(), notNullValue());
        });
    }

    @Test
    @DisplayName("Проверка отложенного запроса")
    void checkDelayedResponse() {
        step("Проверка отложенного запроса", () -> {
            UsersListModel response = given()
                    .spec(basicRequestSpec)
                    .param("delay", 3)
                    .when()
                    .get("/users")
                    .then()
                    .spec(basicResponseSpec200)
                    .extract().as(UsersListModel.class);

            assertEquals(1, response.getData().get(0).getId());
            assertEquals("george.bluth@reqres.in", response.getData().get(0).getEmail());
        });
    }

    @Test
    @DisplayName("Попытка регистрации пользователя без username и email")
    void checkUserRegisterNegative() {
        step("Проверка отложенного запроса", () -> {
            RegisterUserRequestModel request = new RegisterUserRequestModel();
            request.setPassword("pistol");
            RegisterUserNegativeResponseModel response = given()
                    .spec(basicRequestSpec)
                    .body(request)
                    .when()
                    .post("/register")
                    .then()
                    .spec(basicResponseSpec400)
                    .extract().as(RegisterUserNegativeResponseModel.class);

            assertThat(response.getError(), containsString("Missing email or username"));
        });
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void checkDeleteUser() {
        step("Проверка удаления пользователя", () -> {
            given()
                    .spec(basicRequestSpec)
                    .when()
                    .delete("/users/2")
                    .then()
                    .spec(basicResponseSpec204);
        });
    }
}

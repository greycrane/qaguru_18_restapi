package com.example.reqres.tests;

import com.example.reqres.models.CreateUserModel;
import com.example.reqres.models.UserInfoResponseModel;
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

//            assertThat(response.getUser()., notNullValue());
//            assertEquals("Janet", response.getUser().getId);
//            assertEquals("Weaver", response.getLastName());
        });
    }

    @Test
    @DisplayName("Проверка существования id для созданного пользователя")
    void checkCreatedUserIdIsNotNull() {
        step("Запрос информации о пользвателе с id=2", () -> {
            CreateUserModel data = new CreateUserModel();
            data.setName("morpheus");
            data.setJob("leader");
            CreateUserModel response = given()
                    .spec(basicRequestSpec)
                    .when()
                    .body(data)
                    .post("/users")
                    .then()
                    .spec(basicResponseSpec201)
                    .extract().as(CreateUserModel.class);

            assertEquals(data.getName(), response.getName());
            assertEquals(data.getJob(), response.getJob());
            assertThat(response.getId(), notNullValue());
            assertThat(response.getCreatedAt(), notNullValue());
        });
    }

    @Test
    @DisplayName("Проверка отложенного запроса")
    void checkDelayedResponse() {
        step("Запрос информации о пользвателе с id=2", () -> {
            given()
                    .spec(basicRequestSpec)
                    .param("delay", 3)
                    .when()
                    .get("/users")
                    .then()
                    .spec(basicResponseSpec200)
                    .body("data[0].id", is(1))
                    .body("data[0]", hasEntry("email", "george.bluth@reqres.in"));
        });
    }

    @Test
    @DisplayName("Попытка регистрации пользователя без username и email")
    void checkUserRegisterNegative() {
        String data = "{\"password\": \"pistol\" }";

        given()
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("https://reqres.in/api/register")
                .then()
                .log().body()
                .statusCode(400);
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void checkDeleteUser() {
        delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }
}

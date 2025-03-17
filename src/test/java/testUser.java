import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class testUser {
    static  String ct = "application/json";
    static  String urlPet = "https://petstore.swagger.io/v2/pet";


public static String lerArquivoJson(String arquivoJson) throws IOException {
    return new String(Files.readAllBytes(Paths.get(arquivoJson)));
}

    @Test
    public void testPostUser() throws IOException {
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
        int petId = 15899999;

        given()
            .contentType(ct)
            .log().all()
            .body(jsonBody)
        .when()
            .post(urlPet)
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Kiara"))
            .body("id", is(petId))
            .body("status", is("available"))
            .body("category.name", is("cachorro"))
            .body("tags[0].name", is("vacinado"))
        ;
    }
}
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class testPet {
    static  String contentType = "application/json";
    static  String urlPet = "https://petstore.swagger.io/v2/pet";
    int petId = 15899999;
    String petName = "Kiara";
    String categoryName = "cachorro";
    String tagName = "vacinado";
    String[] status = {"available", "sold"};


    public static String lerArquivoJson(String arquivoJson) throws IOException {
    return new String(Files.readAllBytes(Paths.get(arquivoJson)));
    }

    @Test @Order(1)
    public void testPostPet() throws IOException {
        String jsonBody = lerArquivoJson("src/test/resources/json/pet1.json");
        
        given()
            .contentType(contentType)
            .log().all()
            .body(jsonBody)
        .when()
            .post(urlPet)
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))
            .body("id", is(petId))
            .body("category.name", is(categoryName))
            .body("tags[0].name", is(tagName))
            .body("status", is(status[0]))
        ;
    }

    @Test @Order(2)
    public void testGetPet() {

        given()
            .contentType(contentType)
            .log().all()
        .when()
            .get(urlPet + "/" + petId)
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))
            .body("id", is(petId))
            .body("category.name", is(categoryName))
            .body("tags[0].name", is(tagName))
            .body("status", is(status[0]))
        ;
    }

    @Test @Order(3)
    public void testPutPet() throws IOException {
        String jsonBody = lerArquivoJson("src/test/resources/json/pet2.json");

        given()
            .contentType(contentType)
            .log().all()
            .body(jsonBody)
        .when()
            .put(urlPet)
        .then()
            .log().all()
            .statusCode(200)
            .body("name", is(petName))
            .body("id", is(petId))
            .body("category.name", is(categoryName))
            .body("tags[0].name", is(tagName))
            .body("status", is(status[1]))
        ;
    }

    @Test @Order(4)
    public void testDeletePet() {
        given()
            .contentType(contentType)
            .log().all()
        .when()
            .delete(urlPet + "/" + petId)
        .then()
            .log().all()
            .statusCode(200)
            .body("code", is(200))
            .body("type", is("unknown"))
            .body("message", is("15899999"))
        ;
    }

    
}
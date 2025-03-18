
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;


public class testUser {
    static  String contentType = "application/json";
    static  String urlUser = "https://petstore.swagger.io/v2/user";

    @Test
    public void testeLogin() {

        String userName = "sergio";
        String passWord = "123456";

        String resultadoEsperado = "logged in user session:";

        Response resposta = (Response)
        given()
            .contentType(contentType)
            .log().all()
        .when()
            .get(urlUser + "/login?username="+ userName +"&password="+ passWord)
        .then()
            .log().all()
            .statusCode(200)
            .body("type", is("unknown"))
            .body("message", containsString(resultadoEsperado))
            .body("message", hasLength(36))
        .extract()
        ;

        String token = resposta.jsonPath().getString("message").substring(23);
        System.out.println("O token é: " + token);
    }
}

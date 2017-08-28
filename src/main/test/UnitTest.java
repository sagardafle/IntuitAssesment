import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class UnitTest {
    @BeforeClass
    public static void init() {
        RestAssured.baseURI = "https://localhost:8080";
    }

    @Test
    public void testStatusCode () {

        Response res =
                given ()
                        .param ("sagar")
                        .when()
                        .get ("/rest/intuit/sagar");

        Assert.assertEquals (res.statusCode (), 200);
    }

    @AfterClass
    public void oneTimeTearDown() {
        System.out.println("@AfterClass: The annotated method will be run after all the test methods in the current class have been run.");
    }
}

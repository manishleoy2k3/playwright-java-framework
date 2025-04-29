// File: tests/AdvancedFileHandlingTest.java
package tests.api.user;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.codec.digest.DigestUtils;
import org.testng.annotations.Test;
import utils.BaseTest;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.*;

public class AdvancedFileHandlingTest extends BaseTest {

    @Test(description = "Upload a file to API")
    public void testFileUpload() {
        File fileToUpload = new File("src/test/resources/test-image.png");

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .multiPart("file", fileToUpload)
                .when()
                .post("/api/upload");

        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.getBody().asString().contains("uploaded"));
    }

    @Test(description = "Download a file and verify contents")
    public void testFileDownloadAndChecksumValidation() throws IOException {
        String downloadPath = "src/test/resources/downloaded-sample.txt";

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .when()
                .get("/api/download/sample.txt");

        assertEquals(response.getStatusCode(), 200);

        Files.write(Paths.get(downloadPath), response.getBody().asByteArray());

        // Validate using checksum
        String expectedChecksum = DigestUtils.md5Hex(new FileInputStream("src/test/resources/original-sample.txt"));
        String downloadedChecksum = DigestUtils.md5Hex(new FileInputStream(downloadPath));
        assertEquals(downloadedChecksum, expectedChecksum, "MD5 Checksum should match");

        // Validate content contains expected text
        String downloadedText = Files.readString(Paths.get(downloadPath));
        assertTrue(downloadedText.contains("expected content"));
    }

    @Test(description = "Upload dynamically created file")
    public void testDynamicFileUpload() throws IOException {
        String dynamicFilePath = "src/test/resources/dynamic-file.txt";
        String content = "This is dynamically generated content.";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dynamicFilePath))) {
            writer.write(content);
        }

        File dynamicFile = new File(dynamicFilePath);
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .multiPart("file", dynamicFile)
                .when()
                .post("/api/upload");

        assertEquals(response.getStatusCode(), 200);
        assertTrue(response.getBody().asString().contains("uploaded"));
    }
}

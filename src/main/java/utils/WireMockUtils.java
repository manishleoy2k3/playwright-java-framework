package utils;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import java.nio.file.Files;
import java.nio.file.Paths;

public class WireMockUtils {
    private static WireMockServer wireMockServer;

    public static void startServer() {
    	wireMockServer = new WireMockServer(options().dynamicPort());// Running WireMock on port 8089
    	wireMockServer.start();
    	System.out.println("WireMock started on port: " + wireMockServer.port());
    	// ✅ configure client to use the right port
        configureFor("localhost", wireMockServer.port());
    }

    public static int getWireMockServerPort() {
        return wireMockServer.port();
    }
    public static void stopServer() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    public static void stubGetFromFile(String url, String filePath, int statusCode) throws Exception {
        String body = new String(Files.readAllBytes(Paths.get(filePath)));
        stubFor(get(urlEqualTo(url))
            .willReturn(aResponse()
                .withStatus(statusCode)
                .withHeader("Content-Type", "application/json")
                .withBody(body)));
    }

    // Optional POST stub from file
    public static void stubPostFromFile(String url, String filePath, int statusCode) throws Exception {
        String body = new String(Files.readAllBytes(Paths.get(filePath)));
        stubFor(post(urlEqualTo(url))
            .willReturn(aResponse()
                .withStatus(statusCode)
                .withHeader("Content-Type", "application/json")
                .withBody(body)));
    }
}

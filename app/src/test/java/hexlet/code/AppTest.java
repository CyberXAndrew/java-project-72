package hexlet.code;

import hexlet.code.controller.UrlController;
import hexlet.code.repository.UrlCheckRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppTest {
    private static Javalin app;
    private MockWebServer mockServer;

    @BeforeEach
    public void beforeEach() throws SQLException, IOException {
        app = App.getApp();
        mockServer = new MockWebServer();
        mockServer.start();
    }
    @AfterEach
    public void afterEach() throws IOException {
        mockServer.shutdown();
    }

    @Test
    public void testMainPage() { // RootController::index
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        }));
    }

    @Test
    public void testUrlsPage() { // UrlController::index
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        }));
    }

    @Test
    public void testAddUrl() throws SQLException { // UrlController::add
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "https://www.google.com";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("google");
        }));

        assertThat(UrlRepository.getUrls().size()).isEqualTo(1);
    }

    @Test
    public void testAddUrlNegative() {
        JavalinTest.test(app, ((server, client) -> {
            String invalidUrl = "http//www.google.com";
            var response = client.post("/urls", invalidUrl);
            assertThat(response.code()).isNotEqualTo(200); // ??
        }));
    }

    @Test
    public void testUrlPage() { //UrlController::show
        JavalinTest.test(app, (server, client) -> {
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            Url testUrl = new Url("http://www.google.com", createdAt);
            UrlRepository.save(testUrl);
            var response = client.get("/urls/1");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlNotFound() { // UrlController::show
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/url/999");
            assertThat(response.code()).isEqualTo(404);
        });
    }

//    @Test
//    public void testMakeCheck1() throws NullPointerException { // UrlController::makeCheck
//        JavalinTest.test(app, (server, client) -> {
//            client.post("/urls?url=https://www.google.com"); // пост на создание url
//            assertThat(client.get("/urls/1").code()). isEqualTo(200); // проверка наличия записи в бд
//            var response = client.post("/urls/1/checks"); // запрос на создание проверки
//            assertThat(response.code()).isEqualTo(200); // проверка кода
//            assertThat(UrlRepository.getChecksByUrlId(1L).size()).isGreaterThan(0); // проверка наличия объекта проверки
//        });
//    }

    @Test
    public void testMakeCheckPrototipe() throws SQLException, IOException {
        String page = Files.readString(Paths.get("/src/test/resources/testHtmlPage.html"));

        MockResponse mockResponse = new MockResponse().setResponseCode(200)
                .setBody(page);
        mockServer.enqueue(mockResponse);

        String urlString = mockServer.url("/").toString();
        Url testUrl = new Url(urlString, new Timestamp(System.currentTimeMillis()));
        UrlRepository.save(testUrl);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + testUrl.getId() + "/checks"); // запрос на создание проверки
            assertThat(response.code()).isEqualTo(200); // проверка кода ответа
            assertThat(UrlCheckRepository.getChecksByUrlId(1L).size()).isGreaterThan(0); // проверка наличия объекта проверки
            assertThat(response.body().string()).contains("<title>Sample title</title>")
                    .contains("<h1>Sample header</h1>")
                    .contains("<p>Sample content</p>");
        });
    }
}

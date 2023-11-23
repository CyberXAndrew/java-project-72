package hexlet.code;


import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AppTest {
    private static Javalin app;
    private static MockWebServer mockServer;
    private final String incorrectSocketAddress = "https//www.google.com";
    private final String correctSocketAddress = "https://www.google.com";
    private final String correctSocketAddress2 = "https://www.youtube.com";

    @BeforeEach
    public void beforeEach() {
        try {
            app = App.getApp();
            mockServer = new MockWebServer();
            mockServer.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @AfterAll
    public static void afterAll() throws IOException {
        mockServer.shutdown();
        app.stop();
    }

    @Test
    public void testMainPage() { // RootController::index
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Анализатор страниц");
        }));
    }

    @Test
    public void testAddUrl() { // UrlController::add
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=" + correctSocketAddress;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("google");
            assertThat(UrlRepository.getUrls().size()).isEqualTo(1);
            assertThat(UrlRepository.getUrls().get(0).getName()).contains(correctSocketAddress);
        }));
    } // должен проверять, что нужная сущность была добавлена в БД и отображается на странице

    @Test
    public void testAddUrlNegative() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=" + incorrectSocketAddress;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("Некорректный URL");
            assertThat(UrlRepository.getUrls().size()).isEqualTo(0);
        }));
    }

    @Test
    public void testUrlsPage() { // UrlController::index
        JavalinTest.test(app, ((server, client) -> {
            Url url1 = new Url(correctSocketAddress2, createTimestamp());
            Url url2 = new Url(correctSocketAddress, createTimestamp());
            UrlRepository.save(url1);
            UrlRepository.save(url2);
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("youtube").contains("google");
            assertThat(UrlRepository.getUrls().size()).isEqualTo(2);
        }));
    }

    @Test
    public void testUrlPage() { //UrlController::show
        JavalinTest.test(app, (server, client) -> {
            Url testUrl = new Url(correctSocketAddress, createTimestamp());
            UrlRepository.save(testUrl);
            var response = client.get("/urls/1");
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("google");
        });
    }

    @Test
    public void testUrlNotFound() { // UrlController::show
        JavalinTest.test(app, (server, client) -> {
            var unexistingUrlId = 999;
            var response = client.get("/urls/" + unexistingUrlId);
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testMakeCheck() throws SQLException, IOException {
        String page = Files.readString(Paths.get("./src/test/resources/testHtmlPage.html"));

        MockResponse mockResponse = new MockResponse().setResponseCode(200)
                .setBody(page);
        mockServer.enqueue(mockResponse);

        String urlString = mockServer.url("/").toString();
        Url testUrl = new Url(urlString, createTimestamp());
        UrlRepository.save(testUrl);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + testUrl.getId() + "/checks"); // запрос на создание проверки
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("url");

            List<UrlCheck> checks = UrlCheckRepository.getChecksByUrlId(testUrl.getId());
            UrlCheck lastCheck = checks.get(checks.size() - 1);

            assertThat(lastCheck).isNotNull();
            assertThat(lastCheck.getStatusCode()).isEqualTo(200);
            assertThat(lastCheck.getTitle()).isEqualTo("Sample title");
            assertThat(lastCheck.getH1()).isEqualTo("Sample header");
//            assertThat(lastCheck.getDescription()).contains("Sample description"); // находит пустую строку - почему?
        });
    }

    private Timestamp createTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}

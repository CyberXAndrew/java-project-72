package hexlet.code;

import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;

import static org.assertj.core.api.Assertions.assertThat;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public final class AppTest {
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
    public void testMainPage() {
        JavalinTest.test(app, ((server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Анализатор страниц");
        }));
    }

    @Test
    public void testAddUrl() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=" + correctSocketAddress;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("google");
            assertThat(UrlsRepository.getUrls().size()).isEqualTo(1);
            assertThat(UrlsRepository.getUrls().get(0).getName()).contains(correctSocketAddress);
        }));
    }

    @Test
    public void testAddUrlNegative() {
        JavalinTest.test(app, ((server, client) -> {
            String requestBody = "url=" + incorrectSocketAddress;
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("Некорректный URL");
            assertThat(UrlsRepository.getUrls().size()).isEqualTo(0);
        }));
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, ((server, client) -> {
            Url url1 = new Url(correctSocketAddress2, createTimestamp());
            Url url2 = new Url(correctSocketAddress, createTimestamp());
            UrlsRepository.save(url1);
            UrlsRepository.save(url2);
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("youtube").contains("google");
            assertThat(UrlsRepository.getUrls().size()).isEqualTo(2);
        }));
    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            Url testUrl = new Url(correctSocketAddress, createTimestamp());
            UrlsRepository.save(testUrl);
            var response = client.get("/urls/1");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("google");
        });
    }

    @Test
    public void testUrlNotFound() {
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
        UrlsRepository.save(testUrl);

        JavalinTest.test(app, (server, client) -> {
            var response = client.post("/urls/" + testUrl.getId() + "/checks");
            assertThat(response.code()).isEqualTo(200);
            assert response.body() != null;
            assertThat(response.body().string()).contains("url");

            List<UrlCheck> checks = UrlChecksRepository.getChecksByUrlId(testUrl.getId());
            UrlCheck lastCheck = checks.get(checks.size() - 1);

            assertThat(lastCheck).isNotNull();
            assertThat(lastCheck.getStatusCode()).isEqualTo(200);
            assertThat(lastCheck.getTitle()).isEqualTo("Sample title");
            assertThat(lastCheck.getH1()).isEqualTo("Sample header");
        });
    }

    private Timestamp createTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}

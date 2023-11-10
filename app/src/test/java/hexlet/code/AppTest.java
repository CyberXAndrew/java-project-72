package hexlet.code;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class AppTest {
    private static Javalin app;

    @BeforeEach
    public void beforeEach() throws Exception {
        app = App.getApp();
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
    public void testAddUrl() throws SQLException {
        JavalinTest.test(app, ((server, client) -> {
            String responseBody = "https://www.google.com";
            var response = client.post("/urls", responseBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("google");
        }));

        assertThat(UrlRepository.getUrls().size()).isEqualTo(1);
    }

//    @Test
//    public void testAddUrlNegative() throws SQLException {
//        JavalinTest.test(app, ((server, client) -> {
//            String responseBody = "http//www.google.com";
//            var response = client.post("/urls", responseBody);
//            assertThat(response.code()).isNotEqualTo(200); // ??
//        }));
//    }

    @Test
    public void testUrlPage() {
        JavalinTest.test(app, (server, client) -> {
            Url testUrl = new Url("http://www.google.com");
            UrlRepository.save(testUrl);
            var response = client.get("/urls/1");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void testUrlNotFound() throws Exception {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/url/999");
            assertThat(response.code()).isEqualTo(404);
        });
    }
}

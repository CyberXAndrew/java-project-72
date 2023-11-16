package hexlet.code;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.repository.UrlRepository;
import hexlet.code.model.Url;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
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
    public void testAddUrlNegative() throws SQLException {
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
//    public void testMakeCheck() throws NullPointerException { // UrlController::makeCheck
//        JavalinTest.test(app, (server, client) -> {
//            client.post("/urls?url=https://www.google.com"); // пост на создание url
//            assertThat(client.get("/urls/1").code()). isEqualTo(200); // проверка наличия записи в бд
//            var response = client.post("/urls/1/checks"); // запрос на создание проверки
//            assertThat(response.code()).isEqualTo(200); // проверка кода
//            assertThat(UrlRepository.getChecksByUrlId(1L).size()).isGreaterThan(0); // проверка наличия объекта проверки
//        });
//    }

//    @Test
//    public void testMakeCheckkkkkk() throws SQLException, IOException {
//        String page = Files.readString(Paths.get("/src/test/resources/templates/urls/show.jte"));
//        MockWebServer mockServer = new MockWebServer();
//        MockResponse response = new MockResponse().setResponseCode(200)
//                .setBody(page);
//        mockServer.enqueue(response);
//        String url = mockServer.url("/urls/1/checks").toString();
//        mockServer.start();

//        mockServer.shutdown();
//    }
//    Создаём инстанс `MockWebServer`. Вызвав на созданном инстансе метод `mockServer.url("/").toString()` можно получить
//    адрес сайта, который нужно будет использовать в тестах
//2. Создаём инстанс `MockResponse`, и устанавливаем нужное тело ответа. Это и есть та фейковая страница, а точнее её
// содержимое (html), с которой будет работать наше приложение в тестах
//3. Добавляем инстанс MockResponse в очередь к созданному серверу
//4. Запускаем сервер
//5. После выполнения тестов обязательно нужно остановить сервер. Воспользуйтесь аннотациями `@BeforeAll` и `@AfterAll` в тестах
}

package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlsController;
import hexlet.code.repository.BaseRepository;
import hexlet.code.util.NamedRoutes;
import io.javalin.Javalin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import io.javalin.rendering.template.JavalinJte;
import gg.jte.resolve.ResourceCodeResolver;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class App {
    public static Javalin getApp() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl());
        if (!Objects.equals(getDatabaseUrl(), "jdbc:h2:mem:project-72")) {
            hikariConfig.setUsername(System.getenv().get("JDBC_DATABASE_USERNAME"));
            hikariConfig.setPassword(System.getenv().get("JDBC_DATABASE_PASSWORD"));
        }
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        try {
            String sql = readResourceFile();
            try (var connection = dataSource.getConnection();
                 var statement = connection.createStatement()) {
                statement.execute(sql);
                log.info(sql);
            } catch (SQLException ex) {
                log.error(ex.getMessage());
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        BaseRepository.dataSource = dataSource;
        JavalinJte.init(createTemplateEngine());
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.plugins.enableDevLogging());

        app.get(NamedRoutes.rootPath(), RootController::index);

        app.post(NamedRoutes.urlsPath(), UrlsController::add);
        app.get(NamedRoutes.urlsPath(), UrlsController::index);

        app.get(NamedRoutes.urlPath("{id}"), UrlsController::show);
        app.post(NamedRoutes.urlChecksPath("{id}"), UrlsController::makeCheck);

        return app;
    }

    private static String readResourceFile() throws IOException {
        var inputStream = App.class.getClassLoader().getResourceAsStream("schema.sql");
        assert inputStream != null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    public static void main(String[] args) {
        Javalin app = App.getApp();
        int port = Integer.parseInt(getPort());
        app.start(port);
    }

    private static String getPort() {
        return System.getenv().getOrDefault("PORT", "7070");
    }

    private static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project-72");
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}

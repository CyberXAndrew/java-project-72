package hexlet.code;

import hexlet.code.controller.RootController;
import hexlet.code.controller.UrlController;
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class App {
    public static Javalin getApp() throws IOException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(getDatabaseUrl()); //project_72 - имя базы данных
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        URL url = App.class.getClassLoader().getResource("schema.sql");
        File file = new File(Objects.requireNonNull(url).getFile()); //  Objects.requireNonNull(
        String sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));

        log.info(sql);
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        BaseRepository.dataSource  = dataSource;
        JavalinJte.init(createTemplateEngine());
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.plugins.enableDevLogging());

        app.get(NamedRoutes.rootPath(), RootController::index);

        app.post(NamedRoutes.urlsPath(), UrlController::add);
        app.get(NamedRoutes.urlsPath(), UrlController::index);

        app.get(NamedRoutes.urlPath("{id}"), UrlController::show);

        app.post(NamedRoutes.urlChecksPath("{id}"), UrlController::makeCheck);

        return app;
    }

    public static void main(String[] args) throws IOException {
        Javalin app = App.getApp();
        int port = Integer.parseInt(getPort());
        app.start(port);
    }

    private static String getPort() {
        return System.getenv().getOrDefault("PORT", "7070");
    }

    private static String getDatabaseUrl() {
        return System.getenv().getOrDefault("JDBC_DATABASE_URL", "jdbc:h2:mem:project-72"); //project_72 - имя базы данных
    }

    private static TemplateEngine createTemplateEngine() {
        ClassLoader classLoader = App.class.getClassLoader();
        ResourceCodeResolver codeResolver = new ResourceCodeResolver("templates", classLoader);
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }
}

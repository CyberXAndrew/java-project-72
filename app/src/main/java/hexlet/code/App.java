package hexlet.code;

import io.javalin.Javalin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class App {
    public static Javalin getApp() throws IOException, SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:mem:java_project_72;DB_CLOSE_DELAY=-1;"); //java_project_72 - имя базы данных
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        URL url = App.class.getClassLoader().getResource("schema.sql");
        File file = new File(url.getFile());
        String sql = Files.lines(file.toPath()).collect(Collectors.joining("\n"));

        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(sql);
        }

        BaseRepository.dataSource  = dataSource;

        Javalin app = Javalin.create(javalinConfig -> javalinConfig.plugins.enableDevLogging());
        app.get("/", ctx -> ctx.result("Hello World"));

        return app;
    }

    public static void main(String[] args) throws SQLException, IOException {
        Javalin app = App.getApp();
        int port = 7070;
        if(System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        app.start(port);
    }
}

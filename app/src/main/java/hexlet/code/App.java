package hexlet.code;

import io.javalin.Javalin;

public class App {
    public static Javalin getApp() {
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.plugins.enableDevLogging());

        app.get("/", ctx -> ctx.result("Hello World"));

        return app;
    }

    public static void main(String[] args) {
        Javalin app = App.getApp();
        int port = 7070;
        if(System.getenv("PORT") != null) {
            port = Integer.parseInt(System.getenv("PORT"));
        }
        app.start(port);
    }
}
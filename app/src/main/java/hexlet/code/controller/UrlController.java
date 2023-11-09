package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UrlController {
    public static void add(Context ctx) throws SQLException { //get /
        String rawUrl = ctx.formParam("url");
        try {
            URL url = new URL(rawUrl);
            String socketAddress = collectSocketAddress(url);

            if (UrlRepository.findBySocket(socketAddress)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect("/urls"); // на обработчик get /urls
                return;
            }
            Url correctUrl = new Url(socketAddress);
            UrlRepository.save(correctUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.redirect("/urls");
        } catch (MalformedURLException e) {
            String flash = "Некорректный URL";
            BuildUrlPage page = new BuildUrlPage();
            page.setFlash(flash);
            ctx.render("urls/build.jte", Collections.singletonMap("buildUrlPage", page));
        }

    }

    public static void index(Context ctx) throws SQLException { //вывод списка добавленных в бд урл
        List<Url> urls = UrlRepository.getUrls();
        UrlsPage page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", Collections.singletonMap("urlsPage", page));
    }

    public static void show(Context ctx) throws SQLException {
        String id = ctx.pathParam("id");
        Url url = UrlRepository.findById(id).orElseThrow(() -> new NotFoundResponse("Url with id " + id + " not found"));
        UrlPage page = new UrlPage(url);
        ctx.render("urls/show.jte", Collections.singletonMap("urlPage", page));
    }

    private static String collectSocketAddress(URL url) {
        String protocol = url.getProtocol();
        String host = url.getHost();
        Integer port = url.getPort() == -1 ? null : url.getPort();
        return String.format("s%://s%:s%", protocol, host, port);
    }
}

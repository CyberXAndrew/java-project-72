package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class UrlController {
    public static void add(Context ctx) throws SQLException { // post "/urls"
        String rawUrl = ctx.formParam("url");
        try {
            URL url = new URL(rawUrl);
            String socketAddress = collectSocketAddress(url);

            if (UrlRepository.findBySocket(socketAddress)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.redirect("/urls"); // на обработчик get /urls
                return;
            }
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            Url correctUrl = new Url(socketAddress, createdAt);
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

    public static void index(Context ctx) throws SQLException {
        List<Url> urls = UrlRepository.getUrls();
        UrlsPage page = new UrlsPage(urls);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        ctx.render("urls/index.jte", Collections.singletonMap("urlsPage", page));
    }

    public static void show(Context ctx) throws SQLException {
        long id = Long.parseLong(ctx.pathParam("id"));
        Url url = UrlRepository.findById(id).orElseThrow(() -> new NotFoundResponse("Url with id " + id + " not found"));
        UrlPage page = new UrlPage(url);
        ctx.render("urls/show.jte", Collections.singletonMap("urlPage", page));
    }

    public static void makeCheck(Context ctx) throws SQLException {
        long urlId = Long.parseLong(ctx.pathParam("id"));
        Url soughtUrl = UrlRepository.findById(urlId).orElseThrow(
                () -> new NotFoundResponse("Url with id " + urlId + " not found"));
        String name = soughtUrl.getName();

        HttpResponse<String> response = Unirest.get(name).asString();
        Integer statusCode = response.getStatus();
        Document document = Jsoup.parse(response.getBody());
        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
        UrlCheck urlCheck = new UrlCheck(statusCode, urlId, createdAt);

        urlCheck.setTitle(document.title().isEmpty() ? document.title() : null);
        urlCheck.setH1(document.selectFirst("h1").text());
        urlCheck.setDescription(document.selectFirst("meta[name=description]") != null ?
                document.selectFirst("meta[name=content]").text() : null);
        UrlRepository.saveCheck(urlCheck);
        ctx.redirect("urls/show.jte");
    }

    private static String collectSocketAddress(URL url) {
        String protocol = url.getProtocol();
        String host = url.getHost();
        Integer port = url.getPort() == -1 ? null : url.getPort();
        return port == null ? String.format("s%://s%", protocol, host) : String.format("s%://s%:port", protocol, host, port);
    }
}

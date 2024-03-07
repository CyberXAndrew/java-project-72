package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.model.Url;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.repository.UrlsRepository;

import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.Unirest;
import kong.unirest.core.UnirestException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrlsController {
    public static void add(Context ctx) throws SQLException {
        String rawUrl = ctx.formParam("url");
        try {
            URL url = new URL(rawUrl);
            String socketAddress = collectSocketAddress(url);

            if (UrlsRepository.findBySocket(socketAddress)) {
                ctx.sessionAttribute("flash", "Страница уже существует");
                ctx.sessionAttribute("flash-type", "alert-info");
                ctx.redirect("/urls");
                return;
            }
            Url correctUrl = new Url(socketAddress);
            UrlsRepository.save(correctUrl);
            ctx.sessionAttribute("flash", "Страница успешно добавлена");
            ctx.sessionAttribute("flash-type", "alert-success");
            ctx.redirect("/urls");
        } catch (MalformedURLException e) {
            String flash = "Некорректный URL";
            String flashType = "alert-danger";
            BuildUrlPage page = new BuildUrlPage();
            page.setFlash(flash);
            page.setFlashType(flashType);
            ctx.render("urls/build.jte", Collections.singletonMap("buildUrlPage", page));
        }
    }

    public static void index(Context ctx) throws SQLException {
        Map<Url, List<UrlCheck>> map = new HashMap<>();

        List<Url> urls = UrlsRepository.getUrls();
        for (Url url : urls) {
            List<UrlCheck> checks = UrlChecksRepository.getChecksByUrlId(url.getId());
            map.put(url, checks);
        }
        UrlsPage page = new UrlsPage(map);
        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/index.jte", Collections.singletonMap("urlsPage", page));
    }

    public static void show(Context ctx) throws SQLException {
        long id = Long.parseLong(ctx.pathParam("id"));
        Url url = UrlsRepository.findById(id).orElseThrow(() ->
                new NotFoundResponse("Url with id " + id + " not found"));
        List<UrlCheck> urlChecks = UrlChecksRepository.getChecksByUrlId(id);
        UrlPage page = new UrlPage(url, urlChecks);

        page.setFlash(ctx.consumeSessionAttribute("flash"));
        page.setFlashType(ctx.consumeSessionAttribute("flash-type"));
        ctx.render("urls/show.jte", Collections.singletonMap("urlPage", page));
    }

    public static void makeCheck(Context ctx) throws SQLException {
        long urlId = Long.parseLong(ctx.pathParam("id"));
        Url soughtUrl = UrlsRepository.findById(urlId).orElseThrow(
                () -> new NotFoundResponse("Url with id " + urlId + " not found"));
        String name = soughtUrl.getName();

        try {
            HttpResponse<String> response = Unirest.get(name).asString();
            Integer statusCode = response.getStatus();
            Document document = Jsoup.parse(response.getBody());
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            UrlCheck urlCheck = new UrlCheck(statusCode, urlId, createdAt);

            urlCheck.setTitle(document.title().isEmpty() ? null : document.title());
            Element h1 = document.selectFirst("h1");
            urlCheck.setH1(h1 == null ? null : h1.ownText());

            Element descriptionElement = document.selectFirst("meta[name=description]");
            String description = descriptionElement == null ? "" : descriptionElement.attr("content");
            urlCheck.setDescription(description);

            UrlChecksRepository.saveCheck(urlCheck);
            ctx.redirect("/urls/" + urlId);
        } catch (UnirestException ex) {
            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
            UrlCheck urlCheck = new UrlCheck(404, urlId, createdAt);
            UrlChecksRepository.saveCheck(urlCheck);
            ctx.sessionAttribute("flash", "Проверьте правильность домена");
            ctx.sessionAttribute("flash-type", "alert-danger");
            ctx.redirect("/urls/" + urlId);
        }
    }

    private static String collectSocketAddress(URL url) {
        String protocol = url.getProtocol();
        String host = url.getHost();
        Integer port = url.getPort() == -1 ? null : url.getPort();
        String result = port == null ? (protocol + "://" + host) : (protocol + "://" + host + ":" + port);
        return result.toLowerCase().trim();
    }
}

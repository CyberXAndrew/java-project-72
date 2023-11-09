package hexlet.code.controller;

import hexlet.code.dto.urls.BuildUrlPage;
import io.javalin.http.Context;

import java.util.Collections;

public class RootController {
    public static void index(Context ctx) { // get "/"
        BuildUrlPage page = new BuildUrlPage();
        page.setFlash(null);
        ctx.render("urls/build.jte", Collections.singletonMap("buildUrlPage", page));
    }

}

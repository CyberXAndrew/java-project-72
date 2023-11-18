package gg.jte.generated.ondemand.layout;
import gg.jte.Content;
import hexlet.code.dto.urls.BuildUrlPage;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.dto.urls.UrlsPage;
public final class JtemainpageGenerated {
	public static final String JTE_NAME = "layout/mainpage.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,3,5,5,5,36,36,36,37,37,37,38,38,39,39,39,40,40,41,41,41,42,42,49,49,49,5,6,7,8,8,8,8};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, Content content, BuildUrlPage buildUrlPage, UrlsPage urlsPage, UrlPage urlPage) {
		jteOutput.writeContent("\n<!doctype html>\n<html lang=\"en\">\n<head>\n    <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\"\n          integrity=\"sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65\" crossorigin=\"anonymous\">\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\"\n          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n    <title>Главная страница</title>\n</head>\n<body class=\"d-flex flex-column min-vh-100\">\n    <nav class=\"navbar navbar-expand-lg navbar-dark ps-3 border-bottom border-2\" style=\"background-color: #FFD700;\">\n        <a class=\"navbar-brand text-dark ml-3\" href=\"/\">Анализатор страниц</a>\n        <button class=\"navbar-toggler\" type=\"button\" data-bs-toggle=\"collapse\" data-bs-target=\"#navbarNav\"\n                aria-controls=\"navbarNav\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n            <span class=\"navbar-toggler-icon\"></span>\n        </button>\n        <div class=\"navbar-collapse collapse show\" id=\"navbarNav\">\n            <div class=\"navbar-nav\">\n                <a class=\"nav-link text-dark\" href=\"/\">Главная</a>\n                <a class=\"nav-link text-dark\" href=\"/urls\">Сайты</a>\n            </div>\n        </div>\n    </nav>\n    <main class=\"flex-grow-1\">\n    ");
		if (buildUrlPage != null) {
			jteOutput.writeContent("\n        ");
			jteOutput.setContext("main", null);
			jteOutput.writeUserContent(content);
			jteOutput.writeContent("\n    ");
		} else if (urlsPage != null) {
			jteOutput.writeContent("\n        ");
			jteOutput.setContext("main", null);
			jteOutput.writeUserContent(content);
			jteOutput.writeContent("\n    ");
		} else if (urlPage != null) {
			jteOutput.writeContent("\n        ");
			jteOutput.setContext("main", null);
			jteOutput.writeUserContent(content);
			jteOutput.writeContent("\n    ");
		}
		jteOutput.writeContent("\n    </main>\n    <footer class=\"footer text-center mx-auto my-auto fixed-bottom bg-light-blue border-top border-4\">\n        <p>Created by<a href=\"https://github.com/CyberXAndrew\"> Andrew B.</a></p>\n    </footer>\n</body>\n</html>\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		Content content = (Content)params.get("content");
		BuildUrlPage buildUrlPage = (BuildUrlPage)params.getOrDefault("buildUrlPage", null);
		UrlsPage urlsPage = (UrlsPage)params.getOrDefault("urlsPage", null);
		UrlPage urlPage = (UrlPage)params.getOrDefault("urlPage", null);
		render(jteOutput, jteHtmlInterceptor, content, buildUrlPage, urlsPage, urlPage);
	}
}

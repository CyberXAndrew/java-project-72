package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.BuildUrlPage;
public final class JtebuildGenerated {
	public static final String JTE_NAME = "urls/build.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,2,2,4,4,6,6,9,9,10,10,10,10,11,11,11,14,14,24,24,24,25,25,25,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, BuildUrlPage buildUrlPage) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainpageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n        <div class=\"container-fluid p-5\">\n            <h4>Бесплатно проверяйте сайты на SEO пригодность</h4>\n            ");
				if (buildUrlPage.getFlash() != null) {
					jteOutput.writeContent("\n            <div class=\"alert ");
					jteOutput.setContext("div", "class");
					jteOutput.writeUserContent(buildUrlPage.getFlashType());
					jteOutput.setContext("div", null);
					jteOutput.writeContent(" alert-dismissible fade show\" role=\"alert\">\n                ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(buildUrlPage.getFlash());
					jteOutput.writeContent("\n                <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n            </div>\n            ");
				}
				jteOutput.writeContent("\n            <div class=\"container-sm hstack gap-3\">\n                <form class=\"form-floating \" action=\"/urls\" method=\"post\">\n                    <input class=\"form-control\" type=\"search\" required name=\"url\" placeholder=\"Введите ссылку\">\n                    <label>Введите url сайта: </label>\n                    <input class=\"btn btn-primary\" type=\"submit\" value=\"Проверить\">\n                </form>\n                <p class=\"text-muted mt-3\">Пример: https://www.example.com</p>\n            </div>\n        </div>\n");
			}
		}, buildUrlPage, null, null);
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		BuildUrlPage buildUrlPage = (BuildUrlPage)params.get("buildUrlPage");
		render(jteOutput, jteHtmlInterceptor, buildUrlPage);
	}
}

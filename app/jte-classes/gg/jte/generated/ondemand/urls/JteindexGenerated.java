package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.Url;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlChecksRepository;
import hexlet.code.util.NamedRoutes;
import java.sql.SQLException;
import java.util.List;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,3,4,5,6,7,10,10,10,12,12,14,14,15,15,17,17,17,17,18,18,18,22,22,36,36,38,47,47,47,47,47,47,47,47,47,47,49,49,49,52,52,52,52,52,52,52,52,52,52,52,52,55,55,56,56,56,57,57,60,60,61,61,61,62,62,65,65,70,70,70,71,71,71,10,10,10,10};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlsPage urlsPage) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainpageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    ");
				if (urlsPage.getFlash() != null) {
					jteOutput.writeContent("\n    <div>\n        <div class=\"alert ");
					jteOutput.setContext("div", "class");
					jteOutput.writeUserContent(urlsPage.getFlashType());
					jteOutput.setContext("div", null);
					jteOutput.writeContent(" alert-dismissible fade show\" role=\"alert\">\n            ");
					jteOutput.setContext("div", null);
					jteOutput.writeUserContent(urlsPage.getFlash());
					jteOutput.writeContent("\n            <button type=\"button\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button>\n        </div>\n    </div>\n    ");
				}
				jteOutput.writeContent("\n    <div class=\"mx-auto\">\n        <h2>Сайты</h2>\n        <div>\n            <table class=\"table table-bordered table-hover align-middle\">\n                <thead>\n                    <tr>\n                        <td>Id</td>\n                        <td>Имя</td>\n                        <td>Последняя проверка</td>\n                        <td>Код ответа</td>\n                    </tr>\n                </thead>\n                <tbody>\n                ");
				for (Url url : urlsPage.getUrls()) {
					jteOutput.writeContent("\n                    <tr>\n                        ");
					
                        List<UrlCheck> checks;
                        UrlCheck lastCheck;
                        try{
                            checks = UrlChecksRepository.getChecksByUrlId(url.getId());
                            lastCheck = checks.isEmpty() ? null : checks.get(checks.size() - 1);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        
					jteOutput.writeContent("\n                        <td>\n                            ");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(url.getId());
					jteOutput.writeContent("\n                        </td>\n                        <td>\n                            <a");
					var __jte_html_attribute_0 = NamedRoutes.urlPath(url.getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" href=\"");
						jteOutput.setContext("a", "href");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("a", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(">");
					jteOutput.setContext("a", null);
					jteOutput.writeUserContent(url.getName());
					jteOutput.writeContent("</a>\n                        </td>\n                        <td>\n                            ");
					if (lastCheck != null) {
						jteOutput.writeContent("\n                                ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(lastCheck.getCreatedAt().toString());
						jteOutput.writeContent("\n                            ");
					}
					jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
					if (lastCheck != null) {
						jteOutput.writeContent("\n                                ");
						jteOutput.setContext("td", null);
						jteOutput.writeUserContent(lastCheck.getStatusCode().toString());
						jteOutput.writeContent("\n                            ");
					}
					jteOutput.writeContent("\n                        </td>\n                    </tr>\n                ");
				}
				jteOutput.writeContent("\n                </tbody>\n            </table>\n        </div>\n    </div>\n");
			}
		}, null, urlsPage, null);
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlsPage urlsPage = (UrlsPage)params.get("urlsPage");
		render(jteOutput, jteHtmlInterceptor, urlsPage);
	}
}

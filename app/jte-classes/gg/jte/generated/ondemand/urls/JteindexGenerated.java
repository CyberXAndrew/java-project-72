package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlsPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.NamedRoutes;
import java.sql.SQLException;
import java.util.List;
public final class JteindexGenerated {
	public static final String JTE_NAME = "urls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,3,4,5,6,9,9,9,11,11,13,13,14,14,16,16,16,16,17,17,17,21,21,35,35,37,46,46,46,46,46,46,46,46,46,46,48,48,48,51,51,51,51,51,51,51,51,51,51,51,51,54,54,55,55,55,56,56,59,59,60,60,60,61,61,64,64,69,69,69,70,70,70,9,9,9,9};
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
				for (var url : urlsPage.getUrls()) {
					jteOutput.writeContent("\n                    <tr>\n                        ");
					
                        List<UrlCheck> checks;
                        UrlCheck lastCheck;
                        try{
                            checks = UrlCheckRepository.getChecksByUrlId(url.getId());
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

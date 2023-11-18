package gg.jte.generated.ondemand.urls;
import hexlet.code.dto.urls.UrlPage;
import hexlet.code.model.UrlCheck;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.util.NamedRoutes;
import java.util.List;
import java.sql.SQLException;
public final class JteshowGenerated {
	public static final String JTE_NAME = "urls/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,2,3,4,5,6,8,8,8,10,10,12,12,13,13,15,15,15,20,20,20,24,24,24,28,28,28,33,33,33,33,33,33,33,33,33,50,57,57,57,57,57,57,57,57,58,58,59,59,62,62,62,65,65,65,68,68,68,71,71,71,74,74,74,77,77,77,80,80,81,81,86,86,87,87,87,88,88,88,8,8,8,8};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UrlPage urlPage) {
		jteOutput.writeContent("\n");
		gg.jte.generated.ondemand.layout.JtemainpageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\n    ");
				if (urlPage.getUrl() != null) {
					jteOutput.writeContent("\n    <div>\n        <h3>Сайт: ");
					jteOutput.setContext("h3", null);
					jteOutput.writeUserContent(urlPage.getUrl().getName());
					jteOutput.writeContent("</h3>\n        <table class=\"table table-bordered align-middle\">\n            <tbody>\n            <tr>\n                <td>Id</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlPage.getUrl().getId());
					jteOutput.writeContent("</td>\n            </tr>\n            <tr>\n                <td>Имя</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlPage.getUrl().getName());
					jteOutput.writeContent("</td>\n            </tr>\n            <tr>\n                <td>Дата создания</td>\n                <td>");
					jteOutput.setContext("td", null);
					jteOutput.writeUserContent(urlPage.getUrl().getCreatedAt().toString());
					jteOutput.writeContent("</td>\n            </tr>\n            </tbody>\n        </table>\n        <h3>Проверки</h3>\n        <form");
					var __jte_html_attribute_0 = NamedRoutes.urlChecksPath(urlPage.getUrl().getId());
					if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
						jteOutput.writeContent(" action=\"");
						jteOutput.setContext("form", "action");
						jteOutput.writeUserContent(__jte_html_attribute_0);
						jteOutput.setContext("form", null);
						jteOutput.writeContent("\"");
					}
					jteOutput.writeContent(" method=\"post\">\n            <button type=\"button\" class=\"btn btn-primary\">Запустить проверку</button>\n        </form>\n        <div>\n            <table class=\"table table-bordered table-hover align-middle\">\n                <caption class=\"caption-top\">Спискок проверок</caption>\n                <thead class=\"table-light\">\n                <tr>\n                    <td>Id</td>\n                    <td>Код ответа</td>\n                    <td>title</td>\n                    <td>h1</td>\n                    <td>description</td>\n                    <td>Дата проверки</td>\n                </tr>\n                </thead>\n                <tbody>\n                ");
					
                    List<UrlCheck> urlChecks;
                    try{
                        urlChecks = UrlCheckRepository.getChecksByUrlId(urlPage.getUrl().getId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    
					jteOutput.writeContent("\n                ");
					if (!urlChecks.isEmpty()) {
						jteOutput.writeContent("\n                    ");
						for (var check : urlChecks) {
							jteOutput.writeContent("\n                    <tr>\n                        <td>\n                            ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(check.getId());
							jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(check.getStatusCode());
							jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(check.getTitle());
							jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(check.getH1());
							jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(check.getDescription());
							jteOutput.writeContent("\n                        </td>\n                        <td>\n                            ");
							jteOutput.setContext("td", null);
							jteOutput.writeUserContent(check.getCreatedAt().toString());
							jteOutput.writeContent("\n                        </td>\n                    </tr>\n                    ");
						}
						jteOutput.writeContent("\n                ");
					}
					jteOutput.writeContent("\n                </tbody>\n            </table>\n        </div>\n    </div>\n    ");
				}
				jteOutput.writeContent("\n");
			}
		}, null, null, urlPage);
		jteOutput.writeContent("\n");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UrlPage urlPage = (UrlPage)params.get("urlPage");
		render(jteOutput, jteHtmlInterceptor, urlPage);
	}
}
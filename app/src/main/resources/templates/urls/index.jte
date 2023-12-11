@import hexlet.code.dto.urls.UrlsPage

@import hexlet.code.model.Url
@import hexlet.code.model.UrlCheck
@import hexlet.code.repository.UrlChecksRepository
@import hexlet.code.util.NamedRoutes
@import java.sql.SQLException
@import java.util.List


@param UrlsPage urlsPage

@template.layout.mainpage(urlsPage = urlsPage,
    content = @`
    @if(urlsPage.getFlash() != null)
    <div>
        <div class="alert ${urlsPage.getFlashType()} alert-dismissible fade show" role="alert">
            ${urlsPage.getFlash()}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
    @endif
    <div class="mx-auto">
        <h2>Сайты</h2>
        <div>
            <table class="table table-bordered table-hover align-middle">
                <thead>
                    <tr>
                        <td>Id</td>
                        <td>Имя</td>
                        <td>Последняя проверка</td>
                        <td>Код ответа</td>
                    </tr>
                </thead>
                <tbody>
                @for(Url url : urlsPage.getUrls())
                    <tr>
                        !{
                        List<UrlCheck> checks;
                        UrlCheck lastCheck;
                        try{
                            checks = UrlChecksRepository.getChecksByUrlId(url.getId());
                            lastCheck = checks.isEmpty() ? null : checks.get(checks.size() - 1);
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        <td>
                            ${url.getId()}
                        </td>
                        <td>
                            <a href="${NamedRoutes.urlPath(url.getId())}">${url.getName()}</a>
                        </td>
                        <td>
                            @if(lastCheck != null)
                                ${lastCheck.getCreatedAt().toString()}
                            @endif
                        </td>
                        <td>
                            @if(lastCheck != null)
                                ${lastCheck.getStatusCode().toString()}
                            @endif
                        </td>
                    </tr>
                @endfor
                </tbody>
            </table>
        </div>
    </div>
`
)

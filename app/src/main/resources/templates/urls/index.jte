@import hexlet.code.dto.urls.UrlsPage

@import hexlet.code.model.UrlCheck
@import hexlet.code.repository.UrlRepository
@import java.util.List


@param UrlsPage urlsPage

@template.layout.mainpage(urlsPage = urlsPage,
    content = @`
    @if(urlsPage.getFlash() != null)
    <div>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <p>${urlsPage.getFlash()}</p>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </div>
    @endif
    <h2>Сайты</h2>
    <div>
        <table>
            <thead>
                <tr>
                    <td>Id</td>
                    <td>Имя</td>
                    <td>Последняя проверка</td>
                    <td>Код ответа</td>
                </tr>
            </thead>
            <tbody>
            @for(var url : urlsPage.getUrls())
                <tr>
                    !{List<UrlCheck> checks = UrlRepository.getChecksByUrlId(url.getId());
                        UrlCheck lastCheck = checks.isEmpty() ? null : checks.get(checks.size() - 1);}
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        <a href="urls/${url.getId()}">${url.getName()}</a>
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
`
)

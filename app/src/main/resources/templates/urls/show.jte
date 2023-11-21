@import hexlet.code.dto.urls.UrlPage

@import hexlet.code.model.UrlCheck
@import hexlet.code.repository.UrlCheckRepository
@import hexlet.code.util.NamedRoutes
@import java.util.List
@import java.sql.SQLException

@param UrlPage urlPage

@template.layout.mainpage(urlPage = urlPage,
    content = @`
    @if(urlPage.getUrl() != null)
    <div>
        <h3>Сайт: ${urlPage.getUrl().getName()}</h3>
        <table class="table table-bordered align-middle">
            <tbody>
            <tr>
                <td>Id</td>
                <td>${urlPage.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${urlPage.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${urlPage.getUrl().getCreatedAt().toString()}</td>
            </tr>
            </tbody>
        </table>
        <div>
        <h3>Проверки</h3>
            <form action="${NamedRoutes.urlChecksPath(urlPage.getUrl().getId())}" method="post">
                <button type="submit" class="btn btn-primary">Запустить проверку</button>
            </form>
        </div>
        @if(urlPage.getFlash() != null)
            <div>
                <div class="alert ${urlPage.getFlashType()} alert-dismissible fade show" role="alert">
                    ${urlPage.getFlash()}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </div>
        @endif
        <div>
            <table class="table table-bordered table-hover align-middle">
                <caption class="caption-top">Список проверок</caption>
                <thead class="table-light">
                <tr>
                    <td>Id</td>
                    <td>Код ответа</td>
                    <td>title</td>
                    <td>h1</td>
                    <td>description</td>
                    <td>Дата проверки</td>
                </tr>
                </thead>
                <tbody>
                !{
                    List<UrlCheck> urlChecks;
                    try{
                        urlChecks = UrlCheckRepository.getChecksByUrlId(urlPage.getUrl().getId());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                @if(!urlChecks.isEmpty())
                    @for(var check : urlChecks)
                    <tr>
                        <td>
                            ${check.getId()}
                        </td>
                        <td>
                            ${check.getStatusCode()}
                        </td>
                        <td>
                            ${check.getTitle()}
                        </td>
                        <td>
                            ${check.getH1()}
                        </td>
                        <td>
                            ${check.getDescription()}
                        </td>
                        <td>
                            ${check.getCreatedAt().toString()}
                        </td>
                    </tr>
                    @endfor
                @endif
                </tbody>
            </table>
        </div>
    </div>
    @endif
`
)

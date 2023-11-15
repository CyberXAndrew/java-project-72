@import hexlet.code.dto.urls.UrlPage

@import hexlet.code.model.UrlCheck
@import hexlet.code.repository.UrlRepository
@import java.util.List

@param UrlPage page

@template.layout.mainpage(content = @`
    @if(page.getUrl() != null)
    <h1>Сайт: ${page.getUrl().getName()}</h1>
    <div>
        <table>
            <tbody>
            <tr>
                <td>Id</td>
                <td>${page.getUrl().getId()}</td>
            </tr>
            <tr>
                <td>Имя</td>
                <td>${page.getUrl().getName()}</td>
            </tr>
            <tr>
                <td>Дата создания</td>
                <td>${page.getUrl().getCreatedAt().toString()}</td>
            </tr>
            </tbody>
        </table>
        <h1>Проверки</h1>
        <form action="/urls/${page.getUrl().getId()}/checks" method="post">
            <button type="button" class="btn btn-primary" value="Запустить проверку"></button>
        </form>
        <div>
            <table>
                <thead>
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
                !{List<UrlCheck> urlChecks = UrlRepository.getChecksByUrlId(page.getUrl().getId());}
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

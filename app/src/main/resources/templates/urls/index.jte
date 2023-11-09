@import hexlet.code.dto.urls.UrlsPage

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
                    <td>
                        ${url.getId()}
                    </td>
                    <td>
                        ${url.getName()}
                    </td>
                    <td>
                        ${}
                    </td>
                    <td>
                        ${}
                    </td>
                </tr>
            @endfor
            </tbody>
        </table>
    </div>
`
)

@import hexlet.code.dto.urls.BuildUrlPage

@param BuildUrlPage buildUrlPage

@template.layout.mainpage(buildUrlPage = buildUrlPage,
    content = @`
        <div class="container-fluid p-5">
            <h4>Бесплатно проверяйте сайты на SEO пригодность</h4>
            @if(buildUrlPage.getFlash() != null)
            <div class="alert ${buildUrlPage.getFlashType()} alert-dismissible fade show" role="alert">
                ${buildUrlPage.getFlash()}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            @endif
            <div class="container-sm hstack gap-3">
                <form class="form-floating " action="/urls" method="post">
                    <input class="form-control" type="search" required name="url" placeholder="Введите ссылку">
                    <label>Введите url сайта: </label>
                    <input class="btn btn-primary" type="submit" value="Проверить">
                </form>
                <p class="text-muted mt-3">Пример: https://www.example.com</p>
            </div>
        </div>
`
)

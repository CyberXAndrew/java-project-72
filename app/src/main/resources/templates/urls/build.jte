@import hexlet.code.dto.urls.BuildUrlPage

@param BuildUrlPage buildUrlPage

@template.layout.mainpage(buildUrlPage = buildUrlPage,
    content = @`
        <div>
            <h1>Анализатор страниц</h1>
            <h3>Бесплатно проверяйте сайты на SEO пригодность</h3>
        </div>
        <main>
            @if(buildUrlPage.getFlash() != null)
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <p>${buildUrlPage.getFlash()}</p>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            @endif
            <form action="/urls" method="post">
                <label> Введите url сайта
                    <input type="search" required name="url" value="Ссылка">
                </label>
                    <input class="btn btn-primary" type="submit" value="Проверить">
            </form>
            <p>Пример: https://www.example.com</p>
        </main>
`

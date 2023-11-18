@import gg.jte.Content
@import hexlet.code.dto.urls.BuildUrlPage
@import hexlet.code.dto.urls.UrlPage
@import hexlet.code.dto.urls.UrlsPage

@param Content content
@param BuildUrlPage buildUrlPage = null
@param UrlsPage urlsPage = null
@param UrlPage urlPage = null

<!doctype html>
<html lang="en">
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Главная страница</title>
</head>
<body class="d-flex flex-column min-vh-100">
    <nav class="navbar navbar-expand-lg navbar-dark ps-3 border-bottom border-2" style="background-color: #FFD700;">
        <a class="navbar-brand text-dark ml-3" href="/">Анализатор страниц</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-collapse collapse show" id="navbarNav">
            <div class="navbar-nav">
                <a class="nav-link text-dark" href="/">Главная</a>
                <a class="nav-link text-dark" href="/urls">Сайты</a>
            </div>
        </div>
    </nav>
    <main class="flex-grow-1">
    @if(buildUrlPage != null)
        ${content}
    @elseif(urlsPage != null)
        ${content}
    @elseif(urlPage != null)
        ${content}
    @endif
    </main>
    <footer class="footer text-center mx-auto my-auto fixed-bottom bg-light-blue border-top border-4">
        <p>Created by<a href="https://github.com/CyberXAndrew"> Andrew B.</a></p>
    </footer>
</body>
</html>

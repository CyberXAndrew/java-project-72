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
    <title>главная страница</title>
</head>
<body>
    <nav>
        <a href="/">Анализатор страниц</a>
    </nav>

    @if(buildUrlPage != null)
        ${content}
    @endif
    @if(urlsPage != null)
        ${content}
    @endif
    @if(urlPage != null)
        ${content}
    @endif

    <footer>
        <p>Created by<a href="https://github.com/CyberXAndrew">Andrew B.</a></p>
    </footer>
</body>
</html>

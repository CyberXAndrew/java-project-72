@import hexlet.code.dto.urls.UrlPage

@param UrlPage page

@template.layout.mainpage(content = @`
    <h1>Сайт: ${page.getUrl().getName()}</h1>
    <div>
        <table>
            <thead>
            <tr>
                <td>Id</td>
                <td>Имя</td>
                <td>Дата создания</td>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        ${page.getUrl().getId()}
                    </td>
                    <td>
                        ${page.getUrl().getName()}
                    </td>
                    <td>
                        ${page.getUrl().getCreatedAt().toString()}
                    </td>
                </tr>
            </tbody>
        </table>
        <h1>Проверки</h1>
<%--        <button ></button> --%>
<%--        <div>--%>
<%--            <table>--%>
<%--                <thead>--%>
<%--                <tr>--%>
<%--                    <td>Id</td>--%>
<%--                    <td>Код ответа</td>--%>
<%--                    <td>title</td>--%>
<%--                    <td>h1</td>--%>
<%--                    <td>description</td>--%>
<%--                    <td>Дата проверки</td>--%>
<%--                </tr>--%>
<%--                </thead>--%>
<%--                <tbody>--%>
<%--                --%>
<%--                <tr>--%>
<%--                    <td>--%>
<%--                        ${page.getUrl().getId()}--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                        ${page.getUrl().getName()}--%>
<%--                    </td>--%>
<%--                    <td>--%>
<%--                        ${page.getUrl().getCreatedAt().toString()}--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--                </tbody>--%>
<%--        </div>--%>
    </div>
`
)

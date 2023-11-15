package hexlet.code.util;

public class NamedRoutes {
    public static String rootPath() {
        return "/";
    }
    public static String urlsPath() {
        return "/urls";
    }
    public static String urlPath(String id) {
        return "/urls/" + id;
    }
//    public static String urlPath(Long id) {return "/urls/" + id;}
    public static String urlChecksPath(String id) {
        return String.format("/urls/{%s}/checks", id);
    }
}

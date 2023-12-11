package hexlet.code.utils;

import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class TestUtils {

    public static void addUrl(HikariDataSource dataSource, String url) throws SQLException {
        var sql = "INSERT INTO urls (name, created_at) VALUES (?, '2023-08-27 14:20:19.13')";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url);
            var resultSet = stmt.executeUpdate();
        }
    }

    public static Map<String, Object> getUrlByName(HikariDataSource dataSource, String url) throws SQLException {
        var result = new HashMap<String, Object>();
        var sql = "SELECT * FROM urls WHERE name = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, url);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                result.put("id", resultSet.getLong("id"));
                result.put("name", resultSet.getString("name"));
                return result;
            }

            return null;
        }
    }

    public static void addUrlCheck(HikariDataSource dataSource, long urlId) throws SQLException {
        var sql = "INSERT INTO url_checks (url_id, status_code, title, description, h1, created_at)"
                + "VALUES (?, 200, 'en title', 'en description', 'en h1', '2021-09-27 14:20:19.13')";

        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeUpdate();
        }
    }

    public static Map<String, Object> getUrlCheck(HikariDataSource dataSource, long urlId) throws SQLException {
        var result = new HashMap<String, Object>();
        var sql = "SELECT * FROM url_checks WHERE url_id = ?";
        try (var conn = dataSource.getConnection();
             var stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, urlId);
            var resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                result.put("id", resultSet.getLong("id"));
                result.put("url_id", resultSet.getLong("url_id"));
                result.put("status_code", resultSet.getInt("status_code"));
                result.put("title", resultSet.getString("title"));
                result.put("h1", resultSet.getString("h1"));
                result.put("description", resultSet.getString("description"));
                return result;
            }

            return null;
        }
    }
}

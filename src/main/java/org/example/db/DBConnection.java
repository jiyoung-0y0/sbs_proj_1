package org.example.db;

import java.sql.*;
import java.util.*;

public class DBConnection {
    private Connection connection;
    public static String DB_NAME;
    public static String DB_USER;
    public static String DB_PASSWORD;
    public static int DB_PORT;

    public void connect() {
        String url = "jdbc:mysql://localhost:" + DB_PORT + "/" + DB_NAME + "?serverTimezone=Asia/Seoul";
        String user = DB_USER;
        String password = DB_PASSWORD;
        String driverName = "com.mysql.cj.jdbc.Driver";

        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.printf("[SQL 예외] : %s\n", e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.printf("[드라이버 클래스 로딩 예외] : %s\n", e.getMessage());
        }
    }

    public int insert(String sql) {
        int generatedKey = -1;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
        }

        return generatedKey;
    }

    public int insertWithParams(String sql, Object[] params) {
        int id = -1;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
        }

        return id;
    }

    public List<Map<String, Object>> selectRowsWithParams(String sql, Object[] params) {
        List<Map<String, Object>> rows = new ArrayList<>();

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    String columnName = metaData.getColumnName(columnIndex);
                    Object value = rs.getObject(columnName);
                    row.put(columnName, value);
                }
                rows.add(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
        }

        return rows;
    }

    public List<Map<String, Object>> selectRows(String sql) {
        List<Map<String, Object>> rows = new ArrayList<>();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnCount++) {
                    String columnName = metaData.getColumnName(columnIndex);
                    Object value = rs.getObject(columnName);
                    row.put(columnName, value);
                }
                rows.add(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
        }

        return rows;
    }

    public int delete(String sql) {
        int affectedRows = 0;

        try {
            Statement stmt = connection.createStatement();
            affectedRows = stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
        }

        return affectedRows;
    }

    public int update(String sql) {
        int affectedRows = 0;

        try {
            Statement stmt = connection.createStatement();
            affectedRows = stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            System.err.printf("[SQL 예외, SQL : %s] : %s\n", sql, e.getMessage());
        }

        return affectedRows;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.printf("[SQL 예외] : %s\n", e.getMessage());
            }
        }
    }
}

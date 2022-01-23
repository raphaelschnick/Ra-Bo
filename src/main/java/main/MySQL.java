package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {

    private Connection connection;

    public MySQL() {
        this.connect();
    }

    public Connection connect() {
        try {
            String url = Secrets.MYSQL_URL;
            String password = Secrets.MYSQL_PASSWORD;
            String user = Secrets.MYSQL_USER;
            Connection con = DriverManager.getConnection(url, user, password);
            this.setConnection(con);
            System.out.println("MySQL connected!");
            return con;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void disconnect() {
        try {
            this.getConnection().close();
            this.setConnection(null);
            System.out.println("MySQL disconnected!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void init() {
        this.execute("CREATE TABLE IF NOT EXISTS config (" +
                "id VARCHAR(100) NOT NULL PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "prefix VARCHAR(10) DEFAULT '!'" +
                ");");
    }

    public ResultSet executeQuery(String query) {
        try {
            Statement statement = this.getConnection().createStatement();
            return statement.executeQuery(query);
        } catch (SQLException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void execute(String query) {
        try {
            Statement statement = this.getConnection().createStatement();
            statement.execute(query);
        } catch (SQLException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                return connection;
            } else {
                return this.connect();
            }
        } catch (SQLException | NullPointerException ex) {
            System.out.println(ex.getMessage());
        }
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

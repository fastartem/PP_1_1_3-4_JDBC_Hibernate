package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/users_schema";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "11111";

    //JDBC
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    //Hibernate
    private static final SessionFactory sessionFactory;

    static {
        sessionFactory = new Configuration()
                .setProperty("hibernate.connection.url", DB_URL)
                .setProperty("hibernate.connection.username", DB_USERNAME)
                .setProperty("hibernate.connection.password", DB_PASSWORD)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

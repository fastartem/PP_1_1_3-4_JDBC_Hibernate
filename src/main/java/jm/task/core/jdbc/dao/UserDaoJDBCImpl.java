package jm.task.core.jdbc.dao;

import java.sql.*;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

   private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {

            String createTableQuery = "CREATE TABLE IF NOT EXISTS Users ("
                    + "id BigInt  PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(50) NOT NULL,"
                    + "lastName VARCHAR(50) NOT NULL,"
                    + "age TINYINT NOT NULL)";

            statement.executeUpdate(createTableQuery);
            System.out.println("Таблица Users успешно создана или уже существует");
        } catch (SQLException e) {
            System.err.println("Ошибка при создании таблицы Users: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String dropTableQuery = "DROP TABLE IF EXISTS Users";
            statement.executeUpdate(dropTableQuery);
            System.out.println("Таблица Users успешно удалена или не существует");
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении таблицы Users: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String insertQuery = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Пользователь успешно добавлен в таблицу User");
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String deleteQuery = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery);) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь успешно удален");
            } else {
                System.out.println("Пользователь с указанным идентификатором не найден");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT * FROM users";
        try (PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String cleanTableQuery = "TRUNCATE TABLE Users";
            statement.executeUpdate(cleanTableQuery);
            System.out.println("Таблица Users успешно очищена");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке таблицы Users: " + e.getMessage());
        }
    }
}

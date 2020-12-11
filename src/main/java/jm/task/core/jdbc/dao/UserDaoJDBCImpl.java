package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private String sqlCommand;
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        sqlCommand = "CREATE TABLE IF NOT EXISTS user " +
                "(id BigInt PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20) , lastName VARCHAR(20), age TINYINT)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            // создание таблицы
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void dropUsersTable() {
        sqlCommand = "DROP TABLE IF EXISTS user";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        createUsersTable();
        sqlCommand = "INSERT user(name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        sqlCommand = "DELETE FROM user where id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand);) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        sqlCommand = "Select * from user";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()){
                list.add(new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (ClassNotFoundException | SQLException throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        sqlCommand = "TRUNCATE Table user";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(sqlCommand);
        } catch (ClassNotFoundException | SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}

package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDaoJDBCImpl userdao = new UserDaoJDBCImpl();
    public void createUsersTable() {
        userdao.createUsersTable();

    }

    public void dropUsersTable() {
        userdao.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        userdao.saveUser(name, lastName,age);
        System.out.printf("User с именем – %s добавлен в базу данных \n", name);
    }

    public void removeUserById(long id) {
        userdao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userdao.getAllUsers();
    }

    public void cleanUsersTable() {
        userdao.cleanUsersTable();

    }
}

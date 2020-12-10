package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("James", "Kirk", (byte) 32);
        userService.saveUser("Spock", "Spock", (byte) 104);
        userService.saveUser("Nyota", "Uhura", (byte) 26);
        userService.saveUser("Leonard", "McCoy", (byte) 38);
        (userService.getAllUsers()).forEach(user -> System.out.println(user.toString()));
        userService.cleanUsersTable();
        userService.dropUsersTable();
        System.exit(0);
    }
}
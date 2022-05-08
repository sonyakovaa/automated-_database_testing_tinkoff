package ru.tinkoff.qa.jdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.tinkoff.qa.BeforeCreator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ZooJdbcTests {

    static Connection connection;
    static Statement statement;

    @BeforeAll
    static void init() {
        BeforeCreator.createData();
        connection = JdbcConnectionCreator.createConnection();
        try {
            statement = connection.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * В таблице public.animal ровно 10 записей
     */
    @Test
    public void countRowAnimal() {
        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT COUNT(*) FROM public.animal");
            resultSet.next();
            Assertions.assertEquals(10, resultSet.getInt("count(*)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * В таблицу public.animal нельзя добавить строку с индексом от 1 до 10 включительно
     */
    @Test
    public void insertIndexAnimal() {
        try {
            StringBuilder sql = new StringBuilder();
            for (int index = 1; index <= 10; index++) {
                sql.append("INSERT INTO public.animal VALUES (").append(index).append(", 'Иусинка', 2, 1, 1, 1);\n");
            }
            int resultSet = statement.executeUpdate(sql.toString());
            Assertions.assertEquals(0, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * В таблицу public.workman нельзя добавить строку с name = null
     */
    @Test
    public void insertNullToWorkman() {
        try {
            int resultSet = statement.executeUpdate("INSERT INTO public.workman VALUES (11, null, 23, 1)");
            Assertions.assertEquals(0, resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Если в таблицу public.places добавить еще одну строку, то в ней будет 6 строк
     */
    @Test
    public void insertPlacesCountRow() {
        try {
            String sql = "INSERT INTO public.places VALUES(6, 1, 185, 'Загон 6');";
            statement.executeUpdate(sql);
            ResultSet resultSet = statement
                    .executeQuery("SELECT COUNT(*) FROM public.places");
            resultSet.next();
            Assertions.assertEquals(6, resultSet.getInt("count(*)"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * В таблице public.zoo всего три записи с name 'Центральный', 'Северный', 'Западный'
     */
    @Test
    public void countRowZoo() {
        try {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM public.zoo");

            String[] zoo_name = new String[] {"Центральный", "Северный", "Западный"};
            for (int i = 0; i < 3; i++) {
                resultSet.next();
                Assertions.assertEquals(zoo_name[i], resultSet.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

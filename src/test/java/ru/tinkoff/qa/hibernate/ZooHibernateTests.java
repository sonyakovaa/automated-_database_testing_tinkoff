package ru.tinkoff.qa.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.qa.BeforeCreator;
import ru.tinkoff.qa.hibernate.models.Animal;
import ru.tinkoff.qa.hibernate.models.Places;
import ru.tinkoff.qa.hibernate.models.Workman;
import ru.tinkoff.qa.hibernate.models.Zoo;

public class ZooHibernateTests {

    SessionFactory sessionFactory;
    Session session;

    @BeforeAll
    static void init() {
        BeforeCreator.createData();
    }

    @BeforeEach
    void getSession() {
        sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        session = sessionFactory.openSession();
    }

    /**
     * В таблице public.animal ровно 10 записей
     */
    @Test
    public void countRowAnimal() {
        int result = session.createNativeQuery("SELECT * FROM public.animal", Animal.class)
                .getResultList()
                .size();
        Assertions.assertEquals(10, result);
    }

    /**
     * В таблицу public.animal нельзя добавить строку с индексом от 1 до 10 включительно
     */
    @Test
    public void insertIndexAnimal() {
        for (int index = 1; index <= 10; index++) {
            new DbClient().insertAnimal(index, "Бусинка", 2, 1, 1, 1);
        }
    }

    /**
     * В таблицу public.workman нельзя добавить строку с name = null
     */
    @Test
    public void insertNullToWorkman() {
        new DbClient().insertWorkman(7, null, 23, 1);
    }

    /**
     * Если в таблицу public.places добавить еще одну строку, то в ней будет 6 строк
     */
    @Test
    public void insertPlacesCountRow() {
        new DbClient().insertPlace(6, 1, 185, "Загон 6");

        int result = session.createNativeQuery("SELECT * FROM public.places", Places.class)
                .getResultList()
                .size();
        Assertions.assertEquals(6, result);
        session.close();
    }

    /**
     * В таблице public.zoo всего три записи с name 'Центральный', 'Северный', 'Западный'
     */
    @Test
    public void countRowZoo() {
        String[] zoo_name = new String[]{"Центральный", "Северный", "Западный"};
        for (int i = 0; i < 3; i++) {
            String result = session.createNativeQuery("SELECT * from public.zoo WHERE \"name\" LIKE '" + zoo_name[i] + "'",
                            Zoo.class)
                    .getResultList()
                    .get(0)
                    .getName();
            Assertions.assertEquals(zoo_name[i], result);
        }

        int resultRowZoo = session.createNativeQuery("SELECT * FROM public.zoo", Zoo.class)
                .getResultList()
                .size();
        Assertions.assertEquals(3, resultRowZoo);
    }
}

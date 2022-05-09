package ru.tinkoff.qa.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.tinkoff.qa.hibernate.models.Animal;
import ru.tinkoff.qa.hibernate.models.Places;
import ru.tinkoff.qa.hibernate.models.Workman;

import java.util.ArrayList;

public class DbClient {

    public void insertAnimal(int id, String name, int age, int type, int sex, int place) {
        Animal animal = new Animal();
        animal.setId(id);
        animal.setName(name);
        animal.setAge(age);
        animal.setType(type);
        animal.setSex(sex);
        animal.setPlace(place);

        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(animal);
        session.getTransaction().commit();
        session.close();
    }

    public void insertWorkman(int id, String name, int age, int position) {
        Workman workman = new Workman();
        workman.setId(id);
        workman.setName(name);
        workman.setAge(age);
        workman.setPosition(position);

        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(workman);
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        session.close();
    }


    public void insertPlace(int id, int row, int placeNum, String name) {
        Places places = new Places();
        places.setId(id);
        places.setRow(row);
        places.setPlaceNum(placeNum);
        places.setName(name);

        SessionFactory sessionFactory = HibernateSessionFactoryCreator.createSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(places);
            session.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        session.close();
    }
}

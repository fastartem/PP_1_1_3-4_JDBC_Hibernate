package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        new UserDaoJDBCImpl().createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        new UserDaoJDBCImpl().dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
            System.out.println("Пользователь успешно добавлен в таблицу User");
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                session.getTransaction().commit();
                System.out.println("Пользователь успешно удален");
            } else {
                System.out.println("Пользователь с указанным идентификатором не найден");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            users = session.createQuery("FROM User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка пользователей: " + e.getMessage());
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
            System.out.println("Таблица Users успешно очищена");
        } catch (Exception e) {
            System.err.println("Ошибка при очистке таблицы Users: " + e.getMessage());
        }
    }
}

package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }

   @Override
   public User getUserByCar(String model, int series) {
      List <Car> car = sessionFactory.getCurrentSession()
              .createQuery("from Car where model = \'" + model + "\' and series = " + series, Car.class)
              .getResultList();
      if (car.isEmpty()) {
         return null;
      } else {
         User user = sessionFactory.getCurrentSession()
                 .createQuery("from User where car = " + car.get(0).getId(), User.class)
                 .getSingleResult();
         return user;
      }
   }

}

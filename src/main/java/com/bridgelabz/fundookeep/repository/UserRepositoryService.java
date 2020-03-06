package com.bridgelabz.fundookeep.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundookeep.dao.User;
import com.bridgelabz.fundookeep.dto.RegistrationDTO;

public class UserRepositoryService {

//	@Autowired
//	private SessionFactory factory;
//	
//	public User findUserByEmail(String emailAddress) {
//		User user = null;
//		try(Session session = factory.openSession()){
//			
//			Query<User> hql = session.createQuery("from User where emailAddress = :emailAddress");
//			
//			hql.setParameter("emailAddress", emailAddress);
//			
//			user = hql.uniqueResult();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	public User findUserByMobile(Long mobile) {
//		User user = null;
//		try(Session session = factory.openSession()){
//			
//			Query<User> hql = session.createQuery("from User where mobile = :mobile");
//			
//			hql.setParameter("mobile", mobile);
//			
//			user = hql.uniqueResult();
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return user;
//	}
//	
//	public int saveUser(RegistrationDTO register) {
//		User user = new User(register);
//		int pk = 0;
//		Transaction transaction = null;
//		try(Session session = factory.openSession()){
//			transaction = session.beginTransaction();
//			pk = (int)session.save(user);
//			transaction.commit();
//		}
//		catch (Exception e) {
//			transaction.rollback();
//			e.printStackTrace();
//		}
//		return pk;
//	}
//	
//	public void verifyStatus(Long id) {
//		
//		Transaction transaction = null;
//		try(Session session = factory.openSession()){
//			transaction = session.beginTransaction();
//			User user = session.get(User.class, id);
//			user.setUserVerified(true);
//			session.update(user);
//			transaction.commit();
//		}
//		catch (Exception e) {
//			transaction.rollback();
//			e.printStackTrace();
//		}
//	}
}

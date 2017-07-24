package db;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class SokobanDBManager {
	private static SokobanDBManager instance = new SokobanDBManager();

	public static SokobanDBManager getInstance() {
		return instance;
	}

	private SessionFactory factory;

	private SokobanDBManager() {
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}

	public String getLevelSoluion(String lvl_name) {
		Session session = factory.openSession();
		try {
			Criteria cr = session.createCriteria(LevelDescription.class);
			cr.add(Restrictions.like("level_name", lvl_name));
			List<LevelDescription> lvls = cr.list();
			System.out.println(lvls);
			if (lvls.size() == 0)
				return null;
			else
				return lvls.get(0).getLevel_solution();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public List<Player> getAllPlayers() {
		Session session = factory.openSession();
		Query<Player> query = session.createQuery("from Players");
		List<Player> list = query.list();
		return list;
	}

	public List<LevelDescription> getAllLevels() {
		Session session = factory.openSession();
		Query<LevelDescription> query = session.createQuery("from Levels");
		List<LevelDescription> list = query.list();
		return list;
	}

	@SuppressWarnings({ "unchecked", "finally" })
	public List<Player_solved_level> getScores(List<String> specificfields, List<String> orderings) {
		Session session = factory.openSession();
		List<Player_solved_level> list = null;
		try {
			Criteria cr = session.createCriteria(Player_solved_level.class);
			for (int i = 0; i < specificfields.size(); i += 2) {
				String fieldname = specificfields.get(i);
				String fieldspecific = specificfields.get(i + 1);

				cr.add(Restrictions.like(fieldname, fieldspecific));
			}
			for (String orderfield : orderings)
				cr.addOrder(Order.asc(orderfield));

			list = cr.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
			return list;
		}
	}

	public void registerHighscore(Player_solved_level highscore){
		Session session = null;
		Transaction tx = null;
		try{
			session = factory.openSession();
			tx = session.beginTransaction();
			
			Query<LevelDescription> query = session.createQuery("from Levels");
			List<LevelDescription> list = query.list();
			LevelDescription lvl = new LevelDescription(highscore.get_level_name(),highscore.get_solution());
			
			
			

			if(list.contains(lvl)){
				LevelDescription lvl_in_db = list.get(list.indexOf(lvl));
				//if the current solution is better then that in the database or there is no solution for that level
				if(lvl_in_db.getLevel_solution() == null || lvl_in_db.getLevel_solution().length() > lvl.getLevel_solution().length()){
					lvl_in_db.setLevel_solution(lvl.getLevel_solution());
					session.saveOrUpdate(lvl_in_db); //update solution
				}
			}
			else //this level is not in the database
				session.saveOrUpdate(lvl);
			
			Player plyr = new Player(highscore.get_Player_name());

			session.saveOrUpdate(plyr);
			session.saveOrUpdate(highscore);
			tx.commit();
		} catch (HibernateException ex){
			if( tx != null)
				tx.rollback();
			ex.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
	}

	public void add(Object obj) {
		Session session = null;
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();

			session.saveOrUpdate(obj);
			tx.commit();
		} catch (HibernateException ex) {
			if (tx != null)
				tx.rollback();
			System.out.println(ex.getMessage());
		} finally {
			if (session != null)
				session.close();
		}
	}

	public void close() {
		factory.close();
	}

}

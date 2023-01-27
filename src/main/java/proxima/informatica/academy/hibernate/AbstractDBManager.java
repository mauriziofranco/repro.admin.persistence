/**
 *
 */
package proxima.informatica.academy.hibernate;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import proxima.informatica.academy.dto.AbstractCommonDto;
import proxima.informatica.academy.dto.SurveyDto;

/**
 * @author maurizio.franco@ymail.com
 *
 */
public class AbstractDBManager {
	
	private final static Logger logger = LoggerFactory.getLogger(AbstractDBManager.class);
	
    public static boolean deleteAll (Class entityClass) {		
		logger.debug("AbstractDBManager.deleteAll - START");
		boolean returnFalse = false ;
		try {
			Session session = DBManager.getSessionFactory().openSession();
			session.beginTransaction();
			Query<SurveyDto> query = session.createQuery("delete from " + entityClass.getSimpleName());
			query.executeUpdate();
			session.getTransaction().commit();
			session.close();	
			returnFalse = true ;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnFalse = false ;
		}
		logger.debug("AbstractDBManager.deleteAll - END");   
		return returnFalse ;
		
	}
    
    public static int insert (AbstractCommonDto itemToInsert) {
		
		logger.debug("AbstractDBManager.insert - START - itemToInsert: " + itemToInsert);
		int id_inserted_value = 0;
		try {
			Session session = DBManager.getSessionFactory().openSession();
			session.beginTransaction();
			Object generatedIdentifier = session.save(itemToInsert);
			id_inserted_value = ((Integer)generatedIdentifier).intValue();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {	
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.debug("AbstractDBManager.insert - END - id_inserte_value: " + id_inserted_value);
		return id_inserted_value;
		
	}
    
    public static AbstractCommonDto selectById (int id, Class entityClass) {
		
		logger.debug("AbstractDBManager.selectById - START id: " + id);
		AbstractCommonDto itemToReturn = null;
		try {
			Session session = DBManager.getSessionFactory().openSession();
			session.beginTransaction();
			itemToReturn = session.get(entityClass, id);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		logger.debug("AbstractDBManager.selectById -END itemToReturn: " + itemToReturn);
		return itemToReturn;
	}

}

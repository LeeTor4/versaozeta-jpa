package com.zeta.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf = null;
	
	public static EntityManager getEntityManager() {
		
		if(emf == null) {
		   emf = Persistence.createEntityManagerFactory("versaozeta");
		}
		
		return emf.createEntityManager();
	}
	
	public static void fecha() {
		if(emf != null) {
			emf.close();	
		}
		
	}
}

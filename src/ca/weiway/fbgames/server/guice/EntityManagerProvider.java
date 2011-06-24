package ca.weiway.fbgames.server.guice;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class EntityManagerProvider implements Provider<EntityManager> {

	private final EntityManagerFactory emf;

	public EntityManagerProvider() {
		emf = Persistence.createEntityManagerFactory("transactions-optional");
	}

	@Override
	public EntityManager get() {
		return emf.createEntityManager();
	}

}


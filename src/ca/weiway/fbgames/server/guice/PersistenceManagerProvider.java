package ca.weiway.fbgames.server.guice;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PersistenceManagerProvider implements Provider<PersistenceManager> {

	private final PersistenceManagerFactory pmf;

	public PersistenceManagerProvider() {
		pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	}

	@Override
	public PersistenceManager get() {
		return pmf.getPersistenceManager();
	}

}

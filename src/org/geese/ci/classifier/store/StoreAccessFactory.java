package org.geese.ci.classifier.store;

import org.geese.ci.classifier.store.mongodb.MongoDBAccess;
import org.geese.ci.classifier.store.mysql.MySQLAccess;
import org.geese.ci.classifier.store.posix.PosixAccess;
import org.geese.config.Profile;

public class StoreAccessFactory {

	private enum StoreType {

		MYSQL {
			@Override
			StoreAccess getAccess(Profile profile) {
				return new MySQLAccess(profile);
			}
		},
		MONGODB {
			@Override
			StoreAccess getAccess(Profile profile) {
				return new MongoDBAccess(profile);
			}
		},
		POSIX {
			@Override
			StoreAccess getAccess(Profile profile) {
				return new PosixAccess(profile);
			}
		};

		abstract StoreAccess getAccess(Profile profile);
	}

	public static StoreAccess create(Profile profile) {
		String storeType = profile.getStoreType();

		if (storeType == null) {
			throw new IllegalArgumentException("Store type is null.");
		}

		StoreType type = StoreType.valueOf(storeType.toUpperCase());

		return type.getAccess(profile);
	}
}

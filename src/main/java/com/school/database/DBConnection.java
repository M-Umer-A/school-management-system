
package com.school.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class DBConnection {

    private static Datastore datastore;

    public static Datastore getDatastore() {
        if (datastore == null) {
            MongoClient client = MongoClients.create(

                    "mongodb+srv://umerashrafuddin_db_user:<db_password>@smscluster.tnpbuvd.mongodb.net/"

            );

            datastore = Morphia.createDatastore(client, "schoolDB");
            datastore.getMapper().mapPackage("com.school.model");
            datastore.ensureIndexes();
        }
        return datastore;
    }
}

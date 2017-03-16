
package com.siva.ecmrc;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;

/**
 *
 * @author siva
 */
public class MongoUtil {
    private static final String MONGO_HOST ="";
    private static final int MONGO_PORT = 0;
    private static final String USER = "";
    private static final String PWD = "";
    private static final String DATABASE = "";
    
    private static final MongoClient mongoClient;
    
    private MongoUtil(){}
    
    static {
        final MongoCredential credential = MongoCredential.createCredential(USER, DATABASE, PWD.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(MONGO_HOST, MONGO_PORT),Arrays.asList(credential)); 
    }
    
    public static MongoClient getMongoClient() {
        return mongoClient;
    }
    
    public static MongoDatabase getDatabase(String database){
        return mongoClient.getDatabase(database);
    }
}

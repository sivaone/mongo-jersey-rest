package com.siva.ecmrc.rest;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import com.siva.ecmrc.MongoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.bson.Document;

/**
 *
 * @author siva
 */
@Path("/users")
public class UserController {

	private static final String DB = "ecmrc_users";

    @GET
    @Path("/echo/{msg}")
    public String showMsg(@PathParam("msg") String msg) {
        return "Hello world! "+msg;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll(){
        MongoDatabase db = MongoUtil.getDatabase(DB);
        MongoCollection<Document> users = db.getCollection("users");
        
        Document findAllQuery = new Document();
        
        Iterable<Document> itr = users.find(findAllQuery);
        List<Document> usersList = new ArrayList<>();
        itr.forEach(usersList::add);

        return JSON.serialize(usersList);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String create(String user){
        MongoDatabase db = MongoUtil.getDatabase(DB);
        MongoCollection<Document> users = db.getCollection("users");
        
        Document d = Document.parse(user);
        users.insertOne(d);
        return JSON.serialize("success");
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String update(String user){
        MongoDatabase db = MongoUtil.getDatabase(DB);
        MongoCollection<Document> users = db.getCollection("users");
        
        Document d = Document.parse(user);
        
        Document updateQuery = new Document("login", d.get("login"));
        
        UpdateResult uresult = users.updateOne(updateQuery, new Document("$set", d));
        
        Map<String, Long> map = ImmutableMap.of("updatedCount", uresult.getModifiedCount());
        return JSON.serialize(map);
    }
    
    @DELETE
    @Path("/{login}")
    public String delete(@PathParam("login") String login) {
        MongoDatabase db = MongoUtil.getDatabase(DB);
        MongoCollection<Document> users = db.getCollection("users");
        
        DeleteResult dresult = users.deleteOne(new Document("login", login));
        
        Map<String, Long> map = ImmutableMap.of("deltedCount", dresult.getDeletedCount());
        
        return JSON.serialize(map);      
    }
    
    
}

package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;

public class UserDao extends BaseDao<UserDto> {

  private static UserDao instance;



  private UserDao(MongoCollection<Document> collection){
    super(collection);
  }

  public static UserDao getInstance(){
    if(instance != null){
      return instance;
    }
    instance = new UserDao(MongoConnection.getCollection("UserDao"));
    return instance;
  }

  public static UserDao getInstance(MongoCollection<Document> collection){
    instance = new UserDao(collection);
    return instance;
  }

  @Override
  public void put(UserDto messageDto) {
    // TODO
    this.collection.insertOne(messageDto.toDocument());

  }

  public List<UserDto> query(Document filter){
    // TODO
    FindIterable<Document> foundDoc = collection.find((Bson) filter);
    List<Document> list =foundDoc.into(new ArrayList<>());
    List<UserDto> result = list.stream().map(UserDto::fromDocument).collect(Collectors.toList());

    return result;
  }

}

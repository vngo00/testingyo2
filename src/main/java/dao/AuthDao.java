package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dto.AuthDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.conversions.Bson;

public class AuthDao extends BaseDao<AuthDto> {

  private static AuthDao instance;

  private AuthDao(MongoCollection<Document> collection){
    super(collection);
  }

  public static AuthDao getInstance(){
    if(instance != null){
      return instance;
    }
    instance = new AuthDao(MongoConnection.getCollection("AuthDao"));
    return instance;
  }

  public static AuthDao getInstance(MongoCollection<Document> collection){
    instance = new AuthDao(collection);
    return instance;
  }

  @Override
  public void put(AuthDto authDto) {
    // TODO
    collection.insertOne(authDto.toDocument());
  }

  @Override
  public List<AuthDto> query(Document filter) {
    // TODO
    FindIterable<Document> foundDoc = collection.find((Bson) filter);
    List<Document> list = foundDoc.into(new ArrayList<>());
    List<AuthDto> result = list.stream().map(AuthDto::fromDocument).collect(Collectors.toList());
    return result;
  }
}

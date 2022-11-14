package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dto.MessageDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;

// TODO fill this out
public class MessageDao extends BaseDao<MessageDto> {

  private static MessageDao instance;

  private MessageDao(MongoCollection<Document> collection){
    super(collection);
  }

  public static MessageDao getInstance(){
    if(instance != null){
      return instance;
    }
    instance = new MessageDao(MongoConnection.getCollection("MessageDao"));
    return instance;
  }

  public static MessageDao getInstance(MongoCollection<Document> collection){
    instance = new MessageDao(collection);
    return instance;
  }

  @Override
  public void put(MessageDto messageDto) {
    // TODO
    collection.insertOne(messageDto.toDocument());
  }

  public List<MessageDto> query(Document filter){
    // TODO
    FindIterable<Document> foundDoc = collection.find((Bson) filter);
    List<Document> list = foundDoc.into(new ArrayList<>());
    List<MessageDto> result = list.stream().map(MessageDto::fromDocument).collect(Collectors.toList());
    return result;
  }

}

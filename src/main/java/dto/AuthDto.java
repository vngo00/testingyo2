package dto;

import org.bson.Document;

import java.util.Map;

public class AuthDto extends BaseDto{

  private String userName;
  private Long expireTime;
  private String hash;

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  public void setExpireTime(Long expireTime) {
    this.expireTime = expireTime;
  }

  public String getUserName() {
    return userName;
  }

  public Long getExpireTime() {
    return expireTime;
  }

  public String getHash() {
    return hash;
  }

  @Override
  public Document toDocument() {
    // TODO
    return new Document()
            .append("userName", this.userName)
            .append("expireTime", this.expireTime)
            .append("hash", this.hash);

  }

  public static AuthDto fromDocument(Document document){
    // TODO
    AuthDto auth = new AuthDto();
    auth.setExpireTime(document.getLong("expireTime"));
    auth.setUserName(document.getString("userName"));
    auth.setHash(document.getString("hash"));
    return auth;
  }
}

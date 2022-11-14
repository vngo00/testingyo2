package dto;

import org.bson.Document;

import javax.print.Doc;

public class UserDto extends BaseDto{

  private String userName;
  private String password;

  public UserDto() {
    super();
  }

  public UserDto(String uniqueId) {
    super(uniqueId);
  }

  public String getPassword() {
    return password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Document toDocument(){
    // TODO

    return new Document()
            .append("userName", this.userName)
            .append("password", this.password);
  }

  public static UserDto fromDocument(Document match) {
    // TODO
    UserDto userDto = new UserDto();
    userDto.setUserName(match.getString("userName"));
    userDto.setPassword(match.getString("password"));
    return userDto;
  }
}

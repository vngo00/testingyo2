package handler;

import dao.UserDao;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import javax.print.Doc;
import java.util.List;

public class CreateUserHandler implements BaseHandler{
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        RestApiAppResponse res;
        UserDto userDto = (UserDto) GsonTool.gson.fromJson(request.getBody(), UserDto.class);
        UserDao userDao = UserDao.getInstance();

        Document queryDoc = new Document("userName", userDto.getUserName());
        List<UserDto> userDtoList = userDao.query(queryDoc);
        if(userDtoList.size() != 0){
            res = new RestApiAppResponse(false, null, "Username already taken");

        }
        else{
            String unhashedPas = userDto.getPassword();
            userDto.setPassword(DigestUtils.sha256Hex(unhashedPas));
            userDao.put(userDto);
            res = new RestApiAppResponse(true, null, "User Created");
        }
        return new HttpResponseBuilder().setStatus(StatusCodes.OK).setBody(res);
    }
}

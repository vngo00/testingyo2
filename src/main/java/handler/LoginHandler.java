package handler;

import dao.AuthDao;
import dao.UserDao;
import dto.AuthDto;
import dto.UserDto;
import org.apache.commons.codec.digest.DigestUtils;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;


import java.time.Instant;
import java.util.List;

public class LoginHandler implements BaseHandler{
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder res = new HttpResponseBuilder();
        Login currUser = (Login)GsonTool.gson.fromJson(request.getBody(),Login.class);
        UserDao userDao = UserDao.getInstance();

        Document document = new Document();
        document.append("userName", currUser.getUserName());
        document.append("password", DigestUtils.sha256Hex(currUser.getPassword()));
        List<UserDto> list = userDao.query(document);

        if(list.size() == 0){
            return res.setStatus(StatusCodes.UNAUTHORIZED);
        }

        AuthDao authDao = AuthDao.getInstance();
        AuthDto authDto = new AuthDto();

        authDto.setUserName(currUser.getUserName());


        String hash = DigestUtils.sha256Hex(authDto.getUserName() + currUser.hashCode());

        authDto.setHash(hash);
        authDto.setExpireTime(System.currentTimeMillis() + 100000L);

        authDao.put(authDto);
        res.setStatus(StatusCodes.OK);
        res.setHeader("auth",hash);
        res.setHeader("Set-Cookie","auth="+hash);

        return res;
    }
}


class Login{
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}

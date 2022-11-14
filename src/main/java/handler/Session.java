package handler;

import dao.AuthDao;
import dto.AuthDto;
import org.bson.Document;
import request.ParsedRequest;

import java.util.List;

public class Session {



    public static AuthSession checkAuth(ParsedRequest request){
        //System.out.println("here in AuthSession:\n");
        AuthDao authDao = AuthDao.getInstance();

        AuthSession authSession = new AuthSession();
        String hash = request.getHeaderValue("auth");

        if (hash == null){
            return authSession;
        }

        Document document = new Document("hash", hash);
        List<AuthDto> list = authDao.query(document);

//        //testing
//        System.out.println("hash sent from request:");
//        System.out.println(hash);
//        System.out.println("hash from authDto:");
//        System.out.println(list.get(0).getHash());

        if(list.size() == 0){
            authSession.setLoggedIn(false);
            return authSession;
        }
        if(((AuthDto)list.get(0)).getExpireTime() != null && ((AuthDto)list.get(0)).getExpireTime() < System.currentTimeMillis()){
            //System.out.println("in expiretime auth");
            authSession.setLoggedIn(false);
            return authSession;
        }

        authSession.setLoggedIn(true);
        authSession.setUserName(((AuthDto)list.get(0)).getUserName());
        return authSession;
    }



}

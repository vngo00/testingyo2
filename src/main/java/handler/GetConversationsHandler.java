package handler;

import dao.ConversationDao;
import dto.ConversationDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class GetConversationsHandler implements BaseHandler{
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder res = new HttpResponseBuilder();

        AuthSession authSession = Session.checkAuth(request);
        if(!authSession.isLoggedIn()){
            return res.setStatus(StatusCodes.UNAUTHORIZED);
        }

        ConversationDao conversationDao = ConversationDao.getInstance();
        List<ConversationDto> list = conversationDao.query(new Document("userName", authSession.getUserName()));

        return res.setStatus(StatusCodes.OK).setBody(new RestApiAppResponse(true, list, null));
    }
}

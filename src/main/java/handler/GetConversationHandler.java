package handler;

import dao.ConversationDao;
import dao.MessageDao;
import dto.ConversationDto;
import dto.MessageDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.List;

public class GetConversationHandler implements BaseHandler{
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder res = new HttpResponseBuilder();
        AuthSession authSession = Session.checkAuth(request);

        if(!authSession.isLoggedIn())
            return res.setStatus(StatusCodes.UNAUTHORIZED);

        MessageDao messageDao = MessageDao.getInstance();
        List<MessageDto> list = messageDao
                .query(new Document("conversationId", request.getQueryParam("conversationId")));

        return res.setStatus(StatusCodes.OK).setBody(new RestApiAppResponse(true, list, null));
    }
}

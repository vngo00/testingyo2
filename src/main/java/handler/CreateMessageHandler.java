package handler;

import dao.ConversationDao;
import dao.MessageDao;
import dao.UserDao;
import dto.BaseDto;
import dto.ConversationDto;
import dto.MessageDto;
import dto.UserDto;
import org.bson.Document;
import request.ParsedRequest;
import response.HttpResponseBuilder;
import response.RestApiAppResponse;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CreateMessageHandler implements BaseHandler{
    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request) {
        HttpResponseBuilder res = new HttpResponseBuilder();

        // check authentication
        AuthSession authSession = Session.checkAuth(request);
        if(!authSession.isLoggedIn()){
            return res.setStatus(StatusCodes.UNAUTHORIZED);
        }


        UserDao userDao = UserDao.getInstance();
        MessageDto messageDto = (MessageDto) GsonTool.gson.fromJson(request.getBody(),MessageDto.class);
        // check receiver
        List<UserDto> list = userDao.query(new Document("userName", messageDto.getToId()));
        if(list.size() == 0){
            return res.setStatus(StatusCodes.OK).setBody(new RestApiAppResponse<BaseDto>(false, null, "Sending message to unknown user"));
        }


        MessageDao messageDao = MessageDao.getInstance();

        // setting up conversationID
        String conversationId = makeConversationId(messageDto.getFromId(), messageDto.getToId());
        messageDto.setConversationId(conversationId);
        messageDto.setFromId(authSession.getUserName());
        messageDao.put(messageDto);

        ConversationDao conversationDao = ConversationDao.getInstance();

        if(conversationDao.query(new Document("conversationId", conversationId)).size() == 0){
            ConversationDto conversationDto1 = new ConversationDto();
            conversationDto1.setConversationId(conversationId);
            conversationDto1.setUserName(messageDto.getFromId());
            ConversationDto conversationDto2 = new ConversationDto();
            conversationDto2.setConversationId(conversationId);
            conversationDto2.setUserName(messageDto.getToId());
            conversationDao.put(conversationDto1);
            conversationDao.put(conversationDto2);
        }

        RestApiAppResponse<MessageDto> resBody = new RestApiAppResponse(true, List.of(messageDto), null);
        return res.setStatus(StatusCodes.OK).setBody(resBody);

    }

    public static String makeConversationId(String a, String b){
        List<String> list = Arrays.asList(a,b);
        list.sort(Comparator.naturalOrder());
        return list.get(0) + "_" + list.get(1);
    }
}

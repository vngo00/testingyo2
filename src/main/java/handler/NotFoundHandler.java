package handler;

import request.ParsedRequest;
import response.HttpResponseBuilder;

public class NotFoundHandler implements BaseHandler{

    @Override
    public HttpResponseBuilder handleRequest(ParsedRequest request){
        HttpResponseBuilder httpResponseBuilder = new HttpResponseBuilder();

        return httpResponseBuilder.setStatus("404 Not Found");
    }
}

package response;

import handler.GsonTool;

import java.util.Map;

public class CustomHttpResponse {
  public final Map<String,String> headers;
  public final String status;
  public final String version;
  public final String body;

  public CustomHttpResponse(Map<String, String> headers, String status, String version,
      String body) {
    this.headers = headers;
    this.status = status;
    this.version = version;
    this.body = body;
  }

  public String toString(){
    // todo
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(this.version + " " + this.status + "\n");
    for(Map.Entry<String,String> header : this.headers.entrySet()){
      stringBuilder.append(header.getKey() + ": " + header.getValue() + "\n" );
    }
    stringBuilder.append("\n");
    if(body != null) {
      //stringBuilder.append(GsonTool.gson.toJson(body, RestApiAppResponse.class));
      stringBuilder.append(body);
    }

    return stringBuilder.toString();
  }
}

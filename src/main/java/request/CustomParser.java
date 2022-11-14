package request;

public class CustomParser {

  // extract java useable values from a raw http request string
  // https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
  public static ParsedRequest parse(String request){
    String[] lines = request.split("(\r\n|\r|\n)");
    String requestLine = lines[0];

    String[] requestParts = requestLine.split(" ");

    var result = new ParsedRequest();
    result.setMethod(requestParts[0]);

    var parts = requestParts[1].split("\\?");
    result.setPath(parts[0]);

    // parse params
    if(parts.length == 2){
      String[] params = parts[1].split("&");
      for (var para : params){
        String[] pair = para.split("=");
        result.setQueryParam(pair[0],pair[1]);
      }
    }

    // todo get body and headers
    String body ="";
    boolean emptyLine = false;
    for(String line : lines){

      // check if a header and not passed the body since body has : as well
      if(line.contains(":") && !emptyLine){
        String[] pair = line.split(":");
        result.setHeaderValue(pair[0].trim(), pair[1].trim());
      }
      // switch to adding body parts
      if(line.compareTo("") == 0){
        emptyLine = true;
      }
      if (emptyLine){

        body += line;
      }
    }


    result.setBody(body);
    return result;
  }
}

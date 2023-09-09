package url.get.lambda.Service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import url.get.lambda.model.Url;
import url.get.lambda.repository.UrlRepository;
import url.get.lambda.utils.Utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class UrlService {
    UrlRepository urlRepository = new UrlRepository();
    private String jsonBody = null;

    public APIGatewayProxyResponseEvent retrieveAllUrls(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        Url url = Utils.convertStringToObj(apiGatewayRequest.getBody(), context);

        urlRepository.retrieveItem(url);
        jsonBody = Utils.convertObjToString(url, context);
        context.getLogger().log("data retrieved" + jsonBody);
        return createAPIResponse(jsonBody, 200, Utils.createHeaders());
    }
    public APIGatewayProxyResponseEvent saveUrl(APIGatewayProxyRequestEvent apiGatewayRequest, Context context) {
        Url url = Utils.convertStringToObj(apiGatewayRequest.getBody(), context);

        urlRepository.createItems(url);
        jsonBody = Utils.convertObjToString(url, context);
        context.getLogger().log("data retrieved" + jsonBody);
        return createAPIResponse(jsonBody, 201, Utils.createHeaders());
    }







//    public Url getSingleUrl() {
//        urlRepository.retrieveItem();
//    }
//
//    public void deleteUrl(Url url) {
//        urlRepository.deleteItem();
//    }
//
//    public void editUrl() {
//
//
//    }
//


    public Url shortenUrl(String url) {

        //posso receber uma url no formato www.google.com.br
        // posso receber também uma url no formato www.facebook.com/profile/gabriel
        // posso receber uma url com query do tipo https://www.google.com/search?sca_esv=563581542&sxsrf

        //String[] path = url.getLongUrl().toString().split("/");

        Url urlObject = new Url();

        String[] domain = urlObject.getLongUrl().toString().split(".");
        String[] uniqueID = UUID.randomUUID().toString().split("^[^-]+");

        String newUrl = String.join(domain[1], "-", uniqueID[0], ".url");
        urlObject.setShortUrl(newUrl);

        return urlObject;

    }



//
//    public void categorizeUrl() {
//    }


    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String, String> headers) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }


}

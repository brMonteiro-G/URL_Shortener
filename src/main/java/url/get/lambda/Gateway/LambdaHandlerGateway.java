package url.get.lambda.Gateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import url.get.lambda.Service.UrlService;

public class LambdaHandlerGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

    UrlService urlService = new UrlService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        System.out.println(apiGatewayRequest + "request");
        switch (apiGatewayRequest.getHttpMethod()){
            case "POST":
                System.out.println(apiGatewayRequest.getHeaders());
                //call service and put the business logic to make url shorter, after that call repository layer
               return urlService.saveUrl(apiGatewayRequest, context);
            case "GET":
                if(apiGatewayRequest.getPathParameters()!=null){
                    //retrieve url by id
                }
                return urlService.retrieveAllUrls(apiGatewayRequest, context);
                //retrieve all urls
            case "PUT":
                //edit a url

            case "DELETE":

                if(apiGatewayRequest.getPathParameters()!=null){
                    //delete url by id
                }
                //delete all urls
            default:
                //throw some error
        }



        return null;
    }


}

package com.project.urlShortener.Gateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.project.urlShortener.Service.UrlService;

public class LambdaHandlerGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

    UrlService urlService = new UrlService();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){


        switch (apiGatewayRequest.getHttpMethod()){

            case "POST":

                if(apiGatewayRequest.getPathParameters().containsValue("populate")){
                    context.getLogger().log("Calling populate tables");
                    return urlService.populateTables(apiGatewayRequest, context);
                }


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

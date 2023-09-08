package url.get.lambda.Gateway;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class LambdaHandlerGateway implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){

        switch (apiGatewayRequest.getHttpMethod()){
            case "POST":
                //call service and put the business logic to make url shorter, after that call repository layer
            case "GET":
                if(apiGatewayRequest.getPathParameters()!=null){
                    //retrieve url by id
                }
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

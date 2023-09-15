package com.project.urlShortener.Service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.project.urlShortener.Model.Request.CreateUrlRequest;
import com.project.urlShortener.Model.Url;
import com.project.urlShortener.Repository.UrlRepository;
import com.project.urlShortener.Utils.Utils;
import com.project.urlShortener.Enum.AllowedCategories;

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

        url.setShortUrl(url.getLongUrl());

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

    public APIGatewayProxyResponseEvent populateTables(APIGatewayProxyRequestEvent apiGatewayRequest, Context context){
        Url url = Utils.convertStringToObj(apiGatewayRequest.getBody(), context);

        url.setShortUrl(url.getLongUrl());

        context.getLogger().log("[service] calling populateUrlTable method");
        urlRepository.populateUrlTable();

        jsonBody = "Message: data saved";
        context.getLogger().log("populated database" + jsonBody);
        return createAPIResponse(jsonBody, 201, Utils.createHeaders());
    }



    public String shortenUrl(String url) {

        // posso receber uma url no formato www.google.com.br
        // posso receber também uma url no formato www.facebook.com/profile/gabriel
        // posso receber uma url com query do tipo https://www.google.com/search?sca_esv=563581542&sxsrf
        // fazer get do protocol
        // String[] path = url.getLongUrl().toString().split("/");


        String[] domain = url.split("\\.");
        String[] uniqueID = UUID.randomUUID().toString().split("-",5);

        String newUrl = String.join("-",domain[1], uniqueID[0]);
        String finalized = String.join(".", newUrl, "url");


        return finalized;

    }




    public void categorizeUrl(CreateUrlRequest createUrlRequest) {

        AllowedCategories tag = createUrlRequest.getTag();

        urlRepository.getAllCategories();


        //para categorizar uma URL eu preciso que seja passado uma tag falando qual o tipo de url que vai chegar
        // aqui eu vou passar uma lógica para verificar as tags existentes
        // então farei uma consulta no banco de dados
        // listarei e farei um stream.find
        // se caso tiver, adicionar naquela categoria
        // se caso não tiver criar uma nova
        // criar tabela de categorias

    }







    private APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String, String> headers) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        responseEvent.setBody(body);
        responseEvent.setHeaders(headers);
        responseEvent.setStatusCode(statusCode);
        return responseEvent;
    }


}

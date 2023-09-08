package url.get.lambda.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

public class UrlRepository {

    private DynamoDBMapper dynamoDBMapper;

    private void initDynamoDb(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        //ler sobre defaultClient and standart SDK dynamo
        dynamoDBMapper = new DynamoDBMapper(client);
    }


}

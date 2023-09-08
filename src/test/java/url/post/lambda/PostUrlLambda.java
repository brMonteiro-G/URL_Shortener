package url.post.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;

public class DynamoDBLambdaHandler implements RequestHandler<Object, String> {

    private static final String DYNAMODB_TABLE_NAME = "DynamoDbUrlTable";

    @Override
    public String handleRequest(Object input, Context context) {
        // Configurar o cliente DynamoDB
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        DynamoDB dynamoDB = new DynamoDB(client);

        // Obter uma referência para a tabela DynamoDB
        Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);

        // Ler um item da tabela
        GetItemRequest getItemRequest = new GetItemRequest()
                .withTableName(DYNAMODB_TABLE_NAME)
                .withKey("primaryKey", "keyValue"); // Substitua pela chave primária e valor desejados

        GetItemResult getItemResult = client.getItem(getItemRequest);
        Item item = Item.fromMap(getItemResult.getItem());

        // Escrever um item na tabela
        Item newItem = new Item()
                .withPrimaryKey("primaryKey", "keyValue") // Substitua pela chave primária e valor desejados
                .withString("attribute1", "value1") // Substitua pelos atributos e valores desejados
                .withString("attribute2", "value2");

        PutItemOutcome outcome = table.putItem(newItem);

        return "Operação concluída!";
    }
}

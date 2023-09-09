package url.get.lambda.repository;

import java.io.IOException;
import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import url.get.lambda.model.Url;

public class UrlRepository {

    private static DynamoDB dynamoDB;
    private DynamoDBMapper dynamoDBMapper;

    private static final String DYNAMODB_TABLE_NAME = "DynamoDbUrlTable";

    public void createItems(Url url) {
        initDynamoDB();
        try {

            dynamoDBMapper.save(url);

            System.out.println("Printing item after retrieving it....");

        } catch (Exception e) {
            System.err.println("Create items failed.");
            System.err.println(e.getMessage());


        }

    }

    public void retrieveItem(Url url) {
        initDynamoDB();
        //        Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);

        try {

            //      Item item = table.getItem("Id", 120, "Id, ISBN, Title, Authors", null);
            List<Url> urls = dynamoDBMapper.scan(Url.class, new DynamoDBScanExpression());


            System.out.println("Printing item after retrieving it....");
            //     System.out.println(item.toJSONPretty());

        } catch (Exception e) {
            System.err.println("GetItem failed.");
            System.err.println(e.getMessage());
        }

    }

    public void updateExistingAttributeConditionally() {

        Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);

        try {

            // Specify the desired price (25.00) and also the condition (price =
            // 20.00)

            UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("Id", 120)
                    .withReturnValues(ReturnValue.ALL_NEW).withUpdateExpression("set #p = :val1")
                    .withConditionExpression("#p = :val2").withNameMap(new NameMap().with("#p", "Price"))
                    .withValueMap(new ValueMap().withNumber(":val1", 25).withNumber(":val2", 20));

            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);

            // Check the response.
            System.out.println("Printing item after conditional update to new attribute...");
            System.out.println(outcome.getItem().toJSONPretty());

        } catch (Exception e) {
            System.err.println("Error updating item in " + DYNAMODB_TABLE_NAME);
            System.err.println(e.getMessage());
        }
    }

    public void deleteItem() {

        Table table = dynamoDB.getTable(DYNAMODB_TABLE_NAME);

        try {

            DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("Id", 120)
                    .withConditionExpression("#ip = :val").withNameMap(new NameMap().with("#ip", "InPublication"))
                    .withValueMap(new ValueMap().withBoolean(":val", false)).withReturnValues(ReturnValue.ALL_OLD);

            DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);

            // Check the response.
            System.out.println("Printing item that was deleted...");
            System.out.println(outcome.getItem().toJSONPretty());

        } catch (Exception e) {
            System.err.println("Error deleting item in " + DYNAMODB_TABLE_NAME);
            System.err.println(e.getMessage());
        }
    }


    private void initDynamoDB() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        dynamoDBMapper = new DynamoDBMapper(client);

    }


}


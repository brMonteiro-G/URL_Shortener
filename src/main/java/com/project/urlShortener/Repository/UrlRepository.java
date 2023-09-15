package com.project.urlShortener.Repository;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.project.urlShortener.Constants.Constants;
import com.project.urlShortener.Model.Url;
import com.project.urlShortener.Model.Categories;

public class UrlRepository {

    private static DynamoDB dynamoDB;
    private DynamoDBMapper dynamoDBMapper;

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

        Table table = dynamoDB.getTable(Constants.URL_TABLE_NAME);

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
            System.err.println("Error updating item in " + Constants.URL_TABLE_NAME);
            System.err.println(e.getMessage());
        }
    }

    public void deleteItem() {

        Table table = dynamoDB.getTable(Constants.URL_TABLE_NAME);

        try {

            DeleteItemSpec deleteItemSpec = new DeleteItemSpec().withPrimaryKey("Id", 120)
                    .withConditionExpression("#ip = :val").withNameMap(new NameMap().with("#ip", "InPublication"))
                    .withValueMap(new ValueMap().withBoolean(":val", false)).withReturnValues(ReturnValue.ALL_OLD);

            DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);

            // Check the response.
            System.out.println("Printing item that was deleted...");
            System.out.println(outcome.getItem().toJSONPretty());

        } catch (Exception e) {
            System.err.println("Error deleting item in " + Constants.URL_TABLE_NAME);
            System.err.println(e.getMessage());
        }
    }


    public List<Categories> getAllCategories(){


        Table table = dynamoDB.getTable(Constants.CATEGORIES_TABLE_NAME);

        List<Categories> categories = dynamoDBMapper.scan(Categories.class, new DynamoDBScanExpression());


        //filtrar id

        return categories;

    }


    public void populateUrlTable(){

        Url mockedUrl = new Url();
        mockedUrl.setLongUrl("https://portal.tutorialsdojo.com/courses/aws-certified-solutions-architect-associate-practice-exams/?_ga=2.206657427.1497759535.1693109425-1423103866.1693109425&_gl=1*1vp2f83*_ga*MTQyMzEwMzg2Ni4xNjkzMTA5NDI1*_ga_L96TFJ1R9K*MTY5MzEwOTQyNS4xLjAuMTY5MzEwOTQyNS4wLjAuMA..");
        mockedUrl.setShortUrl("tutorialsdojo-g25fs3.url");
        mockedUrl.setActive(true);
        mockedUrl.setClicks(5);
        mockedUrl.setUrlId(ThreadLocalRandom.current().nextInt());

        System.out.println("Saving object");
        dynamoDBMapper.save(mockedUrl);

    }

    private void initDynamoDB() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
        dynamoDBMapper = new DynamoDBMapper(client);

    }

}


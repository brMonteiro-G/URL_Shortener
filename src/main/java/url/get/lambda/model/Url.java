package url.get.lambda.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "DynamoDbUrlTable")
public class Url {

    @DynamoDBHashKey(attributeName = "urlId")
    private Integer urlId;

    @DynamoDBAttribute(attributeName = "longUrl")
    private String longUrl;

    @DynamoDBAttribute(attributeName = "shortUrl")
    private String shortUrl;

    @DynamoDBAttribute(attributeName = "clicks")
    private String clicks;

    @DynamoDBAttribute(attributeName = "ttl")
    private String ttl;

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getClicks() {
        return clicks;
    }

    public void setClicks(String clicks) {
        this.clicks = clicks;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }
}

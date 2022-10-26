import redis.clients.jedis.*;

public class RedisDemo {
    public static void main(String args[])
    {
        boolean ssl = true;
        String hostName = "{Provide Host Name}";
        String cacheKey = "{Provide Cache Key}";
        //Redis Cache connects on port 6380
        int port = 6380;

        // Build connection to Redis Cache
        JedisShardInfo shardInfo = new JedisShardInfo(hostName, port, ssl);
        shardInfo.setPassword(cacheKey);
        Jedis jedisCache = new Jedis(shardInfo);

        // Check if you are able to connect/ping to Redis Cache
        System.out.println( "Ping Result : " + jedisCache.ping());

        // Set Data 1 Key : Data 1, Value : This is value for Data1
        jedisCache.set("Data1", "This is value for Data1");

        // Set Data 2 Key : Data 2, Value : This is value for Data2
        jedisCache.set("Data2", "This is value for Data2");

        // Read Data 1 from Redis Cache
        System.out.println( "Value for Data 1 : " + jedisCache.get("Data1"));

        // Read Data 2 from Redis Cache
        System.out.println( "Value for Data 2 : " + jedisCache.get("Data2"));

        // Set Expiry for Data 1
        jedisCache.expire("Data1", 60);

        // Delete Data 2
        jedisCache.del("Data2");

        jedisCache.close();

    }
}

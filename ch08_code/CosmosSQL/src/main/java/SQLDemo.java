
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;

public class SQLDemo {

    public static void main(String args[])
    {
        String endpoint = "{Provide endpoint URL}";
        String key = "{Provide Access Key}";

        // Create connection to Database
        CosmosClient client = new CosmosClientBuilder()
                .endpoint(endpoint)
                .key(key)
                .buildClient();

        // Create Database
        String databaseName = "students";
        CosmosDatabaseResponse databaseDetails = client.createDatabaseIfNotExists(databaseName);
        CosmosDatabase database = client.getDatabase(databaseDetails.getProperties().getId());
        System.out.println("Database created - " + database.getId() );

        // Create Container
        String containerName = "class";
        CosmosContainerProperties containerProperties = new CosmosContainerProperties(containerName, "/partitionKey");
        CosmosContainerResponse containerResponse = database.createContainerIfNotExists(containerProperties);
        CosmosContainer container = database.getContainer(containerResponse.getProperties().getId());
        System.out.println("Container created " + container.getId());

        //Insert data to the Container
        //Insert Student 1
        Student student = new Student();
        student.setId("1");
        student.setMarks(80);
        student.setPartitionKey("eight-standard");
        student.setName("Abhishek");
        CosmosItemResponse item = container.createItem(student, new PartitionKey(student.getPartitionKey()), new CosmosItemRequestOptions());

        //Insert Student 2
        student = new Student();
        student.setId("2");
        student.setMarks(60);
        student.setPartitionKey("ninth-standard");
        student.setName("Abhijeet");
        item = container.createItem(student, new PartitionKey(student.getPartitionKey()), new CosmosItemRequestOptions());

        //Insert Student 3
        student = new Student();
        student.setId("3");
        student.setMarks(70);
        student.setPartitionKey("eight-standard");
        student.setName("Sunny");
        item = container.createItem(student, new PartitionKey(student.getPartitionKey()), new CosmosItemRequestOptions());

        //Insert Student 4
        student = new Student();
        student.setId("4");
        student.setMarks(60);
        student.setPartitionKey("seventh-standard");
        student.setName("Abhilash");
        item = container.createItem(student, new PartitionKey(student.getPartitionKey()), new CosmosItemRequestOptions());

        //Query data from the container
        int pageSize = 10;
        CosmosQueryRequestOptions queryOptions = new CosmosQueryRequestOptions();
        //  Set populate query metrics to get metrics around query executions
        queryOptions.setQueryMetricsEnabled(true);

        CosmosPagedIterable<Student> studentPagedIterable = container.queryItems(
                "SELECT * FROM class WHERE class.partitionKey IN ('eight-standard', 'seventh-standard')", queryOptions, Student.class);
        studentPagedIterable.iterableByPage(pageSize).forEach(resultItem -> {

            resultItem.getResults().forEach(data -> System.out.println(data.getId() + "-" +data.getName()+"-"+data.getMarks()));

        });

    }
}

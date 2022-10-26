import com.azure.data.tables.*;
import com.azure.data.tables.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TableDemo {
    public static void main(String args[])
    {
        String tableName="students";
        String connectionString="DefaultEndpointsProtocol=https;AccountName=mysademo28;AccountKey=TsjMB2ViZqD1r+XUUNW8NUmiz/2+AjBoGsQuuLUyfJNEOGh5nkauBp8Vropueymnyxg9Es4Q+ihitfueC4zgIg==;EndpointSuffix=core.windows.net";

        // Create table
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();
        TableClient tableClient = tableServiceClient.createTableIfNotExists(tableName);

        // Add record to the table
        String partitionKey="Electronics Department";
        String rowKey="Sam";
        TableEntity entity = new TableEntity(partitionKey, rowKey)
                .addProperty("Age", 22)
                .addProperty("Grade", "A")
                .addProperty("Marks", 88);

        tableClient.createEntity(entity);

        // Add another record to the table
        tableClient = tableServiceClient.getTableClient(tableName);
        rowKey = "Rob";
        entity = new TableEntity(partitionKey, rowKey)
                .addProperty("Age", 24)
                .addProperty("Grade", "B")
                .addProperty("Marks", 60);

        tableClient.createEntity(entity);

        //Retrieve the records in the table
        tableClient = tableServiceClient.getTableClient(tableName);
        String filterQuery = "PartitionKey eq 'Electronics Department'";
        ListEntitiesOptions options = new ListEntitiesOptions().setFilter(filterQuery);

        // Loop through the result set and display.
        tableClient.listEntities(options, null, null).forEach(tableEntity -> {
            System.out.println(tableEntity.getPartitionKey() +
                    " " + tableEntity.getRowKey() +
                    "\t" + tableEntity.getProperty("Age") +
                    "\t" + tableEntity.getProperty("Grade") +
                    "\t" + tableEntity.getProperty("Marks"));
        });

    }
}

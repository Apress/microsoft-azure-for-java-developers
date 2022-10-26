import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class BlobDemo {
    public static void main(String args[])
    {
        String connectionString = "DefaultEndpointsProtocol=https;AccountName=mysademo28;AccountKey=TsjMB2ViZqD1r+XUUNW8NUmiz/2+AjBoGsQuuLUyfJNEOGh5nkauBp8Vropueymnyxg9Es4Q+ihitfueC4zgIg==;EndpointSuffix=core.windows.net";
        String containerName = "sample";

        // Build the BlobServiceClient from connection string
        BlobServiceClient blobClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();

        // Check if Container exists
        // List all containers and then check if the container already exists
        var existingContainers = blobClient.listBlobContainers();
        Boolean containerExists = false;
        for(BlobContainerItem containerItem : existingContainers)
        {
            if(containerItem.getName().equalsIgnoreCase(containerName)) {
                containerExists = true;
            }
        }

        //If the container does not exist then create the container
        // If the container exists then get reference for the existing container
        BlobContainerClient container;
        if(!containerExists) {
            // Create container
             container = blobClient.createBlobContainer(containerName);
        }
        else {
            // Get reference to the existing container
             container = blobClient.getBlobContainerClient(containerName);
        }

        //Create a blob inside the container
        String blobName = "sample.txt";
        BlobClient blob = container.getBlobClient(blobName);
        String data = "Hello Blob !!!!";
        InputStream dataStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
        blob.upload(dataStream,data.length(),true);

        // Read all blobs in the container
        var blobs = container.listBlobs();
        // Iterate through each of the blobs in the container
        for(BlobItem blobItem : blobs){
            // Get blob name
            System.out.println("Blob Name : "+blobItem.getName());
            // Read blob data
            BlobClient blobRead = container.getBlobClient(blobItem.getName());
            int blobSize = (int) blobRead.getProperties().getBlobSize();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(blobSize);
            blobRead.download(outputStream);
            System.out.println("Blob Data : "+outputStream.toString());
        }
    }
}

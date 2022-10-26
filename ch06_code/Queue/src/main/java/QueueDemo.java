import com.azure.storage.queue.*;
import com.azure.storage.queue.models.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class QueueDemo {
    public static void main(String args[])
    {
        String queueName = "sample-queue";
        String connectionString = "DefaultEndpointsProtocol=https;AccountName=mysademo28;AccountKey=TsjMB2ViZqD1r+XUUNW8NUmiz/2+AjBoGsQuuLUyfJNEOGh5nkauBp8Vropueymnyxg9Es4Q+ihitfueC4zgIg==;EndpointSuffix=core.windows.net";

        // Get reference to QueueClient
        QueueClient queueClient = new QueueClientBuilder().connectionString(connectionString).queueName(queueName).buildClient();

        //Create Queue
        queueClient.create();

        //Add message to the Queue
        queueClient.sendMessage("My Message Added");
        queueClient.sendMessage("My Message Added 01");
        queueClient.sendMessage("My Message Added 02");
        queueClient.sendMessage("My Message Added 04");

        //Peek message from the Queue
        var message = queueClient.peekMessage();
        System.out.println("Peek first message in queue : "+message.getMessageText());

        //Get all messages from the queue and remove them from the queue
        var messages = queueClient.receiveMessages(queueClient.getProperties().getApproximateMessagesCount());

        for(QueueMessageItem msg:messages){
            System.out.println("Added Message : "+msg.getMessageText());
        }
    }
}

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.GraphServiceClient;

import java.util.LinkedList;
import java.util.List;

public class SendMail {
    public static void main(String args[])
    {
        String clientId="{Provide Client ID for Service Principal}";
        String clientSecret = "Provide Secret value for Service Principal";
        String tenantId = "Provide Tenant ID";

        //You can send using the default scope.
        List<String> SCOPES = List.of("https://graph.microsoft.com/.default");

        //Build Client Credential based on the Service Principal
        final ClientSecretCredential clientCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();

        //Build a Token Credential Auth provider that can authenticate using the Service Principal
        final TokenCredentialAuthProvider tokenProvider =
                new TokenCredentialAuthProvider(SCOPES, clientCredential);

        //Build the Graph Client
        final GraphServiceClient graph = GraphServiceClient
                .builder()
                .authenticationProvider(tokenProvider)
                .buildClient();

        //Construct the message
        Message message = new Message();
        message.subject = "Order Status - IX1234";
        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = "Dear Customer <br/><br/> You order is ready. It will be dispatched soon <br/><br/> Thanks<br/>OPs Team";
        message.body = body;
        LinkedList<Recipient> to = new LinkedList<Recipient>();
        Recipient toReceiver = new Recipient();
        EmailAddress email = new EmailAddress();
        email.address = "abhisek.misra@thecloudcompute.com";
        toReceiver.emailAddress = email;
        to.add(toReceiver);
        message.toRecipients = to;

        //Send mail. Provide the mail address of the user we created in the Azure AD
        //and have assigned Microsoft365 Developer E5 license
        graph.users("mailer@abhisekmisra.onmicrosoft.com")
                .sendMail(UserSendMailParameterSet
                        .newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(false)
                        .build())
                .buildRequest()
                .post();

    }
}

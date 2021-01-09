package pl.edu.agh.to.drugstore.command.clientorder;

import pl.edu.agh.to.drugstore.command.EditCommand;
import pl.edu.agh.to.drugstore.model.business.ClientOrder;
import pl.edu.agh.to.drugstore.model.dao.ClientOrderDAO;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EditClientOrderCommand extends EditCommand<ClientOrder> {

    private void sendEmail() {
        final String username = "drugstore123okon@gmail.com\n";
        final String password = "Admin.123"; //xD

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("wtarsa@gmail.com")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Szanowny Panie,"
                    + "\n\n Wysyłanie maili działa!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public EditClientOrderCommand(ClientOrder objectToEdit, ClientOrder editedObject, ClientOrderDAO objectDAO) {
        super(objectToEdit, editedObject, objectDAO);

        if (objectToEdit.getOrderStatus() != editedObject.getOrderStatus()) {
            sendEmail();
        }
    }

    @Override
    public String getName() {
        return "Edited client order: " + this.getObjectToEdit().toString();
    }
}

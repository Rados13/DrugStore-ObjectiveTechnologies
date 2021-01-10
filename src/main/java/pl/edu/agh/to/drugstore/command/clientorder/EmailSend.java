package pl.edu.agh.to.drugstore.command.clientorder;

import pl.edu.agh.to.drugstore.model.business.ClientOrder;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSend {

    public static void sendEmail(ClientOrder editedObject) {
        final String username = "drugstore123okon@gmail.com\n";
        final String password = "Admin.123";

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
                    InternetAddress.parse(editedObject.getPerson().getEmail())
            );
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("Dear, ").append(editedObject.getPerson().getFirstname()).append(" ").append(editedObject.getPerson().getLastname()).append("\n\n");

            switch (editedObject.getOrderStatus().getValue()) {
                case CREATED:
                    message.setSubject("DrugStore: Your order " + editedObject.getId() + " has been created");
                    messageBuilder.append("your order no. ")
                            .append(editedObject.getId())
                            .append(" has been created. \nPlease pay ")
                            .append(editedObject.getSumPriceProperty().getValue())
                            .append(" within 3 days, or your order will be cancelled");
                    break;

                case PAID:
                case SHIPPED:
                case DELIVERED:
                    message.setSubject("DrugStore: Your order " + editedObject.getId());
                    messageBuilder.append("status of your order no. ")
                            .append(editedObject.getId())
                            .append(" has changed to ")
                            .append(editedObject.getOrderStatus().getValue());
                    break;
                case CANCELED:
                    message.setSubject("DrugStore: Your order " + editedObject.getId() + " has been cancelled");
                    messageBuilder.append("status of your order no. ")
                            .append(editedObject.getId())
                            .append(" has changed to cancelled");
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + editedObject.getOrderStatus().getValue());
            }
            messageBuilder.append("\n\nGreetings, DrugStore");
            message.setText(messageBuilder.toString());
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

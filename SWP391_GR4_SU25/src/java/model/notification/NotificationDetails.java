package model.notification;

public class NotificationDetails {

    private String notificationId;
    private String receiver;

    public NotificationDetails(String notificationId, String receiver) {
        this.notificationId = notificationId;
        this.receiver = receiver;
    }

    public NotificationDetails() {
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}

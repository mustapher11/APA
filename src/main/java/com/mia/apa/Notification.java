package com.mia.apa;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Notification {

    public static void createSuccessNotification(String title, String message){
        Notifications notifications = Notifications.create().
                title(title).text(message).position(Pos.TOP_RIGHT).darkStyle().hideAfter(Duration.seconds(2.0));
        notifications.showInformation();
    }

    public static void showHelpNotification(){
        Notifications notifications = Notifications.create().
                title("Contact us")
                .text("In case of any issues:\n\n call us at 0792672013\n or email us at mustapher1997@gmail.com")
                .position(Pos.TOP_RIGHT).darkStyle().
                hideAfter(Duration.INDEFINITE);
        notifications.showInformation();
    }
}

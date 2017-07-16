package com.example.admin.import2;

/**
 * Created by Admin on 7/4/2017.
 */

public class Reminder {


    public String reminderMessage;
    public String senderUID;
    public String senderName;
    public  String receiverUID;
    public  String receiverName;
    public  String reminderTime;
    public  String timestamp;
    public String status;

    public Reminder() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Reminder(String reminderMessage,String senderUID,String senderName,String receiverUID,String receiverName,String reminderTime,String timestamp,String status) {
        this.reminderMessage = reminderMessage;
        this.senderUID =senderUID ;
        this.senderName=senderName;


        this.receiverUID=receiverUID;
        this.receiverName=receiverName;

        this.reminderTime=reminderTime;
        this.timestamp=timestamp;
        this.status=status;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    public String getSenderUID() {
        return senderUID;
    }

    public void setSenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverUID() {
        return receiverUID;
    }

    public void setReceiverUID(String receiverUID) {
        this.receiverUID = receiverUID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

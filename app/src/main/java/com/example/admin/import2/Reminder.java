package com.example.admin.import2;

/**
 * Created by Admin on 7/4/2017.
 */

public class Reminder {


    public String reminderMessage;
    public String senderUID;
    public String senderName;
    public  String senderPicture;


    public  String receiverUID;
    public  String receiverName;
    public  String reminderTime;
    public  String receiverPicture;

    public  String timestamp;
    public String status;
    public  String receiverUID_status;


    public Reminder() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getReceiverPicture() {
        return receiverPicture;
    }

    public void setReceiverPicture(String receiverPicture) {
        this.receiverPicture = receiverPicture;
    }

    public Reminder(String reminderMessage, String senderUID, String senderName, String senderPicture, String receiverUID, String receiverName, String receiverPicture, String reminderTime, String timestamp, String status, String receiverUID_status) {
        this.reminderMessage = reminderMessage;
        this.senderUID =senderUID ;
        this.senderName=senderName;
        this.senderPicture = senderPicture;

        this.receiverUID=receiverUID;
        this.receiverName=receiverName;
        this.receiverPicture=receiverPicture;

        this.reminderTime=reminderTime;
        this.timestamp=timestamp;
        this.status=status;
        this.receiverUID_status=receiverUID_status;
    }

    public String getReminderMessage() {

        return reminderMessage;
    }

    public String getReceiverUID_status() {
        return receiverUID_status;
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

    public void setReceiverUID_status(String receiverUID_status) {
        this.receiverUID_status = receiverUID_status;
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

    public String getSenderPicture() {
        return senderPicture;
    }

    public void setSenderPicture(String senderPicture) {
        this.senderPicture = senderPicture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

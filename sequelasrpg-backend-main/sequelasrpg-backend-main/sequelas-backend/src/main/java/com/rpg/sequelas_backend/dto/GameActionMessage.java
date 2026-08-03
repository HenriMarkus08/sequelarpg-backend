package com.rpg.sequelas_backend.dto;

public class GameActionMessage {

    private String roomCode;
    private String sender;
    private String type;
    private String content;

    public GameActionMessage() {
    }

    public GameActionMessage(String roomCode, String sender, String type, String content) {
        this.roomCode = roomCode;
        this.sender = sender;
        this.type = type;
        this.content = content;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}


package com.chub.communication_hub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CommunicationMessageRequest {

    @NotBlank(message = "Receiver cannot be empty")
    private String receiver;

    @NotBlank(message = "Channel cannot be empty")
    private String channel;

    @NotBlank(message = "Content cannot be empty")
    private String content;

    @Pattern(
            regexp = "initial|inProgress|completed|failed",
            message = "Invalid state"
    )
    private String state = "initial";

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}


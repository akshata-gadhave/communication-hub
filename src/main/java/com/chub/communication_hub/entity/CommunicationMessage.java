package com.chub.communication_hub.entity;

        import jakarta.persistence.*;
        import java.time.LocalDateTime;
        import jakarta.validation.constraints.NotBlank;
        import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "communication_message")
public class CommunicationMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Receiver cannot be empty")
    @Column(nullable = false)
    private String receiver;

    @NotBlank(message = "Channel cannot be empty")
    @Column(nullable = false)
    private String channel;

    @NotBlank(message = "Content cannot be empty")
    @Column(length = 2000)
    private String content;

    @Pattern(
            regexp = "initial|inProgress|completed|failed",
            message = "State must be initial, inProgress, completed, or failed"
    )
    @Column(nullable = false)
    private String state = "initial";

    private LocalDateTime createdAt = LocalDateTime.now();

    public CommunicationMessage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

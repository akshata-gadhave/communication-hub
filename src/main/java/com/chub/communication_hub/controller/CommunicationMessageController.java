package com.chub.communication_hub.controller;

import com.chub.communication_hub.entity.CommunicationMessage;
import com.chub.communication_hub.service.CommunicationMessageService;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.chub.communication_hub.dto.CommunicationMessageRequest;
import jakarta.validation.Valid;
import com.chub.communication_hub.dto.CommunicationMessageRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/communicationMessage")
public class CommunicationMessageController {

    private final CommunicationMessageService service;

    public CommunicationMessageController(
            CommunicationMessageService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommunicationMessage createMessage(
            @Valid @RequestBody CommunicationMessageRequest request) {

        CommunicationMessage message = new CommunicationMessage();

        message.setReceiver(request.getReceiver());
        message.setChannel(request.getChannel());
        message.setContent(request.getContent());
        message.setState(request.getState());

        return service.createMessage(message);
    }

    @GetMapping
    public List<CommunicationMessage> getAllMessages() {
        return service.getAllMessages();
    }

    @GetMapping("/page")
    public Page<CommunicationMessage> getMessagesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.fromString(sortDir), "id")
        );

        return service.getAllMessages(pageable);
    }
    @GetMapping("/{id}")
    public CommunicationMessage getMessageById(@PathVariable Long id) {

        CommunicationMessage message = service.getMessageById(id);

        if (message == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Message not found with id: " + id
            );
        }

        return message;
    }
    @GetMapping("/channel/{channel}")
    public List<CommunicationMessage> getMessagesByChannel(
            @PathVariable String channel) {

        return service.getMessagesByChannel(channel);
    }
    @GetMapping("/state/{state}")
    public List<CommunicationMessage> getMessagesByState(
            @PathVariable String state) {
        return service.getMessagesByState(state);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@PathVariable Long id) {
        service.deleteMessage(id);
    }


    @PatchMapping("/{id}")
    public CommunicationMessage updateMessage(
            @PathVariable Long id,
            @Valid @RequestBody CommunicationMessageRequest request) {

        CommunicationMessage message = new CommunicationMessage();
        message.setReceiver(request.getReceiver());
        message.setChannel(request.getChannel());
        message.setContent(request.getContent());
        message.setState(request.getState());

        CommunicationMessage updatedMessage =
                service.updateMessage(id, message);

        if (updatedMessage == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Message not found with id: " + id
            );
        }

        return updatedMessage;
    }

}




package com.chub.communication_hub.service;
import com.chub.communication_hub.entity.CommunicationMessage;
import com.chub.communication_hub.repository.CommunicationMessageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class CommunicationMessageServiceImpl
    implements CommunicationMessageService {

        private final CommunicationMessageRepository repository;

    public CommunicationMessageServiceImpl(
                CommunicationMessageRepository repository) {
            this.repository = repository;
        }

        @Override
        public CommunicationMessage createMessage(
                CommunicationMessage message) {

            return repository.save(message);
        }

        @Override
        public List<CommunicationMessage> getAllMessages() {

            return repository.findAll();
        }
    @Override
    public List<CommunicationMessage> getMessagesByChannel(String channel) {
        return repository.findByChannelIgnoreCase(channel);
    }
    @Override
    public List<CommunicationMessage> getMessagesByState(String state) {
        return repository.findByStateIgnoreCase(state);
    }

        @Override
        public CommunicationMessage getMessageById(Long id) {

            return repository.findById(id).orElse(null);
        }
    @Override
    public CommunicationMessage updateMessage(
            Long id,
            CommunicationMessage message) {

        CommunicationMessage existingMessage =
                repository.findById(id).orElse(null);

        if (existingMessage == null) {
            return null;
        }

        existingMessage.setReceiver(message.getReceiver());
        existingMessage.setChannel(message.getChannel());
        existingMessage.setContent(message.getContent());
        existingMessage.setState(message.getState());

        return repository.save(existingMessage);
    }

        @Override

    public void deleteMessage(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Message not found with id: " + id
            );
        }

        repository.deleteById(id);
    }

    @Override
    public Page<CommunicationMessage> getAllMessages(Pageable pageable) {
        return repository.findAll(pageable);
    }


}

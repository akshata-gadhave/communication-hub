package com.chub.communication_hub.service;
import com.chub.communication_hub.entity.CommunicationMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
public interface CommunicationMessageService {
    CommunicationMessage createMessage(CommunicationMessage message);

    List<CommunicationMessage> getAllMessages();

    CommunicationMessage getMessageById(Long id);
    List<CommunicationMessage> getMessagesByChannel(String channel);
    List<CommunicationMessage> getMessagesByState(String state);
    Page<CommunicationMessage> getAllMessages(Pageable pageable);

    void deleteMessage(Long id);
    CommunicationMessage updateMessage(
            Long id,
            CommunicationMessage message);
}

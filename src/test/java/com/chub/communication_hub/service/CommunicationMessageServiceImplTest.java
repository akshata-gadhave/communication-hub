
package com.chub.communication_hub.service;

import com.chub.communication_hub.entity.CommunicationMessage;
import com.chub.communication_hub.repository.CommunicationMessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommunicationMessageServiceImplTest {

    @Mock
    private CommunicationMessageRepository repository;

    @InjectMocks
    private CommunicationMessageServiceImpl service;

    @Test
    void createMessage_shouldSaveMessage() {
        CommunicationMessage message = new CommunicationMessage();
        message.setReceiver("9123456780");
        message.setChannel("SMS");
        message.setContent("Unit test message");
        message.setState("initial");

        when(repository.save(message)).thenReturn(message);

        CommunicationMessage result = service.createMessage(message);

        assertEquals("9123456780", result.getReceiver());
        verify(repository).save(message);
    }
    @Test
    void getMessageById_shouldReturnMessage() {
        CommunicationMessage message = new CommunicationMessage();
        message.setReceiver("9123456780");
        message.setChannel("SMS");
        message.setContent("Test message");
        message.setState("initial");

        when(repository.findById(1L))
                .thenReturn(java.util.Optional.of(message));

        CommunicationMessage result = service.getMessageById(1L);

        assertEquals("9123456780", result.getReceiver());
        verify(repository).findById(1L);
    }
    @Test
    void deleteMessage_shouldDeleteMessage() {
        Long id = 1L;

        when(repository.existsById(id)).thenReturn(true);

        service.deleteMessage(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void updateMessage_shouldUpdateMessage() {
        Long id = 1L;

        CommunicationMessage existingMessage = new CommunicationMessage();
        existingMessage.setReceiver("9123456780");
        existingMessage.setChannel("SMS");
        existingMessage.setContent("Old message");
        existingMessage.setState("initial");

        CommunicationMessage updatedMessage = new CommunicationMessage();
        updatedMessage.setReceiver("9876543210");
        updatedMessage.setChannel("EMAIL");
        updatedMessage.setContent("Updated message");
        updatedMessage.setState("inProgress");

        when(repository.findById(id))
                .thenReturn(java.util.Optional.of(existingMessage));

        when(repository.save(existingMessage))
                .thenReturn(existingMessage);

        CommunicationMessage result =
                service.updateMessage(id, updatedMessage);

        assertEquals("9876543210", result.getReceiver());
        assertEquals("EMAIL", result.getChannel());
        assertEquals("Updated message", result.getContent());
        assertEquals("inProgress", result.getState());

        verify(repository).findById(id);
        verify(repository).save(existingMessage);
    }

    @Test
    void getMessagesByChannel_shouldReturnMessages() {
        CommunicationMessage message1 = new CommunicationMessage();
        message1.setReceiver("9123456780");
        message1.setChannel("SMS");
        message1.setContent("First SMS");
        message1.setState("initial");

        CommunicationMessage message2 = new CommunicationMessage();
        message2.setReceiver("9876543210");
        message2.setChannel("SMS");
        message2.setContent("Second SMS");
        message2.setState("completed");

        List<CommunicationMessage> messages =
                List.of(message1, message2);

        when(repository.findByChannelIgnoreCase("SMS"))
                .thenReturn(messages);

        List<CommunicationMessage> result =
                service.getMessagesByChannel("SMS");

        assertEquals(2, result.size());
        assertEquals("SMS", result.get(0).getChannel());
        assertEquals("SMS", result.get(1).getChannel());

        verify(repository).findByChannelIgnoreCase("SMS");
    }

    @Test
    void getMessagesByState_shouldReturnMessages() {
        CommunicationMessage message = new CommunicationMessage();
        message.setReceiver("9876543210");
        message.setChannel("EMAIL");
        message.setContent("In progress message");
        message.setState("inProgress");

        List<CommunicationMessage> messages = List.of(message);

        when(repository.findByStateIgnoreCase("inProgress"))
                .thenReturn(messages);

        List<CommunicationMessage> result =
                service.getMessagesByState("inProgress");

        assertEquals(1, result.size());
        assertEquals("inProgress", result.get(0).getState());

        verify(repository).findByStateIgnoreCase("inProgress");
    }



}


package com.chub.communication_hub.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.chub.communication_hub.entity.CommunicationMessage;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.chub.communication_hub.entity.CommunicationMessage;
public interface CommunicationMessageRepository

            extends JpaRepository<CommunicationMessage, Long> {
    List<CommunicationMessage> findByChannelIgnoreCase(String channel);
    List<CommunicationMessage> findByStateIgnoreCase(String state);
    List<CommunicationMessage> getMessagesByState(String state);
    Page<CommunicationMessage> findAll(Pageable pageable);
    }


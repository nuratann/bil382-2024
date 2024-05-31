package kg.buyers.websocketservice.repository;

import jakarta.transaction.Transactional;
import kg.buyers.websocketservice.model.ChatMessage;
import kg.buyers.websocketservice.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository
        extends JpaRepository<ChatMessage, String> {
    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);
    List<ChatMessage> findByChatId(Long chatId);
    @Transactional
    @Modifying
    @Query("UPDATE ChatMessage cm SET cm.status = :status WHERE cm.senderId = :senderId AND cm.recipientId = :recipientId")
    void updateStatuses(String senderId, String recipientId, MessageStatus status);

}
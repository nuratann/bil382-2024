package kg.buyers.messageservice.repositories

import kg.buyers.messageservice.entities.Chat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository : JpaRepository<Chat,String> {
}
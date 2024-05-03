package kg.buyers.messageservice.repositories

import kg.buyers.messageservice.entities.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<Message, String> {
}
package kg.buyers.messageservice.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.sql.Timestamp
import java.util.Base64

@Entity
class Message {
    @Id
    private lateinit var id:String
    @ManyToOne
    private lateinit var chat: Chat
    private lateinit var text:String
    private lateinit var image:ByteArray
    private lateinit var timestamp:Timestamp
}
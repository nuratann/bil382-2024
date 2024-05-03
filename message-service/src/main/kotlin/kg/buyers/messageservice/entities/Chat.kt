package kg.buyers.messageservice.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Chat {
    @Id
    private lateinit var id: String
    @OneToMany
    private lateinit var messages: List<Message>;
    @ManyToOne
    private lateinit var user1:User
    @ManyToOne
    private lateinit var user2:User
}
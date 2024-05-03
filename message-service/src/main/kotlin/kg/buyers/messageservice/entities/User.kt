package kg.buyers.messageservice.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class User {
    @Id
    private lateinit var id: String
    @OneToMany
    private lateinit var chats:List<Chat>;

}
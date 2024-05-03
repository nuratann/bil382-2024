package kg.buyers.messageservice.endpoints

import jakarta.websocket.OnOpen
import jakarta.websocket.Session
import jakarta.websocket.server.PathParam
import jakarta.websocket.server.ServerEndpoint
import kg.buyers.messageservice.entities.Message
import java.util.concurrent.CopyOnWriteArraySet


@ServerEndpoint("/chat")
class ChatEndpoint {

    private var session: Session? = null
    private val chatEndpoints: Set<ChatEndpoint> = CopyOnWriteArraySet<ChatEndpoint>()
    private val users = HashMap<String, String>()
    @OnOpen
    fun onOpen(){
    }
}
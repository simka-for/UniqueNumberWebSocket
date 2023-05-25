import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.io.IOException;
import java.math.BigInteger;

@WebSocket
public class WebSocketServer {

    private final SessionRepository sessionRepository = new SessionRepository();
    private final NumberGeneratorService numberGeneratorService = new NumberGeneratorService();

    @OnWebSocketConnect
    public void onConnect(Session session) {
        sessionRepository.addSession(session);
    }

    @OnWebSocketClose
    public void onClose(Session session, int statusCode, String reason) {
        sessionRepository.removeSession(session, statusCode, reason);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        if ("getNumber".equals(message)) {
            BigInteger uniqueNumber = numberGeneratorService.generateUniqueNumber();
            try {
                session.getRemote().sendString("{\"number\": \"" + uniqueNumber + "\"}");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

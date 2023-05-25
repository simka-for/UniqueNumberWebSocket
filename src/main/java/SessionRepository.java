import org.eclipse.jetty.websocket.api.Session;
import java.util.HashSet;
import java.util.Set;

/**
 * Репозиторий для работы с сессиями
 */
public class SessionRepository {

    private static final Set<Session> sessions = new HashSet<>();

    public void addSession(Session session) {
        String clientIp = session.getRemoteAddress().getAddress().getHostAddress();
        if (isClientAllowed(clientIp)) {
            sessions.add(session);
            System.out.println("Client connected: " + clientIp);
        } else {
            System.out.println("Connection rejected for client: " + clientIp);
            session.close();
        }
    }

    public void removeSession(Session session, int statusCode, String reason) {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getRemoteAddress().getAddress().getHostAddress()
                                   + " " + statusCode + " " + reason);
    }

    private boolean isClientAllowed(String clientIp) {
        for (Session session : sessions) {
            String existingIp = session.getRemoteAddress().getAddress().getHostAddress();
            if (clientIp.equals(existingIp)) {
                return false;
            }
        }
        return true;
    }
}

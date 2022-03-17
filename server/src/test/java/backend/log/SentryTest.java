package backend.log;

import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.protocol.Message;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("dev")
public class SentryTest {

    public void sentMessageException() {
        try {
            throw new Exception("Exception 테스트 로그입니다.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }

    public void sentMessage() {
        Sentry.captureMessage("Message 테스트 로그입니다.");
    }

    public void sentEvent() {
        Message message = new Message();
        message.setMessage("event Message 테스트 로그입니다.");

        SentryEvent event = new SentryEvent();
        event.setTag("test_id", UUID.randomUUID().toString());
        event.setLevel(SentryLevel.ERROR);
        event.setMessage(message);

        Sentry.captureEvent(event);
    }

    @Test
    public void Exception로그 () { sentMessageException();}

    @Test
    public void 메세지로그() { sentMessage(); }

    @Test
    public void 이벤트로그() { sentEvent(); }
}
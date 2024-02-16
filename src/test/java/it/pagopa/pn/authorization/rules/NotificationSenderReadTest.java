package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.mocks.Notification;
import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.NotificationResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationSenderReadTest {

    static NotificationResource internalNotification;

    @BeforeAll
    public static void setup() {
        Notification notification = new Notification();
        internalNotification = new NotificationResource(notification.fakeSenderPaId, notification.fakeGroup, null, null, null);
    }

    @Test
    void checkOK() {
        AuthorizationActor actor = new AuthorizationActor("PA", internalNotification.getSenderPaId(), null, null);
        NotificationSenderRead rule = new NotificationSenderRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertTrue(outcome.isAuthorized());
    }

    @Test
    void checkKOCxType() {
        AuthorizationActor actor = new AuthorizationActor("PF", internalNotification.getSenderPaId(), null, null);
        NotificationSenderRead rule = new NotificationSenderRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertFalse(outcome.isAuthorized());
        assertEquals("ACTOR_ISNT_PA_TYPE", outcome.getReason());
    }

    @Test
    void checkKOCxId() {
        AuthorizationActor actor = new AuthorizationActor("PA", "anotherCxId", null, null);
        NotificationSenderRead rule = new NotificationSenderRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertFalse(outcome.isAuthorized());
        assertEquals("SENDERID_DOESNT_MATCH", outcome.getReason());
    }

    @Test
    void checkKOGroups() {
        List<String> groups = new ArrayList<>();
        groups.add("Group1");
        groups.add("Group2");
        AuthorizationActor actor = new AuthorizationActor("PA", internalNotification.getSenderPaId(), groups, null);
        NotificationSenderRead rule = new NotificationSenderRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertFalse(outcome.isAuthorized());
        assertEquals("GROUPS_DONT_MATCH", outcome.getReason());
    }
}
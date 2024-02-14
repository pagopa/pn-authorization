package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.mocks.Mandate;
import it.pagopa.pn.authorization.mocks.Notification;
import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.MandateResource;
import it.pagopa.pn.authorization.models.NotificationResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationReceiverReadTest {
    static NotificationResource internalNotification;

    @BeforeAll
    public static void setup() {
        Notification notification = new Notification();
        Mandate mandate = new Mandate();
        MandateResource internalMandate = new MandateResource(mandate.fakeMandateId, mandate.fakeDateFrom, mandate.fakeDelegator, mandate.fakeDelegate);
        List<MandateResource> internalMandates = new ArrayList<>();
        internalMandates.add(internalMandate);
        internalNotification = new NotificationResource(notification.fakeSenderPaId,
                notification.fakeGroup, notification.fakeSentAt, notification.fakeRecipientIds, internalMandates);
    }

    @Test
    void checkOK() {
        AuthorizationActor actor = new AuthorizationActor("PF", internalNotification.getSenderPaId(), null, null);
        NotificationReceiverRead rule = new NotificationReceiverRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertTrue(outcome.isAuthorized());
    }

    @Test
    void checkKOCxType() {
        AuthorizationActor actor = new AuthorizationActor("PA", internalNotification.getSenderPaId(), null, null);
        NotificationReceiverRead rule = new NotificationReceiverRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertFalse(outcome.isAuthorized());
        assertEquals("ACTOR_ISNT_PF_TYPE", outcome.getReason());
    }

    @Test
    void checkKOCxId() {
        AuthorizationActor actor = new AuthorizationActor("PF", "anotherCxId", null, null);
        NotificationReceiverRead rule = new NotificationReceiverRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertFalse(outcome.isAuthorized());
        assertEquals("ACTOR_ISNT_RECIVER", outcome.getReason());
    }

    @Test
    void checkOKMandate() {
        AuthorizationActor actor = new AuthorizationActor("PF", "anotherCxId", null, "cxId");
        NotificationReceiverRead rule = new NotificationReceiverRead();
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertTrue(outcome.isAuthorized());
    }

    @Test
    void checkKOMandate() {
        AuthorizationActor actor = new AuthorizationActor("PF", "anotherCxId", null, "cxId");
        NotificationReceiverRead rule = new NotificationReceiverRead();
        internalNotification.getMandates().forEach(m -> m.setDelegator("anotherCxId"));
        BaseAuthorizationOutcome outcome = rule.evaluate(actor, internalNotification);
        assertFalse(outcome.isAuthorized());
        assertEquals("ACTOR_ISNT_DELEGATE", outcome.getReason());
    }
}
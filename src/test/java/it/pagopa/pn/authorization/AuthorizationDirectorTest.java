package it.pagopa.pn.authorization;

import it.pagopa.pn.authorization.mocks.Notification;
import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.NotificationResource;
import it.pagopa.pn.authorization.operation.AuthorizationOperation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AuthorizationDirectorTest {


    private static AuthorizationDirector director;

    @BeforeAll
    public static void setup() {

        director = new AuthorizationDirector();
        director
                .addConfig(AuthorizationOperation.NOTIFICATION_SENDER_READ,
                        (SenderActor actor) -> new AuthorizationActor(actor.fakeCxType, actor.fakeCxId, null, null),
                        (Notification resource) -> new NotificationResource(resource.fakeSenderPaId, resource.fakeGroup, null, null, null))
                .addConfig(AuthorizationOperation.NOTIFICATION_RECIVIER_READ,
                        (ReceiverActor actor) -> new AuthorizationActor(actor.fakeCxType, actor.fakeCxId, null, null),
                        (Notification resource) -> new NotificationResource(resource.fakeSenderPaId, resource.fakeGroup, null, resource.fakeRecipientIds, null))
                .addConfig(AuthorizationOperation.NOTIFICATION_RECIVIER_COMPANY_READ,
                        (ReceiverCompanyActor actor) -> new AuthorizationActor(actor.fakeCxType, actor.fakeCxId, null, null),
                        (Notification resource) -> new NotificationResource(resource.fakeSenderPaId, resource.fakeGroup, null, resource.fakeRecipientIds, null));
    }

    @Test
    public void checkSenderOK() throws Exception {
        BaseAuthorizationOutcome outcome = director.check(AuthorizationOperation.NOTIFICATION_SENDER_READ, new SenderActor(), new Notification());
        assertTrue(outcome.isAuthorized());
    }

    @Test
    public void checkSenderKO() throws Exception {
        SenderActor actor = new SenderActor();
        actor.fakeCxId = "anotherCxId";
        BaseAuthorizationOutcome outcome = director.check(AuthorizationOperation.NOTIFICATION_SENDER_READ, actor, new Notification());
        assertFalse(outcome.isAuthorized());
    }

    @Test
    public void checkReceiverOK() throws Exception {
        BaseAuthorizationOutcome outcome = director.check(AuthorizationOperation.NOTIFICATION_RECIVIER_READ, new ReceiverActor(), new Notification());
        assertTrue(outcome.isAuthorized());
    }

    @Test
    public void checkReceiverKO() throws Exception {
        ReceiverActor actor = new ReceiverActor();
        actor.fakeCxId = "anotherCxId";
        BaseAuthorizationOutcome outcome = director.check(AuthorizationOperation.NOTIFICATION_RECIVIER_READ, actor, new Notification());
        assertFalse(outcome.isAuthorized());
    }

    @Test
    public void checkReceiverCompanyOK() throws Exception {
        BaseAuthorizationOutcome outcome = director.check(AuthorizationOperation.NOTIFICATION_RECIVIER_COMPANY_READ, new ReceiverCompanyActor(), new Notification());
        assertTrue(outcome.isAuthorized());
    }

    @Test
    public void checkReceiverCompanyKO() throws Exception {
        ReceiverCompanyActor actor = new ReceiverCompanyActor();
        actor.fakeCxId = "anotherCxId";
        BaseAuthorizationOutcome outcome = director.check(AuthorizationOperation.NOTIFICATION_RECIVIER_COMPANY_READ, actor, new Notification());
        assertFalse(outcome.isAuthorized());
    }

    public static class SenderActor {
        public String fakeCxType = "PA";
        public String fakeCxId = "cxId";
        public List<String> fakeGroups = new ArrayList<>();

        public SenderActor() {
            fakeGroups.add("Group1");
            fakeGroups.add("Group2");
        }
    }

    public static class ReceiverActor {
        public String fakeCxType = "PF";
        public String fakeCxId = "cxId";
    }

    public static class ReceiverCompanyActor {
        public String fakeCxType = "PG";
        public String fakeCxId = "cxId";

        public List<String> fakeGroups = new ArrayList<>();

        public ReceiverCompanyActor() {
            fakeGroups.add("Group1");
            fakeGroups.add("Group2");
        }
    }

}
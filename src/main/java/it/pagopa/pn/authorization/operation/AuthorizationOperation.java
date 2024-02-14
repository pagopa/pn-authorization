package it.pagopa.pn.authorization.operation;

import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.NotificationResource;
import it.pagopa.pn.authorization.rules.NotificationReceiverCompanyRead;
import it.pagopa.pn.authorization.rules.NotificationReceiverRead;
import it.pagopa.pn.authorization.rules.NotificationSenderRead;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthorizationOperation {
    public static final Operation<AuthorizationActor, NotificationResource> NOTIFICATION_SENDER_READ =
            new Operation<>("NOTIFICATION_SENDER_READ", new NotificationSenderRead());
    public static final Operation<AuthorizationActor, NotificationResource> NOTIFICATION_RECIVIER_READ =
            new Operation<>("NOTIFICATION_RECIVIER_READ", new NotificationReceiverRead());
    public static final Operation<AuthorizationActor, NotificationResource> NOTIFICATION_RECIVIER_COMPANY_READ =
            new Operation<>("NOTIFICATION_RECIVIER_COMPANY_READ", new NotificationReceiverCompanyRead());
}
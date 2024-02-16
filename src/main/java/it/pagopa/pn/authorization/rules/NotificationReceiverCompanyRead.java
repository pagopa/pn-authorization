package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.MandateResource;
import it.pagopa.pn.authorization.models.NotificationResource;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

/**
 * This is the rule that checks if an actor can read a specific notification as a PG
 */
public class NotificationReceiverCompanyRead extends AuthorizationRule<AuthorizationActor, NotificationResource> {

    /**
     * This method evaluate if:
     * - actor is a PG
     * - groups of the actor contain the group of the notification
     * - actor is the receiver or the delegate of the notification
     *
     * @param actor    this is the actor that must be authorized
     * @param resource this is the data over witch check the authorization
     * @return the result of the evaluation
     */
    @Override
    public BaseAuthorizationOutcome evaluate(AuthorizationActor actor, NotificationResource resource) {
        boolean authorized;
        String reason;

        if ("PG".equals(actor.getCxType())) {
            if (checkGroups(actor.getCxGroups(), resource.getGroup())) {
                String receiverId = actor.getCxId();
                int rIdx = resource.getRecipientIds().indexOf(receiverId);
                // gestione deleghe
                String mandateId = actor.getMandateId();
                boolean isMandate = false;
                if (rIdx < 0 && mandateId != null) {
                    List<MandateResource> mandates = resource.getMandates();
                    isMandate = checkMandate(mandates, resource.getSentAt());
                    if (isMandate) {
                        String delegatedCxId = mandates.get(0).getDelegator();
                        rIdx = resource.getRecipientIds().indexOf(delegatedCxId);
                    }
                }
                if (rIdx >= 0) {
                    authorized = true;
                    reason = null;
                } else {
                    authorized = false;
                    reason = isMandate ? "ACTOR_ISNT_DELEGATE" : "ACTOR_ISNT_RECIVER";
                }
            } else {
                authorized = false;
                reason = "GROUPS_DONT_MATCH";
            }
        } else {
            authorized = false;
            reason = "ACTOR_ISNT_PG_TYPE";
        }

        return new BaseAuthorizationOutcome(authorized, reason);
    }

    /**
     * Checks if the notifications has mandates and they are still valid
     *
     * @param mandates list of the mandates linked to the notification
     * @param sentAt   when the notification was sent
     * @return the result of the check
     */
    private boolean checkMandate(List<MandateResource> mandates, OffsetDateTime sentAt) {
        return mandates.isEmpty() || OffsetDateTime.parse(Objects.requireNonNull(mandates.get(0).getDateFrom())).isAfter(sentAt);
    }

    /**
     * Checks if actor has groups and if its groups contains the group of the notification
     *
     * @param groups actor groups
     * @param group  notification group
     * @return the result of the check
     */
    private boolean checkGroups(List<String> groups, String group) {
        // if groups is null or is empty, it means that the actor is an administrator and can access the resource
        // if groups is not null and not empty, it means that the actor can access the resource only if in its groups there is the resource's one
        return groups == null || groups.isEmpty() || groups.contains(group);
    }
}
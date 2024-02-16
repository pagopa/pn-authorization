package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.NotificationResource;

import java.util.List;
import java.util.Objects;

/**
 * This is the rule that checks if an actor can read a specific notification as a PA
 */
public class NotificationSenderRead extends AuthorizationRule<AuthorizationActor, NotificationResource> {

    /**
     * This method evaluate if:
     * - actor is a PA
     * - the context id of the actor is the same of the sender that created the notification
     * - groups of the actor contain the group of the notification
     *
     * @param actor    this is the actor that must be authorized
     * @param resource this is the data over witch check the authorization
     * @return the result of the evaluation
     */
    @Override
    public BaseAuthorizationOutcome evaluate(AuthorizationActor actor, NotificationResource resource) {
        boolean authorized;
        String reason;
        // do checks
        if ("PA".equals(actor.getCxType())) {
            String senderId = actor.getCxId();
            if (Objects.equals(senderId, resource.getSenderPaId())) {
                if (checkGroups(actor.getCxGroups(), resource.getGroup())) {
                    authorized = true;
                    reason = null;
                } else {
                    authorized = false;
                    reason = "GROUPS_DONT_MATCH";
                }
            } else {
                authorized = false;
                reason = "SENDERID_DOESNT_MATCH";
            }
        } else {
            authorized = false;
            reason = "ACTOR_ISNT_PA_TYPE";
        }

        return new BaseAuthorizationOutcome(authorized, reason);
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
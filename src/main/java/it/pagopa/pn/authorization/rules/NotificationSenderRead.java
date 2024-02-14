package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.models.AuthorizationActor;
import it.pagopa.pn.authorization.models.NotificationResource;

import java.util.List;
import java.util.Objects;

public class NotificationSenderRead extends AuthorizationRule<AuthorizationActor, NotificationResource> {

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

    private boolean checkGroups(List<String> groups, String group) {
        // if groups is null or is empty, it means that the actor is an administrator and can access the resource
        // if groups is not null and not empty, it means that the actor can access the resource only if in its groups there is the resource's one
        return groups == null || groups.isEmpty() || groups.contains(group);
    }
}
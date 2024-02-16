package it.pagopa.pn.authorization.operation;

import it.pagopa.pn.authorization.rules.AuthorizationRule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * This is the single operation class.
 * It links the name of the operation (i.e. NOTIFICATION_SENDER_READ) and its rule.
 *
 * @param <A> generic type for the Actor
 * @param <R> generic type for the Resource
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Operation<A, R> {
    private String name;
    private AuthorizationRule<A, R> defaultRule;
}
package it.pagopa.pn.authorization.operation;

import it.pagopa.pn.authorization.rules.AuthorizationRule;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class Operation<A, R> {
    private String name;
    private AuthorizationRule<A, R> defaultRule;
}
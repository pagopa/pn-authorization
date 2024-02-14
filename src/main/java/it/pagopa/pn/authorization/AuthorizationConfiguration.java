package it.pagopa.pn.authorization;

import it.pagopa.pn.authorization.rules.AuthorizationRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@AllArgsConstructor
class AuthorizationConfiguration<A, R, AM, RM> {
    private final @Getter AuthorizationRule<A, R> rule;
    private @Getter Function<AM, A> actorMapper;
    private @Getter Function<RM, R> resourceMapper;

}
package it.pagopa.pn.authorization;

import it.pagopa.pn.authorization.rules.AuthorizationRule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

/**
 * This is the internal configuration used by the director.
 * It contains the rule linked to the operation for which we want check the authorization,
 * and the two mappers that map the external model to the internal ones used by the rules
 *
 * @param <A>  generic type for the internal Actor
 * @param <R>  generic type for the internal Resource
 * @param <AM> generic type for the external Actor
 * @param <RM> generic type for the external Resource
 */
@AllArgsConstructor
class AuthorizationConfiguration<A, R, AM, RM> {
    private final @Getter AuthorizationRule<A, R> rule;
    private @Getter Function<AM, A> actorMapper;
    private @Getter Function<RM, R> resourceMapper;

}
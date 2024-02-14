package it.pagopa.pn.authorization;

import it.pagopa.pn.authorization.operation.Operation;
import it.pagopa.pn.authorization.rules.AuthorizationRule;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This what defines the authorization process.
 * The so-called director runs the rule/rules added and returns the result of the checking process.
 */
public class AuthorizationDirector {

    /**
     * The list of the configuration added to the authorization process.
     *
     * @see AuthorizationConfiguration
     */
    private final Map<String, AuthorizationConfiguration<?, ?, ?, ?>> configs = new IdentityHashMap<>();

    /**
     * The method used to add a new configuration to the authorization process.
     * This method is useful for those cases that are not yet configured in the library.
     * Avoid using this method if it is not strictly necessary.
     *
     * @param operation the specific operation (i.e. NOTIFICATION_SENDER_READ).
     * @param rule      the rule linked to the specific operation.
     * @return the instance of the director.
     */
    protected <A, R, AM, RM> AuthorizationDirector addConfig(Operation<A, R> operation, AuthorizationRule<A, R> rule, Function<AM, A> actorMapper, Function<RM, R> resourceMapper) {
        AuthorizationConfiguration<A, R, AM, RM> config = new AuthorizationConfiguration<>(rule, actorMapper, resourceMapper);
        configs.put(operation.getName(), config);
        return this;
    }

    /**
     * The method used to add known configuration to the authorization process.
     * This method must be used in most cases.
     *
     * @param operation the specific operation (i.e. NOTIFICATION_SENDER_READ).
     * @return the instance of the director.
     */
    public <A, R, AM, RM> AuthorizationDirector addConfig(Operation<A, R> operation, Function<AM, A> actorMapper, Function<RM, R> resourceMapper) {
        // get rule corresponding to specified operation
        AuthorizationRule<A, R> rule = operation.getDefaultRule();
        return addConfig(operation, rule, actorMapper, resourceMapper);
    }


    /**
     * The method used to evaluate the rules and return the result of the check.
     *
     * @param operation the operation, among those added, that must be checked
     * @return result of the evaluation.
     */
    public <A, R, AM, RM> BaseAuthorizationOutcome check(Operation<A, R> operation, AM actor, RM resource) throws Exception {
        // get the config that corresponds to the signaled operation
        AuthorizationConfiguration actualConfig = configs.get(operation.getName());
        if (actualConfig != null) {
            // get the rule
            AuthorizationRule<A, R> rule = operation.getDefaultRule();
            // get the actor and the resource as result of the mappers
            Function<AM, A> actorMapper = actualConfig.getActorMapper();
            Function<RM, R> resourceMapper = actualConfig.getResourceMapper();
            A internalActor = actorMapper.apply(actor);
            R internalResource = resourceMapper.apply(resource);
            // evaluate the rule
            return rule.evaluate(internalActor, internalResource);
        }

        // return outcome;
        return new BaseAuthorizationOutcome();
    }
}
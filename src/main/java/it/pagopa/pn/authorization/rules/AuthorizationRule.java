package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;

/**
 * Rule is the smallest part of an authorization process.
 * It contains a specific check that must be met to pass the authorization.
 * This is an abstract class and each definition of a rule must expand it.
 *
 * @param <A> generic type for the Actor
 * @param <R> generic type for the Resource
 */
public abstract class AuthorizationRule<A, R> {

    /**
     * The method that contains the condition/conditions
     * that must be met to pass the authorization.
     * Each rule must override this method and implement its own logic.
     *
     * @param actor    this is the actor that must be authorized
     * @param resource this is the data over witch check the authorization
     * @return the result of the evaluation
     */
    public abstract BaseAuthorizationOutcome evaluate(A actor, R resource) throws Exception;
}
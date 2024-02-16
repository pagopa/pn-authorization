package it.pagopa.pn.authorization.mocks;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.rules.AuthorizationRule;

public class PersonRule extends AuthorizationRule<Person, Family> {
    @Override
    public BaseAuthorizationOutcome evaluate(Person person, Family family) throws Exception {
        boolean authorized;
        String reason;
        if (family.getMembers().stream().anyMatch(p -> p.getName().equals(person.getName()) && p.getSurname().equals(person.getSurname()))) {
            authorized = true;
            reason = null;
        } else if (family.getMembers().stream().anyMatch(p -> p.getSurname() == person.getSurname())) {
            authorized = false;
            reason = "NOT_IN_THIS_FAMILY";
        } else {
            throw new Exception("FAIL!!!");
        }
        return new BaseAuthorizationOutcome(authorized, reason);
    }
}
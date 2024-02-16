package it.pagopa.pn.authorization.rules;

import it.pagopa.pn.authorization.BaseAuthorizationOutcome;
import it.pagopa.pn.authorization.mocks.Family;
import it.pagopa.pn.authorization.mocks.Person;
import it.pagopa.pn.authorization.mocks.PersonRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    static Family family;

    @BeforeAll
    public static void setup() {
        Person father = new Person("Mario", "Rossi");
        Person mother = new Person("Sara", "Bianchi");
        Person son = new Person("Paolo", "Rossi");
        List<Person> members = new ArrayList<>();
        members.add(father);
        members.add(mother);
        members.add(son);
        family = new Family(members);
    }

    @Test
    void checkOK() throws Exception {
        Person person = new Person("Mario", "Rossi");
        PersonRule rule = new PersonRule();
        BaseAuthorizationOutcome outcome = rule.evaluate(person, family);
        assertTrue(outcome.isAuthorized());
    }

    @Test
    void checkKO() throws Exception {
        Person person = new Person("Marta", "Bianchi");
        PersonRule rule = new PersonRule();
        BaseAuthorizationOutcome outcome = rule.evaluate(person, family);
        assertFalse(outcome.isAuthorized());
    }

    @Test
    void checkException() {
        Person person = new Person("Luca", "Verdi");
        PersonRule rule = new PersonRule();
        assertThrows(Exception.class, () -> rule.evaluate(person, family));
    }
}
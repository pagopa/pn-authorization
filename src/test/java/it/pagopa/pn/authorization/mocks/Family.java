package it.pagopa.pn.authorization.mocks;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class Family {
    private List<Person> members;
}
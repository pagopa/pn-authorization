package it.pagopa.pn.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseAuthorizationOutcome {
    private boolean authorized;
    private String reason;
}
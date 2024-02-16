package it.pagopa.pn.authorization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The base outcome of the authorization process.
 * It contains a boolean authorized and a string reason.
 * The reason field contains how the authorization failed.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseAuthorizationOutcome {
    private boolean authorized;
    private String reason;
}
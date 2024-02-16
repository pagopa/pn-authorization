package it.pagopa.pn.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Mandate class.
 * It is used by the NotificationResource class and represent the mandate.
 * This class can be updated, adding more keys if rules require it.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MandateResource {
    private String mandateId;
    private String dateFrom;
    private String delegator;
    private String delegate;
}
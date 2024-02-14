package it.pagopa.pn.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
package it.pagopa.pn.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Actor class.
 * It is used by the rules and represents the actor that must be authorized.
 * This class can be updated, adding more keys if rules require it.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorizationActor {
    private String cxType;
    private String cxId;
    private List<String> cxGroups;
    private String mandateId;
}
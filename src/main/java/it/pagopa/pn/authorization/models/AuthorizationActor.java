package it.pagopa.pn.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
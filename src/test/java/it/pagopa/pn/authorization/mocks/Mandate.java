package it.pagopa.pn.authorization.mocks;

import java.time.OffsetDateTime;

public class Mandate {
    public String fakeMandateId = "mandateId";
    public String fakeDateFrom = OffsetDateTime.now().plusDays(1).toString();
    public String fakeDelegator = "cxId";
    public String fakeDelegate = "cxId";
}
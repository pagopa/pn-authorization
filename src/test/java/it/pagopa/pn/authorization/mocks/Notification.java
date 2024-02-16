package it.pagopa.pn.authorization.mocks;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Notification {
    public String fakeSenderPaId = "cxId";
    public String fakeGroup = "emptyGroup";
    public List<String> fakeRecipientIds = new ArrayList<>();
    public OffsetDateTime fakeSentAt = OffsetDateTime.now();

    public Notification() {
        fakeRecipientIds.add("cxId");
    }
}
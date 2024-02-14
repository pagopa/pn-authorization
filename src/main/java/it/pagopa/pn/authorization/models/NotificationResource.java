package it.pagopa.pn.authorization.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NotificationResource {
    String senderPaId;
    String group;
    OffsetDateTime sentAt;
    List<String> recipientIds;
    List<MandateResource> mandates;
}
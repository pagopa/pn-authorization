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
/**
 * Notification class.
 * It is used by the rules and represents a type of resource over witch check the authorization.
 * This class can be updated, adding more keys if rules require it.
 */
public class NotificationResource {
    String senderPaId;
    String group;
    OffsetDateTime sentAt;
    List<String> recipientIds;
    List<MandateResource> mandates;
}
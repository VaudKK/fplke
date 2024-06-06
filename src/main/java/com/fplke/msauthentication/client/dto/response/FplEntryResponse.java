package com.fplke.msauthentication.client.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record FplEntryResponse(Long id, String playerFirstName, String playerLastName, String playerRegionId,
                               String playerRegionName) {
}

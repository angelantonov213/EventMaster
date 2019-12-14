package com.angelantonov.eventmaster.web.models.ticket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BuyTicketModel {
    private long eventId;
    private long userId;
}

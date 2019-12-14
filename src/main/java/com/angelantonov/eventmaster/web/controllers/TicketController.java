package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.services.model.event.EventDetailsServiceModel;
import com.angelantonov.eventmaster.services.services.EventService;
import com.angelantonov.eventmaster.services.services.TicketsService;
import com.angelantonov.eventmaster.web.models.ticket.BuyTicketForEventModel;
import com.angelantonov.eventmaster.web.models.ticket.BuyTicketModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketController {
    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final TicketsService ticketsService;

    public TicketController(EventService eventService, ModelMapper modelMapper, TicketsService ticketsService) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
        this.ticketsService = ticketsService;
    }

    @GetMapping("/buy/{eventId}")
    public ModelAndView getBuyTicketForm(@PathVariable String eventId, ModelAndView modelAndView) {
        long eventIdLong = Long.parseLong(eventId);

        Optional<EventDetailsServiceModel> optionalEvent = eventService.getEventById(eventIdLong);

        if (optionalEvent.isEmpty()) {
            modelAndView.setViewName("redirect:/events/all");
            return modelAndView;
        }

        EventDetailsServiceModel event = optionalEvent.get();

        if (event.getTicketsSold() >= event.getVenueCapacity()) {
            modelAndView.setViewName("redirect:/events/all");
            return modelAndView;
        }

        BuyTicketForEventModel eventModel = modelMapper.map(
                event,
                BuyTicketForEventModel.class
        );

        BuyTicketModel buyTicketModel = new BuyTicketModel();

        modelAndView.addObject("buyTicketModel", buyTicketModel);
        modelAndView.addObject("event", eventModel);
        modelAndView.setViewName("/ticket/buy");

        return modelAndView;
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute("buyTicketModel") BuyTicketModel model) {
        ticketsService.addTicketFor(model.getEventId(), model.getUserId());

        return "redirect:/events/" + model.getEventId();
    }
}

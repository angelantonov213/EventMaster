package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.services.model.event.EventAllServiceModel;
import com.angelantonov.eventmaster.services.model.venue.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.event.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.model.event.EventDetailsServiceModel;
import com.angelantonov.eventmaster.services.services.EventService;
import com.angelantonov.eventmaster.services.services.UserService;
import com.angelantonov.eventmaster.services.services.VenueService;
import com.angelantonov.eventmaster.web.models.event.UpdateEventModel;
import com.angelantonov.eventmaster.web.models.event.CreateEventModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("events")
public class EventController {

    private final EventService eventService;
    private final ModelMapper modelMapper;
    private final VenueService venueService;
    private final UserService userService;

    public EventController(EventService eventService, ModelMapper modelMapper, VenueService venueService, UserService userService) {
        this.eventService = eventService;
        this.modelMapper = modelMapper;
        this.venueService = venueService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public ModelAndView getAllEvents(ModelAndView modelAndView) {
        return setupEventsAllModelAndViewFrom(eventService.getAllEvents(), modelAndView);
    }

    @GetMapping("/all/{searchText}")
    public ModelAndView getAllEventsByName(@PathVariable String searchText, ModelAndView modelAndView) {
        return setupEventsAllModelAndViewFrom(eventService.getAllEventsThatContainStringInNameOrDescription(searchText), modelAndView);
    }

    private ModelAndView setupEventsAllModelAndViewFrom(List<EventAllServiceModel> events, ModelAndView modelAndView) {
        modelAndView.addObject("allEvents", events);
        modelAndView.setViewName("/event/all");

        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getAddEventForm(ModelAndView modelAndView) {
        modelAndView.setViewName("/event/create");

        // TODO: UserId
        List<AllVenueServiceModel> venuesForAdmin =
                venueService.getVenuesForUser(2);

        modelAndView.addObject("venues", venuesForAdmin);

        CreateEventModel createEventModel = new CreateEventModel();
        modelAndView.addObject("createEventModel", createEventModel);

        return modelAndView;
    }

    @PostMapping("/create")
    public String createEvent(@ModelAttribute("createVenueModel") CreateEventModel model) {
        long eventId = eventService.createEvent(modelMapper.map(model, CreateEventServiceModel.class));

        venueService.addEventForVenue(eventId, model.getVenue().getId());

        return "redirect:/events/create";
    }

    @GetMapping("{eventId}")
    public ModelAndView getEventDetails(@PathVariable String eventId, ModelAndView modelAndView) {
        Optional<EventDetailsServiceModel> optionalVenue = eventService.getEventById(Long.parseLong(eventId));

        modelAndView.setViewName("/event/details");

        if (optionalVenue.isPresent()) {
            modelAndView.addObject("event", optionalVenue.get());

            return modelAndView;
        }

        return modelAndView;
    }

    @GetMapping("/update/{eventId}")
    public ModelAndView getUpdateVenue(@PathVariable String eventId, ModelAndView modelAndView) {
        Optional<EventDetailsServiceModel> optionalEvent = eventService.getEventById(Long.parseLong(eventId));

        modelAndView.setViewName("/event/update");

        UpdateEventModel updateEventModel = new UpdateEventModel();
        modelAndView.addObject("updateEventModel", updateEventModel);

        if (optionalEvent.isPresent()) {
            EventDetailsServiceModel eventDetailsServiceModel = optionalEvent.get();

            // TODO: UserId
            List<AllVenueServiceModel> venuesForAdmin =
                    venueService.getVenuesForUser(2);

            modelAndView.addObject("venues", venuesForAdmin);
            modelAndView.addObject("event", eventDetailsServiceModel);

            return modelAndView;
        }

        return modelAndView;
    }

    @PostMapping("/update")
    public String updateVenue(@ModelAttribute("updateEvent") UpdateEventModel updateEventModel) {
        Optional<EventDetailsServiceModel> optionalEvent = eventService.getEventById(updateEventModel.getId());

        if (optionalEvent.isEmpty()) {
            return "redirect:/events/" + updateEventModel.getId();
        }

        eventService.updateEvent(modelMapper.map(updateEventModel, EventDetailsServiceModel.class));

        return "redirect:/events/all";
    }
}

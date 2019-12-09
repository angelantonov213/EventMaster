package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.services.model.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.CreateEventServiceModel;
import com.angelantonov.eventmaster.services.services.EventService;
import com.angelantonov.eventmaster.services.services.UserService;
import com.angelantonov.eventmaster.services.services.VenueService;
import com.angelantonov.eventmaster.web.models.event.CreateEventModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @GetMapping("/create")
    public ModelAndView getAddEventForm(ModelAndView modelAndView) {
        modelAndView.setViewName("/event/create");

        List<AllVenueServiceModel> venuesForAdmin =
                venueService.getVenuesForUser(modelMapper.map(userService.getUserById(2).get(), User.class));

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
}

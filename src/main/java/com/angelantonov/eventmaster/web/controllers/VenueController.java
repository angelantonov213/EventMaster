package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.services.model.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.model.VenueDetailsServiceModel;
import com.angelantonov.eventmaster.services.services.UserService;
import com.angelantonov.eventmaster.services.services.VenueService;
import com.angelantonov.eventmaster.web.models.venue.CreateVenueModel;
import com.angelantonov.eventmaster.web.models.venue.VenueAdminSelectionModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public VenueController(VenueService venueService, UserService userService, ModelMapper modelMapper) {
        this.venueService = venueService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView getCreateVenueForm(ModelAndView modelAndView) {
        List<VenueAdminSelectionModel> admins = userService.getUsersForRole(Role.ADMIN)
                .stream()
                .map(user -> new VenueAdminSelectionModel(user.getId(), user.getEmail()))
                .collect(Collectors.toList());

        modelAndView.setViewName("/venue/create");
        modelAndView.addObject("adminUsers", admins);

        CreateVenueModel createVenueModel = new CreateVenueModel();
        modelAndView.addObject("createVenueModel", createVenueModel);

        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView getAllVenues(ModelAndView modelAndView) {
        List<AllVenueServiceModel> allVenues = venueService.getAllVenues();

        modelAndView.setViewName("/venue/all");
        modelAndView.addObject("allVenues", allVenues);

        return modelAndView;
    }

    @GetMapping("/{venueId}")
    public ModelAndView getVenue(@PathVariable String venueId, ModelAndView modelAndView) {
        Optional<VenueDetailsServiceModel> optionalVenue = venueService.getVenueById(Long.parseLong(venueId));


        modelAndView.setViewName("/venue/details");

        if (optionalVenue.isPresent()) {
            modelAndView.addObject("venue", optionalVenue.get());

            return modelAndView;
        }

        return modelAndView;
    }

    @PostMapping("/create")
    public String createVenue(@ModelAttribute("createVenueModel") CreateVenueModel createVenueModel) {
        Venue venue = modelMapper.map(createVenueModel, Venue.class);
        for (User admin: createVenueModel.getAdmins()) {
            List<Venue> newVenues = admin.getVenues();
            newVenues.add(venue);
            admin.setVenues(newVenues);
        }

        venueService.createVenue(modelMapper.map(createVenueModel, CreateVenueServiceModel.class));

        return "redirect:/venues/create";
    }
}

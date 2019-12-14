package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.services.model.venue.AllVenueServiceModel;
import com.angelantonov.eventmaster.services.model.venue.CreateVenueServiceModel;
import com.angelantonov.eventmaster.services.model.venue.VenueDetailsServiceModel;
import com.angelantonov.eventmaster.services.model.venue.UpdateVenueServiceModel;
import com.angelantonov.eventmaster.services.services.UserService;
import com.angelantonov.eventmaster.services.services.VenueService;
import com.angelantonov.eventmaster.web.models.venue.CreateVenueModel;
import com.angelantonov.eventmaster.web.models.venue.UpdateVenueModel;
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
                .map(user -> new VenueAdminSelectionModel(user.getId(), user.getEmail(), false))
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

    @GetMapping("/update/{venueId}")
    public ModelAndView getUpdateVenue(@PathVariable String venueId, ModelAndView modelAndView) {
        Optional<VenueDetailsServiceModel> optionalVenue = venueService.getVenueById(Long.parseLong(venueId));

        modelAndView.setViewName("/venue/update");

        UpdateVenueModel updateVenueModel = new UpdateVenueModel();
        modelAndView.addObject("updateVenueModel", updateVenueModel);

        if (optionalVenue.isPresent()) {
            VenueDetailsServiceModel venue = optionalVenue.get();

            List<VenueAdminSelectionModel> admins = userService.getUsersForRole(Role.ADMIN)
                    .stream()
                    .map(
                            user -> new VenueAdminSelectionModel(user.getId(), user.getEmail(), hasUserWithIdIn(venue.getAdmins(), user.getId()))
                    )
                    .collect(Collectors.toList());

            modelAndView.addObject("adminUsers", admins);

            modelAndView.addObject("venue", venue);

            return modelAndView;
        }

        return modelAndView;
    }

    @PostMapping("/create")
    public String createVenue(@ModelAttribute("createVenueModel") CreateVenueModel createVenueModel) {
        updateAdmins(createVenueModel);

        long venueId = venueService.createVenue(modelMapper.map(createVenueModel, CreateVenueServiceModel.class));

        for (User admin: createVenueModel.getAdmins()) {
            userService.addVenueForUser(venueId, admin.getId());
        }

        return "redirect:/venues/create";
    }

    @PostMapping("/update")
    public String updateVenue(@ModelAttribute("updateVenueModel") UpdateVenueModel updateVenueModel) {
        VenueDetailsServiceModel venue = venueService.getVenueById(updateVenueModel.getId()).get();

        for (User oldAdmin: venue.getAdmins()) {
            if (!updateVenueModel.getAdmins().contains(oldAdmin)) {
                userService.removeVenueForUser(updateVenueModel.getId(), oldAdmin.getId());
            }
        }

        for(User user: updateVenueModel.getAdmins()) {
            userService.addVenueForUser(updateVenueModel.getId(), user.getId());
        }

        venueService.updateVenue(modelMapper.map(updateVenueModel, UpdateVenueServiceModel.class));

        return "redirect:/venues/all";
    }

    private void updateAdmins(CreateVenueModel venueModel) {
        Venue venue = modelMapper.map(venueModel, Venue.class);
        venue.setAdmins(List.of());
        for (User admin: venueModel.getAdmins()) {
            List<Venue> newVenues = admin.getVenues();
            newVenues.add(venue);
        }
    }

    private boolean hasUserWithIdIn(List<User> users, Long id) {
        for (User user: users) {
            if (user.getId() == id) {
                return true;
            }
        }

        return false;
    }
}

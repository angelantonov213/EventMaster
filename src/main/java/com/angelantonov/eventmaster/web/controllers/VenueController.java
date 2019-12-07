package com.angelantonov.eventmaster.web.controllers;

import com.angelantonov.eventmaster.data.models.Role;
import com.angelantonov.eventmaster.data.models.User;
import com.angelantonov.eventmaster.data.models.Venue;
import com.angelantonov.eventmaster.data.repositories.UsersRepository;
import com.angelantonov.eventmaster.data.repositories.VenuesRepository;
import com.angelantonov.eventmaster.web.models.CreateVenueModel;
import com.angelantonov.eventmaster.web.models.VenueAdminSelectionModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/venues")
public class VenueController {

    private final UsersRepository usersRepository;
    private final VenuesRepository venuesRepository;
    private final ModelMapper modelMapper;

    public VenueController(UsersRepository usersRepository, VenuesRepository venuesRepository, ModelMapper modelMapper) {
        this.usersRepository = usersRepository;
        this.venuesRepository = venuesRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/create")
    public ModelAndView getCreateVenueForm(ModelAndView modelAndView) {
        List<VenueAdminSelectionModel> admins = usersRepository.findByRolesEquals(Role.ADMIN)
                .stream()
                .map(user -> new VenueAdminSelectionModel(user.getId(), user.getEmail()))
                .collect(Collectors.toList());

        modelAndView.setViewName("/venue/create");
        modelAndView.addObject("adminUsers", admins);

        CreateVenueModel createVenueModel = new CreateVenueModel();
        modelAndView.addObject("createVenueModel", createVenueModel);

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

        venuesRepository.save(modelMapper.map(createVenueModel, Venue.class));

        return "redirect:/venues/create";
    }
}

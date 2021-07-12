package com.zensar.olxadvertises.controller;

import com.zensar.olxadvertises.dto.AdvertiseDto;
import com.zensar.olxadvertises.service.AdvertiseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class AdvertisesController {

    @Autowired
    AdvertiseService advertiseService;

    @PostMapping("/advertise")
    public ResponseEntity<?> createAdvertise(@RequestBody AdvertiseDto advertiseDto,
                                             @RequestHeader("auth-token") String authToken) {
        return advertiseService.createAdvertise(advertiseDto, authToken);
    }

    @PutMapping("/advertise/{id}")
    public ResponseEntity<?> updateAdvertise(@RequestBody AdvertiseDto advertiseDto,
                                             @RequestHeader("auth-token") String authToken,
                                             @PathVariable("id") Optional<Integer> id) {
        return advertiseService.updateAdvertise(advertiseDto, authToken, id.get());
    }

    @GetMapping("/user/advertise")
    public ResponseEntity<?> getAdvertisementsForAUser(@RequestHeader("auth-token") String authToken) {
        return advertiseService.getAdvertisementsForAUser(authToken);
    }

    @GetMapping("/user/advertise/{postId}")
    public ResponseEntity<?> getAdvertisementBasedOnId(@PathParam(value = "postId") int postId,
                                                       @RequestHeader("auth-token") String authToken) {
        return advertiseService.getAdvertisementBasedOnId(postId, authToken);
    }

    @GetMapping("/advertise/search/filtercriteria")
    public ResponseEntity<?> getFilteredAdvertisements(@RequestParam("searchText") String searchText,
                                                       @RequestParam("category") String category,
                                                       @RequestParam("postedBy") String postedBy) {
        return advertiseService.getFilteredAdvertisements(searchText, category, postedBy);
    }

    @GetMapping("/advertise/search")
    public ResponseEntity<?> searchAdvertisement(@RequestParam("searchText") String searchText) {
        return advertiseService.searchAdvertisement(searchText);
    }

    @GetMapping("/advertise/{postId}")
    public ResponseEntity<?> getAdvertisementById(@PathParam(value = "postId") int postId) {
        return advertiseService.getAdvertisementById(postId);
    }

}

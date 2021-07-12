package com.zensar.olxadvertises.service;

import com.zensar.olxadvertises.dto.AdvertiseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdvertiseService {

    ResponseEntity<AdvertiseDto> createAdvertise(AdvertiseDto advertiseDto, String authToken);

    ResponseEntity<AdvertiseDto> updateAdvertise(AdvertiseDto advertiseDto, String authToken, int id);

    ResponseEntity<List<AdvertiseDto>> getAdvertisementsForAUser(String authToken);

    ResponseEntity<AdvertiseDto> getAdvertisementBasedOnId(int postId, String authToken);

    ResponseEntity<List<AdvertiseDto>> getFilteredAdvertisements(String searchText, String category, String postedBy);

    ResponseEntity<List<AdvertiseDto>> searchAdvertisement(String searchText);

    ResponseEntity<AdvertiseDto> getAdvertisementById(int postId);
}

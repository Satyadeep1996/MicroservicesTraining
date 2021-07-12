package com.zensar.olxadvertises.service.impl;

import com.zensar.olxadvertises.dao.AdvertiseDao;
import com.zensar.olxadvertises.dto.AdvertiseDto;
import com.zensar.olxadvertises.dto.CategoryDto;
import com.zensar.olxadvertises.dto.StatusDto;
import com.zensar.olxadvertises.dto.UserDto;
import com.zensar.olxadvertises.exception.InvalidAdvertisementException;
import com.zensar.olxadvertises.exception.InvalidUserException;
import com.zensar.olxadvertises.model.Advertise;
import com.zensar.olxadvertises.service.AdvertiseService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AdvertiseServiceImpl implements AdvertiseService {

    @Autowired
    AdvertiseDao advertiseDao;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseEntity<AdvertiseDto> createAdvertise(AdvertiseDto advertiseDto, String authToken) {
        if (!isLoggedInUser(authToken))
            throw new InvalidUserException("Invalid Token");
        Advertise advertise = new Advertise(advertiseDto);
        ResponseEntity<UserDto> userResponse =
                restTemplate.getForEntity("http://OLX-LOGIN/user",
                        UserDto.class);
        UserDto userDto = userResponse.getBody();
        advertise.setUserName(userDto.getUserName());
        advertiseDao.save(advertise);
        advertiseDto.setId(advertise.getId());
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        Optional<CategoryDto> category = categories.stream()
                .filter(categoryDto -> categoryDto.getId() == advertiseDto.getCategoryId()).findAny();
        Optional<StatusDto> status = statuses.stream()
                .filter(statusDto -> statusDto.getId() == 1).findAny();
        advertiseDto.setCategory(category.get().getCategory());
        advertiseDto.setStatus(status.get().getStatus());
        advertiseDto.setCreatedDate(new Date());
        advertiseDto.setModifiedDate(new Date());
        return ResponseEntity.ok(advertiseDto);
    }

    @Override
    public ResponseEntity<AdvertiseDto> updateAdvertise(AdvertiseDto advertiseDto, String authToken, int id) {
        if (!isLoggedInUser(authToken))
            throw new InvalidUserException("Invalid Token");
        if (id == 0)
            throw new InvalidAdvertisementException("Invalid Advertisement");

        Advertise advertise = advertiseDao.getById(id);
        advertise.setCategoryId(advertiseDto.getCategoryId());
        advertise.setTitle(advertiseDto.getTitle());
        advertise.setDescription(advertiseDto.getDescription());
        advertise.setUserName(advertise.getUserName());
        advertise.setModifiedDate(new Date());
        advertise.setStatusId(advertiseDto.getStatusId());
        advertiseDao.save(advertise);
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        Optional<CategoryDto> category = categories.stream()
                .filter(categoryDto -> categoryDto.getId() == advertiseDto.getCategoryId()).findAny();
        Optional<StatusDto> status = statuses.stream()
                .filter(statusDto -> statusDto.getId() == advertiseDto.getStatusId()).findAny();
        advertiseDto.setCategory(category.get().getCategory());
        advertiseDto.setStatus(status.get().getStatus());
        advertiseDto.setId(advertise.getId());
        return ResponseEntity.ok(advertiseDto);
    }

    @Override
    public ResponseEntity<List<AdvertiseDto>> getAdvertisementsForAUser(String authToken) {
        if (!isLoggedInUser(authToken))
            throw new InvalidUserException("Invalid Token");

        ResponseEntity<UserDto> userResponse =
                restTemplate.getForEntity("http://OLX-LOGIN/user",
                        UserDto.class);
        UserDto userDto = userResponse.getBody();
        List<Advertise> advertiseList = advertiseDao.findByUserName(userDto.getUserName());
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        List<AdvertiseDto> advertiseDtos = new ArrayList<>();
        if (!advertiseList.isEmpty()) {
            advertiseList.forEach(advertise -> {
                Optional<CategoryDto> category = categories.stream()
                        .filter(categoryDto -> categoryDto.getId() == advertise.getCategoryId()).findAny();
                Optional<StatusDto> status = statuses.stream()
                        .filter(statusDto -> statusDto.getId() == advertise.getStatusId()).findAny();
                AdvertiseDto advertiseDto = new AdvertiseDto(advertise);
                advertiseDto.setCategory(category.get().getCategory());
                advertiseDto.setStatus(status.get().getStatus());
                advertiseDtos.add(advertiseDto);
            });
        }
        return ResponseEntity.ok(advertiseDtos);
    }

    @Override
    public ResponseEntity<AdvertiseDto> getAdvertisementBasedOnId(int postId, String authToken) {
        if (!isLoggedInUser(authToken))
            throw new InvalidUserException("Invalid Token");
        if (postId == 0)
            throw new InvalidAdvertisementException("Invalid Advertisement");

        Advertise advertise = advertiseDao.getById(postId);
        if (advertise == null)
            throw new InvalidAdvertisementException("Invalid Advertisement");
        AdvertiseDto advertiseDto = new AdvertiseDto(advertise);
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        Optional<CategoryDto> category = categories.stream()
                .filter(categoryDto -> categoryDto.getId() == advertise.getCategoryId()).findAny();
        Optional<StatusDto> status = statuses.stream()
                .filter(statusDto -> statusDto.getId() == advertise.getStatusId()).findAny();
        advertiseDto.setCategory(category.get().getCategory());
        advertiseDto.setStatus(status.get().getStatus());
        return ResponseEntity.ok(advertiseDto);
    }

    @Override
    public ResponseEntity<List<AdvertiseDto>> getFilteredAdvertisements(String searchText, String categoryText, String postedBy) {
        ResponseEntity<UserDto> userResponse =
                restTemplate.getForEntity("http://OLX-LOGIN/user",
                        UserDto.class);
        UserDto userDto = userResponse.getBody();
        List<Advertise> advertiseList = advertiseDao.findByUserName(userDto.getUserName());
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        List<AdvertiseDto> advertiseDtos = new ArrayList<>();
        if (!advertiseList.isEmpty()) {
            advertiseList.forEach(advertise -> {
                Optional<CategoryDto> category = categories.stream()
                        .filter(categoryDto -> categoryDto.getId() == advertise.getCategoryId()).findAny();
                Optional<StatusDto> status = statuses.stream()
                        .filter(statusDto -> statusDto.getId() == advertise.getStatusId()).findAny();
                AdvertiseDto advertiseDto = new AdvertiseDto(advertise);
                advertiseDto.setCategory(category.get().getCategory());
                advertiseDto.setStatus(status.get().getStatus());
                advertiseDtos.add(advertiseDto);
            });
        }
        return ResponseEntity.ok(advertiseDtos);
    }

    @Override
    public ResponseEntity<List<AdvertiseDto>> searchAdvertisement(String searchText) {
        ResponseEntity<UserDto> userResponse =
                restTemplate.getForEntity("http://OLX-LOGIN/user",
                        UserDto.class);
        UserDto userDto = userResponse.getBody();
        List<Advertise> advertiseList = advertiseDao.findByUserName(userDto.getUserName());
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        List<AdvertiseDto> advertiseDtos = new ArrayList<>();
        if (!advertiseList.isEmpty()) {
            advertiseList.forEach(advertise -> {
                Optional<CategoryDto> category = categories.stream()
                        .filter(categoryDto -> categoryDto.getId() == advertise.getCategoryId()).findAny();
                Optional<StatusDto> status = statuses.stream()
                        .filter(statusDto -> statusDto.getId() == advertise.getStatusId()).findAny();
                AdvertiseDto advertiseDto = new AdvertiseDto(advertise);
                advertiseDto.setCategory(category.get().getCategory());
                advertiseDto.setStatus(status.get().getStatus());
                advertiseDtos.add(advertiseDto);
            });
        }
        return ResponseEntity.ok(advertiseDtos);
    }

    @Override
    public ResponseEntity<AdvertiseDto> getAdvertisementById(int postId) {

        if (postId == 0)
            throw new InvalidAdvertisementException("Invalid Advertisement");

        Advertise advertise = advertiseDao.getById(postId);
        if (advertise == null)
            throw new InvalidAdvertisementException("Invalid Advertisement");
        AdvertiseDto advertiseDto = new AdvertiseDto(advertise);
        ResponseEntity<CategoryDto[]> categoryResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/category",
                        CategoryDto[].class);
        Set<CategoryDto> categories = new HashSet<>(Arrays.asList(categoryResponse.getBody()));
        ResponseEntity<StatusDto[]> statusResponse =
                restTemplate.getForEntity("http://OLX-MASTERDATA/advertise/status",
                        StatusDto[].class);
        Set<StatusDto> statuses = new HashSet<>(Arrays.asList(statusResponse.getBody()));
        Optional<CategoryDto> category = categories.stream()
                .filter(categoryDto -> categoryDto.getId() == advertise.getCategoryId()).findAny();
        Optional<StatusDto> status = statuses.stream()
                .filter(statusDto -> statusDto.getId() == advertise.getStatusId()).findAny();
        advertiseDto.setCategory(category.get().getCategory());
        advertiseDto.setStatus(status.get().getStatus());
        return ResponseEntity.ok(advertiseDto);
    }

    @CircuitBreaker(name = "AUTH_TOKEN_VALIDATION", fallbackMethod = "fallBackForIsLoggedInUser")
    public boolean isLoggedInUser(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Boolean> response =
                this.restTemplate.exchange("http://OLX-LOGIN/user/token/validate", HttpMethod.GET, entity, Boolean.class);
        return response.getBody();
    }

    public boolean fallBackForIsLoggedInUser(String authToken, Throwable throwable) throws Throwable {
        System.out.println("Login service failed " + throwable);
        if (throwable != null)
            return false;
        return false;
    }
}

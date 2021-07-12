package com.zensar.olxadvertises.dao;

import com.zensar.olxadvertises.model.Advertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertiseDao extends JpaRepository<Advertise, Integer> {

    List<Advertise> findByUserName(String userName);
}


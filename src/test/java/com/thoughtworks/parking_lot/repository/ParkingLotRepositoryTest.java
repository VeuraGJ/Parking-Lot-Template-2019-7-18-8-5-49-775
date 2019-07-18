package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.entity.ParkingLot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
public class ParkingLotRepositoryTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Before
    public void setUp()  throws Exception{
        List<ParkingLot> parkingLotList = new ArrayList<>();
        for(int i =0;i<17;i++){
            parkingLotList.add(new ParkingLot("parkingLot"+i,i+15,"nanfangruanjianyuan"));
        }
        parkingLotRepository.saveAll(parkingLotList);
    }
    @Test
    public void should_return_15_record_when_page_query_function(){
        Page<ParkingLot> pageParkingLots=parkingLotRepository.findAll(PageRequest.of(0, 15));
        pageParkingLots.stream().collect(Collectors.toList());
        List<ParkingLot> parkingLots = parkingLotRepository.findAll().subList(0,15);
        assertEquals(parkingLots,pageParkingLots);

    }
}
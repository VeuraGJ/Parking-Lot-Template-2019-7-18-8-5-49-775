package com.thoughtworks.parking_lot.controller;

import com.google.gson.Gson;
import com.thoughtworks.parking_lot.entity.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingLotController.class)
public class ParkingLotControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ParkingLotRepository parkingLotRepository;
    @Test
    public void should_add_parkingLot_when_call_post_parkingLot_api() throws Exception {
        Gson gson = new Gson();
        String name = UUID.randomUUID().toString();
        ParkingLot parkingLot = new ParkingLot(name, 10, "where");
        given(parkingLotRepository.save(any(ParkingLot.class))).willReturn(parkingLot);

        mvc.perform(post("/parking-lots").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(parkingLot)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }
    @Test
    public void should_delete_parkingLot_when_call_delete_parkingLot_api() throws Exception {
        Gson gson = new Gson();
        String name = UUID.randomUUID().toString();
        ParkingLot parkingLot = new ParkingLot(name, 10, "where");
        given(parkingLotRepository.save(any(ParkingLot.class))).willReturn(parkingLot);
        ParkingLot saveParkingLot = parkingLotRepository.save(parkingLot);
        long parkingLotId = saveParkingLot.getId();
        mvc.perform(delete("/parking-lots/{parkingLotId}",parkingLotId))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void should_get_parkingLots_when_call_get_parkingLot_api() throws Exception {
        List<ParkingLot> parkingLots = new ArrayList<>();
        parkingLots.add(new ParkingLot("Lala", 10, "where"));
        parkingLots.add(new ParkingLot("Laa", 10, "where"));
        given(parkingLotRepository.findAll()).willReturn(parkingLots);
        mvc.perform(get("/parking-lots"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Lala"));
    }
    @Test
    public void should_get_parkingLots_by_page_when_call_get_parkingLot_api() throws Exception {
        mvc.perform(get("/parking-lots?page=1&pageSize=15"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void should_get_parkingLots_by_id_when_call_get_parkingLot_api() throws Exception {
        ParkingLot parkingLot = new ParkingLot("Lala", 10, "where");
        parkingLot.setId(1);
        given(parkingLotRepository.findById(any(Long.class))).willReturn(java.util.Optional.of(parkingLot));
        mvc.perform(get("/parking-lots/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lala"))
                .andExpect(jsonPath("$.capacity").value("10"))
                .andExpect(jsonPath("$.location").value("where"));
    }
}
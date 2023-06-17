package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.driver.model.SpotType.*;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot=new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;

    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
           SpotType spotType=null;
           if(numberOfWheels<=2){
               spotType=TWO_WHEELER;
           }
           else if(numberOfWheels>2&&numberOfWheels<=4){
               spotType=FOUR_WHEELER;
           }
           else if(numberOfWheels>4){
               spotType=OTHERS;
           }
           ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
           Spot spot=new Spot();
           spot.setParkingLot(parkingLot);
           spot.setSpotType(spotType);
           spot.setPricePerHour(pricePerHour);
          // spotRepository1.save(spot);
          parkingLot.getSpotList().add(spot);
          parkingLotRepository1.save(parkingLot);
          return spot;


    }

    @Override
    public void deleteSpot(int spotId) {
          Spot spot=spotRepository1.findById(spotId).get();
          ParkingLot parkingLot=spot.getParkingLot();
          parkingLot.getSpotList().remove(spot);
          parkingLotRepository1.save(parkingLot);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {

        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        Spot spot=spotRepository1.findById(spotId).get();
        spot.setPricePerHour(pricePerHour);
        spotRepository1.save(spot);
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
          parkingLotRepository1.deleteById(parkingLotId);
    }
}

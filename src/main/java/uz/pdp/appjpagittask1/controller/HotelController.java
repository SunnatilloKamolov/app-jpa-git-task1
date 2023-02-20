package uz.pdp.appjpagittask1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjpagittask1.entity.Hotel;
import uz.pdp.appjpagittask1.repository.HotelRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;
    @GetMapping
    public List<Hotel>getHotels(){
        List<Hotel> allHotelList = hotelRepository.findAll();
        return allHotelList;
    }
    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
         hotelRepository.save(hotel);
         return "Hotel added";
    }@PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel ){
        Optional<Hotel> optionalHotel = hotelRepository.findById(id);
        if (optionalHotel.isPresent()){
        Hotel hotel1 = optionalHotel.get();
        hotel1.setName(hotel.getName());
        hotelRepository.save(hotel1);
        return "Hotel edited";}
        return "Hotel not found";
    }
    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
    hotelRepository.deleteById(id);
    return "Hotel deleted";
    }
}

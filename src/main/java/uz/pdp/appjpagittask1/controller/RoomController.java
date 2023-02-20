package uz.pdp.appjpagittask1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appjpagittask1.entity.Hotel;
import uz.pdp.appjpagittask1.entity.Room;
import uz.pdp.appjpagittask1.payload.RoomDto;
import uz.pdp.appjpagittask1.repository.HotelRepository;
import uz.pdp.appjpagittask1.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;

    @GetMapping()
    public Page<Room> getAllRooms(@RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomsPage =roomRepository.findAll(pageable);
        return roomsPage;
    }

    @GetMapping("/{hotelId}")
    public Page<Room> getRoomsByHotelId(@PathVariable Integer hotelId, @RequestParam int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Room> roomPage = roomRepository.findAllByHotelId(hotelId, pageable);
        return roomPage;
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        boolean exist = roomRepository.existsByNumberAndHotelId(roomDto.getNumber(), roomDto.getHotelId());
        if (exist)
            return "This hotel and room exist";
        Room room = new Room();
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        Optional<Hotel> optionalHotel = hotelRepository.findById(roomDto.getHotelId());
        if (!optionalHotel.isPresent())
            return "Hotel not found";
        room.setHotel(optionalHotel.get());
        roomRepository.save(room);
        return "Room saved";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        Optional<Room> optionalRoom = roomRepository.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setNumber(roomDto.getNumber());
            room.setFloor(roomDto.getFloor());
            room.setSize(roomDto.getSize());
            Optional<Hotel> optional = hotelRepository.findById(roomDto.getHotelId());
            if (!optional.isPresent()) {
                return "Hotel not found";
            }
            room.setHotel(optional.get());
            roomRepository.save(room);
            return "Room edited";
        }return "Room not found";
    }@DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id){
    try {
        roomRepository.deleteById(id);
        return "Deleted";
    }catch (Exception e){
        return "Room not found";
    }
    }
}

package uz.pdp.appjpagittask1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appjpagittask1.entity.Room;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    Page<Room>findAllByHotelId(Integer hotel_id, Pageable pageable);
    boolean existsByNumberAndHotelId(String number, Integer hotel_id);

}

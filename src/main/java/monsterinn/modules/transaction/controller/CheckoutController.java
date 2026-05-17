package monsterinn.modules.transaction.controller;

import monsterinn.modules.monster.model.Monster;
import monsterinn.modules.monster.repository.MonsterRepository;
import monsterinn.modules.room.repository.RoomRepository;
import monsterinn.modules.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CheckoutController {

    @Autowired private MonsterRepository monsterRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private ServiceRepository serviceRepository;

    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        List<Monster> activeGuests = monsterRepository.findByRoomIdIsNotNull();
        model.addAttribute("activeGuests", activeGuests);
        return "modules/transaction/checkout";
    }

    @GetMapping("/api/checkout-info/{guestId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getCheckoutInfo(@PathVariable String guestId) {
        return monsterRepository.findById(guestId).map(monster -> {
            Map<String, Object> info = new HashMap<>();
            info.put("name",       monster.getName());
            info.put("roomId",     monster.getRoomId());
            info.put("stayDays",   monster.getStayDays());
            info.put("roomTotal",  monster.calculateTotalCost() - monster.getExtraCost());
            info.put("extraCost",  monster.getExtraCost());
            info.put("prepaid",    monster.getPrepaidAmount());
            info.put("grandTotal", monster.calculateTotalCost() - monster.getPrepaidAmount());
            info.put("serviceLog", monster.getServiceLog());
            return ResponseEntity.ok(info);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/checkout/confirm/{guestId}")
    @ResponseBody
    public ResponseEntity<String> confirmCheckout(@PathVariable String guestId) {
        try {
            Monster monster = monsterRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Monster tidak ditemukan: " + guestId));

            String roomId = monster.getRoomId();

            // Checkout kamar
            roomRepository.findById(roomId).ifPresent(room -> {
                room.checkOut();
                roomRepository.save(room);
            });

            // Hapus semua service log monster ini
            serviceRepository.findByGuestId(guestId).forEach(sr -> serviceRepository.delete(sr));

            // Hapus monster dari DB
            monsterRepository.delete(monster);

            return ResponseEntity.ok("Checkout berhasil");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

package monsterinn.modules.report.controller;

import monsterinn.modules.monster.model.Monster;
import monsterinn.modules.monster.repository.MonsterRepository;
import monsterinn.modules.service.model.ServiceRequest;
import monsterinn.modules.service.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LaporanController {

    @Autowired private MonsterRepository monsterRepository;
    @Autowired private ServiceRepository serviceRepository;

    @GetMapping("/laporan")
    public String showLaporan(Model model) {
        List<Monster> allMonsters = monsterRepository.findAll();
        List<ServiceRequest> allServices = serviceRepository.findAll();

        // Hitung total pendapatan dari semua monster yang masih aktif
        double revenue = allMonsters.stream()
            .mapToDouble(Monster::calculateTotalCost)
            .sum();

        long totalOrders  = allServices.size();
        long totalMonsters = allMonsters.size();

        // Hitung persentase per elemen
        long fireCount  = allMonsters.stream().filter(m -> "FIRE".equals(m.getElement())).count();
        long waterCount = allMonsters.stream().filter(m -> "WATER".equals(m.getElement())).count();
        long earthCount = allMonsters.stream().filter(m -> "EARTH".equals(m.getElement())).count();
        long total = fireCount + waterCount + earthCount;

        int firePct  = total > 0 ? (int) (fireCount  * 100 / total) : 0;
        int waterPct = total > 0 ? (int) (waterCount * 100 / total) : 0;
        int earthPct = total > 0 ? (int) (earthCount * 100 / total) : 0;

        // History ringkas: semua monster dengan data menginap
        model.addAttribute("revenue",       revenue);
        model.addAttribute("totalOrders",   totalOrders);
        model.addAttribute("totalMonsters", totalMonsters);
        model.addAttribute("firePct",       firePct);
        model.addAttribute("waterPct",      waterPct);
        model.addAttribute("earthPct",      earthPct);
        model.addAttribute("history",       allMonsters); // pakai monster sebagai "transaksi"
        model.addAttribute("allServices",   allServices);

        return "modules/report/laporan";
    }
}

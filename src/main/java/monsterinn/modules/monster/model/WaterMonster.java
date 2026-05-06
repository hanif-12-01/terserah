package monsterinn.modules.monster.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class WaterMonster extends Monster {
    private double filtrationCost; // Biaya tambahan filtrasi air

    // Constructor
    public WaterMonster(String idMonster, String name, double baseCost, double filtrationCost) {
        super(idMonster, name, baseCost);
        this.filtrationCost = filtrationCost;
    }

    // Override calculateTotalCost - Formula Air
    // Total = (baseCost + filtrationCost) * stayDays
    @Override
    public double calculateTotalCost() {
        return (baseCost + filtrationCost) * stayDays;
    }

    @Override
    public String getDetail() {
        return super.getDetail() +
               " | Elemen: WATER" +
               " | Filtration Cost: Rp" + filtrationCost +
               " | Total Cost: Rp" + calculateTotalCost();
    }   
}

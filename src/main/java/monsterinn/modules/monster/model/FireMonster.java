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
public class FireMonster extends Monster {
    private double thermalSurcharge; // Biaya tambahan panas/api

    // Constructor
    public FireMonster(String idMonster, String name, double baseCost, double thermalSurcharge) {
        super(idMonster, name, baseCost);
        this.thermalSurcharge = thermalSurcharge;
    }

    // Override calculateTotalCost - Formula Api
    // Total = (baseCost + thermalSurcharge) * stayDays
    @Override
    public double calculateTotalCost() {
        return (baseCost + thermalSurcharge) * stayDays;
    }

    @Override
    public String getDetail() {
        return super.getDetail() +
               " | Elemen: FIRE" +
               " | Thermal Surcharge: Rp" + thermalSurcharge +
               " | Total Cost: Rp" + calculateTotalCost();
    }
}

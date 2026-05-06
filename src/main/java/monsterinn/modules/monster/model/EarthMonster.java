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
public class EarthMonster extends Monster {
    private double soilNutrientCost; // Biaya tambahan nutrisi tanah

    // Constructor
    public EarthMonster(String idMonster, String name, double baseCost, double soilNutrientCost) {
        super(idMonster, name, baseCost);
        this.soilNutrientCost = soilNutrientCost;
    }

    // Override calculateTotalCost - Formula Tanah
    // Total = (baseCost + soilNutrientCost) * stayDays
    @Override
    public double calculateTotalCost() {
        return (baseCost + soilNutrientCost) * stayDays;
    }

    @Override
    public String getDetail() {
        return super.getDetail() +
               " | Elemen: EARTH" +
               " | Soil Nutrient Cost: Rp" + soilNutrientCost +
               " | Total Cost: Rp" + calculateTotalCost();
    }
}

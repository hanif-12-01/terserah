package monsterinn.modules.monster.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Data;
import jakarta.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Table (name = "monsters")
public abstract class Monster {

    @Id
    protected String idMonster ;
    protected String name;
    protected double baseCost;
    protected double extraCost;
    protected String roomId;
    protected int stayDays;
    protected double prepaidAmount;

    // Constructor
    public Monster(String idMonster, String name, double baseCost) {
        this.idMonster = idMonster;
        this.name = name;
        this.baseCost = baseCost;
        this.extraCost = 0;
        this.stayDays = 1;
        this.prepaidAmount = 0;
    }

    // Method getDetail
    public String getDetail() {
        return "ID: " + idMonster + " | Nama: " + name +
               " Base Cost: Rp" + baseCost;
    }

    // Polymorphic Method - wajib di-override tiap subclass
    public abstract double calculateTotalCost();

    // Validate state sederhana
    public boolean validateState() {
        return idMonster != null && !idMonster.isEmpty()
            && name != null && !name.isEmpty()
            && baseCost > 0;
    }
}
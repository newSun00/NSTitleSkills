package org.nano.nstitleskills.util.item;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;
import org.nano.nstitleskills.data.register.StatRegistry;
import org.nano.nstitleskills.etc.AuraSkillsColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.nano.nstitleskills.util.number.Unit.unitDouble;

public class ItemManger {
    private final StatRegistry registry = StatRegistry.getInstance();

    public List<String> getConditionsLore(List<String> basicLore, String unique){
        List<String> newLore = new ArrayList<>(basicLore);
        List<String> lore = new ArrayList<>();

        lore.add("");

        Map<Skills, Integer> skillsCondition = registry.getConditions(unique).getSkillsCondition();
        boolean hasNonZeroValue1 = skillsCondition.values().stream().anyMatch(value -> value != 0);
        if (!skillsCondition.isEmpty() && hasNonZeroValue1) {
            lore.add("&o<color:#B5EACD>     조건&7( 스킬 )&o      ");
            for (Map.Entry<Skills, Integer> entry : skillsCondition.entrySet()) {
                Skills key = entry.getKey();
                int value = entry.getValue();
                if (value > 0) {
                    String name = AuraSkillsColor.skillsColor(key) + " : &o&7" + value;
                    lore.add(name);
                } else if (value < 0) {
                    String name = AuraSkillsColor.skillsColor(key) + " : &o&7" + value;
                    lore.add(name);
                }
            }
        }


        Map<Stats, Integer> statsCondition = registry.getConditions(unique).getStatsCondition();
        boolean hasNonZeroValue2 = statsCondition.values().stream().anyMatch(value -> value != 0);
        if (!statsCondition.isEmpty() && hasNonZeroValue2) {
            lore.add("&o<color:#B5EACD>     조건&7( 스텟 )&o      ");
            for (Map.Entry<Stats, Integer> entry : statsCondition.entrySet()) {
                Stats key = entry.getKey();
                int value = entry.getValue();
                if (value > 0) {
                    String name = AuraSkillsColor.statsColor(key) + " : &o&7" + value;
                    lore.add(name);
                } else if (value < 0) {
                    String name = AuraSkillsColor.statsColor(key) + " : &o&7" + value;
                    lore.add(name);
                }
            }
        }

        Map<Stats, Integer> additionalStats = registry.getStats(unique).getAdditionalStats();
        boolean hasNonZeroValue3 = additionalStats.values().stream().anyMatch(value -> value != 0);
        if (!additionalStats.isEmpty() && hasNonZeroValue3) {
            lore.add("&o<color:#B5EACD>     추가&7( 스텟 )&o      ");
            for (Map.Entry<Stats, Integer> entry : additionalStats.entrySet()) {
                Stats key = entry.getKey();
                int value = entry.getValue();
                if (value > 0) {
                    String name = AuraSkillsColor.statsColor(key) + " : &a+" + value;
                    lore.add(name);
                } else if (value < 0) {
                    String name = AuraSkillsColor.statsColor(key) + " : &c" + value;
                    lore.add(name);
                }
            }
        }

        double additionalXP = registry.getStats(unique).getAdditionalXP();
        boolean hasNonZeroValue4 = additionalXP != 0.0;
        if (hasNonZeroValue4) {
            if ( additionalXP > 0 ){
                String name =  "&a추가 경험치 : &a+" +unitDouble(additionalXP);
                lore.add(name);
            }else if ( additionalXP < 0 ){
                String name =  "&a추가 경험치 : &c" +unitDouble(additionalXP);
                lore.add(name);
            }
        }



        if ( lore.size() == 1 ) return basicLore;
        newLore.addAll(lore);
        return newLore;
    }
}

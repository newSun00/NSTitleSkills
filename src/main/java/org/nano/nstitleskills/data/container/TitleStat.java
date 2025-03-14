package org.nano.nstitleskills.data.container;

import dev.aurelium.auraskills.api.stat.Stats;

import java.util.HashMap;
import java.util.Map;

public class TitleStat {
    /**
     * @descripiton 추가스텟
     * Map<Stats, Integer>
     */

    private final Map<Stats, Integer> additionalStats;
    private double additionalXP;

    public TitleStat() {
        additionalStats = new HashMap<>();
        for ( Stats stats : Stats.values()) {
            additionalStats.put(stats,0);
        }
        additionalXP = 0;
    }

    public Map<Stats, Integer> getAdditionalStats() {
        return additionalStats;
    }

    public double getAdditionalXP() {
        return additionalXP;
    }

    public void setAdditionalXP(double additionalXP) {
        this.additionalXP = additionalXP;
    }
}

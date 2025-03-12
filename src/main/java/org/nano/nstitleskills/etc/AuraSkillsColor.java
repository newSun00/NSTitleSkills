package org.nano.nstitleskills.etc;

import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.stat.Stats;

public class AuraSkillsColor {
    public static String skillsColor(Skills skills){
        switch (skills){
            case MINING -> {
                return "&7광부&f";
            }
            case AGILITY -> {
                return "&f회피&f";
            }
            case ALCHEMY -> {
                return "&5연금술&f";
            }
            case DEFENSE -> {
                return "&c방어&f";
            }
            case ENCHANTING -> {
                return "&d마법부여&f";
            }
            case ENDURANCE -> {
                return "<color:#E9D298>마법부여&f";
            }
            case FARMING -> {
                return "<color:#EBBC66>농사&f";
            }
            case FISHING -> {
                return "&b낚시&f";
            }
            case FORGING -> {
                return "<color:#9AEB7F>FORGING&f";
            }
            case SORCERY -> {
                return "<color:#9AEB7F>SORCERY&f";
            }
            case HEALING -> {
                return "&a치유";
            }
            case ARCHERY -> {
                return "<color:#EB8C44>양궁&f";
            }
            case EXCAVATION -> {
                return "&6발굴&f";
            }
            case FIGHTING -> {
                return "<color:#EA544B>전투&f";
            }
            case FORAGING -> {
                return "<color:#9AEB7F>채집&f";
            }
        }
        return "&4알수 없음";
    }
    public static String statsColor(Stats stats){
        switch (stats){
            case CRIT_CHANCE -> {
                return "&c크리티컬 확률&f";
            }
            case CRIT_DAMAGE -> {
                return "&c크리티컬 데미지&f";
            }
            case HEALTH -> {
                return "&4체력&f";
            }
            case LUCK -> {
                return "&a행운&f";
            }
            case REGENERATION -> {
                return "&d재생력&f";
            }
            case SPEED -> {
                return "&b속도&f";
            }
            case STRENGTH -> {
                return "<color:#EA852C>힘&f";
            }
            case TOUGHNESS -> {
                return "&5강인함&f";
            }
            case WISDOM -> {
                return "&6지혜&f";
            }
        }
        return "&4알수 없음";
    }
}

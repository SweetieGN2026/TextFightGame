package com.itheima.domain;

import java.util.ArrayList;

public class HeroCharacter extends Character {
    public ArrayList <String> skillList ;

    public HeroCharacter() {
        super();
        skillList = new ArrayList();
    }

    public HeroCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
     skillList = new ArrayList();
    }
    public String showSkills() {
        StringBuilder skills = new StringBuilder();
        for (int i = 0; i < skillList.size(); i++) {
            skills.append(skillList.get(i));
            if (i != skillList.size() - 1) {
                skills.append(", ");
            }
        }
        return skills.toString();
    }
}

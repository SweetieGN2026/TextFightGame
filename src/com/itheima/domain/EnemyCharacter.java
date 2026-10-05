package com.itheima.domain;

import java.util.ArrayList;

public class EnemyCharacter extends Character {
   public String skill;
    public boolean defending;

    public EnemyCharacter(String name, int HP, int attack, int defense) {
        super(name, HP, attack, defense);
    }

    public EnemyCharacter(String name, int HP, int attack, int defense, String skill) {
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    @Override
    public void takeDamage(int damage) {
        if (defending) {
          damage = damage /2>1?damage/2  :1;
        }
       super.takeDamage(damage);
    }
}

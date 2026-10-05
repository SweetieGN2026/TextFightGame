package com.itheima.ui;

import com.itheima.domain.EnemyCharacter;
import com.itheima.domain.HeroCharacter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FightGame {
    public void gamestart(String username){
            System.out.println("┌──────────────────────────────────────────────────────┐");
            System.out.println("    🎮 " + username + "欢迎来到文字格斗游戏 🎮    ");
            System.out.println("└──────────────────────────────────────────────────────┘");
        HeroCharacter hero = createPlayerCharacter(username);
        System.out.println("====角色创建完成====");
        System.out.println(hero.show());
        System.out.println("✨ 拥有的技能: " +hero.showSkills());
        ArrayList<EnemyCharacter> enemyList = new ArrayList<>();
        enemyList.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemyList.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemyList.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemyList.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));
        int count=1;//记录现在是跟第几个敌人战斗
        int wins=0;
        while(hero.isAlive()){
            //怪物肯定是要越来越难 每多打一场，所有怪物模板属性永久提升
            if(count>1){
                for (int i = 0; i < enemyList.size(); i++) {
                    EnemyCharacter c = enemyList.get(i);
                    c.maxHP=c.maxHP+10;
                    c.HP=c.maxHP;
                    c.attack=c.attack+3;
                    c.defense=c.defense+2;
                    c.defending =false;
                }
            }
            Random r = new Random();
            int index = r.nextInt(enemyList.size());
            EnemyCharacter enemy = enemyList.get(index);
            System.out.println(enemy.show());
            System.out.println("------------------------------------------------------------------------");
            System.out.println("⚔️  第" + count + "场战斗开始！对手: " + enemy.name);
            int round = 1;
            while (hero.isAlive()) {
                System.out.println("⚔️  第" +round+ "回合开始！对手: ");
                System.out.println(getHealthBar(hero.name, hero.HP, hero.maxHP));
                System.out.println(getHealthBar(enemy.name,enemy.HP,enemy.maxHP));
                playerTurn(hero,enemy);
                if(!enemy.isAlive()){
                    System.out.println("🎉 你击败了 " + enemy.name + "! ");
                    wins++;
                    break;
                }
                enemyTurn(enemy, hero);
                if(!hero.isAlive()){
                    System.out.println("💀 你被 " + enemy.name + " 击败了....");
                    break;
                }
                round++;
            }
            count++;
            if(hero.isAlive()){
                int heal1 = r.nextInt(30, 51);
                hero.heal(heal1);
                System.out.println("💚 战斗结束！你恢复了 " + heal1 + " 点生命值");
                System.out.println("🏆 当前胜场: " + wins);
                System.out.println("-----------------------");
                if(hero.isAlive()&&wins>0&&wins%3==0){
                    System.out.println("获得属性提升");
                    hero.maxHP+=30;
                    hero.attack+=6;
                    hero.defense+=3;
                    System.out.println("血量提升30点 攻击提升6点 防御提升3点");
                    System.out.println("你的属性是："+hero.show());
                    if(hero.isAlive()){
                        System.out.println("要继续战斗吗");
                        Scanner sc = new Scanner(System.in);
                        String choice = sc.next();
                        if("y".equalsIgnoreCase(choice)){
                            count++;
                            continue;
                        }else if("n".equalsIgnoreCase(choice)){
                            break;
                        }else{
                            System.out.println("没有这个选项，默认游戏继续");
                            continue;
                        }

                    }
                }
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("游戏结束！");
        System.out.println("总胜场: " + wins);
        System.out.println("感谢游玩文字版格斗游戏");
        System.exit(0);

    }

    private void enemyTurn(EnemyCharacter enemy, HeroCharacter hero) {
        System.out.println("======" + enemy.name + "的回合 =====");
        String action = "普通攻击";
        Random r = new Random();
        int num = r.nextInt(10);
       if(num>4)
           action=enemy.skill;
       switch (action) {
           case "普通攻击":
               System.out.println("敌人采取了普通攻击");
               int damage1 = calculateDamage(enemy.attack, hero.defense);
               System.out.println("⚔️ " + enemy.name + "对你使用了普通攻击，造成 " + damage1 + " 点伤害!");
               hero.takeDamage(damage1);
               break;
           case "猛击":
               System.out.println("当前的战士采取了猛击");
               int damage2 = calculateDamage((int)(enemy.attack * 1.5), hero.defense);
               System.out.println("💥 " + enemy.name + "对你使用了猛击，造成 " + damage2 + " 点伤害! ");
               hero.takeDamage(damage2);
               break;
           case "快速攻击":
               System.out.println("当前的刺客采取了快速攻击");
               int damage3 = 0;
               for (int i = 0; i < 2; i++) {
                   int temp = calculateDamage(enemy.attack / 2, hero.defense);
                   damage3+= temp;
               }
               System.out.println("🌀 " + enemy.name + " 对你使用了快速攻击，造成 " + damage3 + " 点伤害!");
               hero.takeDamage(damage3);
               break;
           case "防御姿态":
               System.out.println("当前的坦克采取了防御姿态  buff");
               enemy.defending = true;
               System.out.println("🛡️ " + enemy.name + " 摆出了防御姿态! ");
               break;
           case "火球术":
               System.out.println("当前的法师采取了火球术");
               int damage4 = calculateDamage((int)(enemy.attack * 1.8), hero.defense);
               System.out.println("🔥 " + enemy.name + " 对你使用了火球术，造成 " + damage4 + " 点伤害!");
               hero.takeDamage(damage4);
               break;
       }

    }

    public void playerTurn(HeroCharacter hero,EnemyCharacter enemy){
        System.out.println("===== 你的回合 =====");
        System.out.println("1. 普通攻击");
        System.out.println("2. 强力一击");
        System.out.println("3. 生命汲取");
        System.out.println("请选择");
        Scanner sc = new Scanner(System.in);
        String choice = sc.next();
        switch (choice){
            default:
                System.out.println("没有这个操作，默认使用普通攻击");
            case "1":
                System.out.println("1. 普通攻击");
                int damage1 = calculateDamage(hero.attack, enemy.defense);
                System.out.println("你对 " + enemy.name + " 使用了普通攻击，造成 " + damage1 + " 点伤害！");
                enemy.takeDamage(damage1);
                break;
            case "2":
                System.out.println("2. 强力一击");
                if(hero.HP<=0){
                    System.out.println("血量不够");
                }
                else
                    System.out.println("强力一击");
                hero.takeDamage(10);
                int damage2 = calculateDamage((int) (hero.attack * 1.8), enemy.defense);
                System.out.println("💥 消耗10HP，你对 " + enemy.name + " 使用了强力一击，造成 " + damage2 + " 点伤害！");
                enemy.takeDamage(damage2);
                break;
            case "3":
                System.out.println("3. 生命汲取");
                if(hero.HP > 10){
                    hero.takeDamage(10);
                    Random r = new Random();
                    int healHP = r.nextInt(11) + 20;
                    hero.heal(healHP);
                    System.out.println(" ❤️ 消耗10HP，你使用了生命汲取，恢复了" + healHP + "点生命值！");
                }else{
                    System.out.println("体力不足！恢复生命失败~");
                }
                break;
        }


    }
    public int calculateDamage(int attack, int defense){
        int damage=attack-defense;
        if(damage<1){
            damage=1;
        }
        return damage;
    }
    public String getHealthBar(String username, int Hp, int maxHp){
        int barlength=20;
        int filled=(int)( (Hp*1.0/maxHp) * barlength );
        if(filled < 0) filled = 0;
        if(filled > barlength) filled = barlength;
        StringBuilder sb=new StringBuilder();
        sb.append(username).append(": [") ;
        for (int i = 0; i < barlength; i++) {
            if(i < filled){
                sb.append("■");
            }else{
                sb.append(" ");
            }
        }
        sb.append("]").append(Hp).append("/").append(maxHp).append(" HP");
        return sb.toString();
    }

    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("创建您的角色:");
        System.out.println("您的角色名为: " + username);
        int points = 50;
        System.out.println("请分配属性点（共50点）：");
        System.out.println("1. 生命值（每点 +10HP）");
        System.out.println("2. 攻击力（每点 +2ATK）");
        System.out.println("3. 防御力（每点 +1DEF）");

        Scanner sc = new Scanner(System.in);
        String[] attributes = {"生命值", "攻击力", "防御力"};

        int[] attrPoint = new int[3];

        for (int i = 0; i < attributes.length; i++) {
            while (true) {
                System.out.println("请分配" + attributes[i] + "，剩余点数：" + points + "：");
                int num = sc.nextInt();
                if (num >= 0 && num <= points) {
                    attrPoint[i] = num;
                    points -= num;
                    break;
                } else {
                    System.out.println("输入非法！不能是负数，也不能超过剩余点数");
                }
            }
        }
        if(points > 0){
            System.out.println("还有剩余属性点：" + points + "，自动加到生命值");
            attrPoint[0] += points;
            points = 0;
        }
        int hp = attrPoint[0] * 10;
        int atk = attrPoint[1] * 2;
        int def = attrPoint[2] * 1;
        HeroCharacter hero = new HeroCharacter(username, hp, atk, def);
        hero.skillList.add("普通攻击");
        hero.skillList.add("强力一击");
        hero.skillList.add("生命汲取");
        return hero;
    }

}

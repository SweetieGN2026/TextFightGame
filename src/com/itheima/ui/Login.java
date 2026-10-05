package com.itheima.ui;
import com.itheima.domain.User;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Login {
        public  void start(){
            System.out.println("游戏界面打开了");
            ArrayList<User> list=new ArrayList<User>();
            while (true) {
                System.out.println("┌──────────────────────────────────────────────────────┐");
                System.out.println("🎮 欢迎来到文字格斗游戏 🎮");
                System.out.println("└──────────────────────────────────────────────────────┘");
                System.out.println("请选择操作：1登录 2注册 3退出");
                Scanner sc = new Scanner(System.in);
                String choose = sc.next();
                switch (choose){
                case "1"-> login(list);
                case "2"->register(list);
                case "3"-> {
                    System.out.println("用户选择离开");
                    System.exit(0);
                }
                    default -> System.out.println("输入有误");
            }
            }

        }
        public  void login( ArrayList<User> list){
            System.out.println("用户选择登录");
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入用户名");
            String username = sc.next();
            if(!comparusername(list,username)){
                System.out.println("没有注册 请你去注册");
              return;
            }
            int index = findIndex(list, username);
            User u = list.get(index);
            if(!u.isStatus()){
                System.out.println("账号："+username+"你的账号异常 请找黑马客服 电话：123456");
                return;
            }

            String rightpssword = u.getPassword();
            for (int i = 0; i < 3; i++) {
                System.out.println("请输入密码");
                String password = sc.next();

                while (true) {
                    String code2 = getCode();
                    System.out.println("验证码："+code2);
                    System.out.println("请输入验证码");
                    String code = sc.next();

                    if(code.equalsIgnoreCase(code2)){
                        System.out.println("成功");
                        break;
                    }
                    else{
                        System.out.println("错误的验证码");
                        continue;
                    }
                }
                if(password.equals(rightpssword)){
                    System.out.println("登录成功");
                    FightGame fg = new FightGame();
                    fg.gamestart(username);
                    break;
                }
                else{
                    System.out.println("密码错误");
                    if(i == 2){
                        u.setStatus(false);
                        System.out.println("当前账户" + username + "已锁定，请联系黑马客服 12345");
                        return;
                    }else{
                        System.out.println("密码错误，还剩下" + (2 - i) + "次机会");
                    }

                }
            }

        }
        public int findIndex(ArrayList<User> list,String username){
            for(int i=0;i<list.size();i++){
                User u=list.get(i);
                if(u.getUsername().equals(username)){
                    return i;
                }
            }
            return -1;
        }
        public  void register(ArrayList<User> list){
            System.out.println("用户选择注册");
            Scanner sc = new Scanner(System.in);
            User user=new User();
            //第一个while的账号 第二个是密码
            while (true) {
                System.out.println("请输入用户名");
                String username = sc.next();
                if(!checklen(3,16,username)){
                    System.out.println("错误 请输入3--16位");
                    continue;
                }
                if( !checkUsername(username)){
                    System.out.println("用户名必须至少包含 1 个字母；数字可有可无；不能有任何其他符号");
                    continue;
                }
                if(comparusername(list,username)){
                    System.out.println("账号不唯一 请重新输入");
                    continue;
                }
                user.setUsername(username);
                break;
            }
            while (true) {
                System.out.println("请输入密码");
                String password1= sc.next();
                System.out.println("请在输入密码");
                String password2= sc.next();
                if(!checklen(3,8,password1) || !checklen(3,8,password2)){
                    System.out.println("必须是3--8之间");
                    continue;
                }

                if(!checkPassword(password1) ){
                    System.out.println("密码只能是数字跟字母的组合，不能有其字母");
                    continue;
                }
                if(!password1.equals(password2)){
                    System.out.println("两次密码不一样");
                    continue;
                }
                user.setPassword(password1);
                break;
            }
            list.add(user);
            System.out.println("用户："+user.getUsername() +"注册成功");
        }
        public  int[] getCount(String userInfo){
            int charCount = 0;
            int numCount = 0;
            int otherCount = 0;

            for (int i = 0; i < userInfo.length(); i++) {
                char c = userInfo.charAt(i);
                if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'){
                    charCount++;
                }else if(c >= '0' && c <= '9'){
                    numCount++;
                }else{
                    otherCount++;
                }
            }
            return new int[]{charCount,numCount,otherCount};
        }
        public  boolean comparusername(ArrayList<User> list,String username){
            for(int i=0;i<list.size();i++){
            User u=list.get(i);
            if(u.getUsername().equals(username)){
                return true;
            }
            }
            return false;
        }
        public boolean  checklen(int minn,int maxn,String username){
            if(username.length()>=minn&&username.length()<=maxn){
                return true;
            }
            return false;
        }
    public boolean checkUsername(String username){
        int[] arr= getCount(username);
        return arr[0] > 0 &&  arr[1] >= 0 && arr[2] == 0;
    }
    public boolean checkPassword(String password){
        int[] arr= getCount(password);
        return arr[0] > 0 && arr[1] > 0 && arr[2] == 0;
    }
    //最近的这个是取得验证码
    public static String getCode(){
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            list.add((char)('a'+i));
            list.add((char)('A'+i));
        }
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            int index = r.nextInt(list.size());
            Character c = list.get(index);
            sb.append(c);
        }
        sb.append(r.nextInt(10));
        //先变字符串然后转成数组
        char[] arr = sb.toString().toCharArray();
        int i=r.nextInt(arr.length);
        //交换位置
        char ch = arr[i];
        arr[i] = arr[arr.length-1];
        arr[arr.length-1] = ch;
        String str = new String(arr);

        return str;
    }
}

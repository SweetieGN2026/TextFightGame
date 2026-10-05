import com.itheima.ui.FightGame;
import com.itheima.ui.Login;

public class App {
    public static void main(String[] args) {
      /*  Login login = new Login();
        login.start();*/
        FightGame fg = new FightGame();
        fg.gamestart("zhangsan");
    }
}

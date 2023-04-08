package ap.backend;

import jakarta.validation.constraints.AssertTrue;
import org.junit.*;
import TgBotClass.TgBotMethods;

public class BotTests {
    // так как БД нет и вообще ничего нет, то пока кондовый способ проверки
    TgBotMethods tgBotMethods;
    @Before
    public void setBot(){
        tgBotMethods = new TgBotMethods();
    }
    @Test
    public void ListExistsResponseTest(){
        tgBotMethods.setHasList(true);
        Assert.assertEquals("Список",tgBotMethods.getResponse("/list"));
    }
    @Test
    public void ListNotExistsResponseTest(){
        tgBotMethods.setHasList(false);
        Assert.assertEquals("Нет списка",tgBotMethods.getResponse("/list"));
    }
}
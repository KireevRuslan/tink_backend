package TgBotClass;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import java.util.List;

public class TgBotMethods {
    private TelegramBot bot;
    private boolean hasList = true;


    public void setUpdateListener(){
        this.bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                updates.forEach(update ->{
                            if (update.message()==null){
                                return;
                            }
                            String msg = update.message().text();
                            System.out.println(msg);
                            doAction(msg, update);
                        }
                );



                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }

        });
    }
    public void setBot(TelegramBot bot){
        this.bot = bot;
    }
    private void doAction(String msg, Update update){
        bot.execute(new SendMessage(update.message().chat().id(), getResponse(msg)));

    }
    public String getResponse(String  msg){
        String helpMsg = "/start - запуск бота\n" +
                "/help - вывести окно с командами\n" +
                "/track - начать отслеживание ссылки\n" +
                "/untrack - прекратить отслеживание ссылки\n" +
                "/list - показать список отслеживаемых ссылок";
        switch (msg){
            case "/start": return "Добрый день, для того, чтобы ознакомиться с командами воспользуйтесь коммандой /help";
            case "/help":   return helpMsg;
            case "/track": return  "Отслеживаемая сслыка добавлена";
            case "/untrack": return "Ссылка больше не остлеживается";
            case "/list": return  (hasList)?"Список":"пусто";
            default: return  "/help - для вызова списка команд";
        }
    }
    public void setHasList(boolean inHasList){
        this.hasList = inHasList;
    }
}
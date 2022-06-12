package DiscordBot;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class mainBot{
    public static void main(String[] args) throws LoginException {
        JDABuilder bot = JDABuilder.createDefault("TOKEN");
        bot.setActivity(Activity.watching("-log per le info"));
        bot.setStatus(OnlineStatus.ONLINE);
        bot.addEventListeners(new BotCommands());
        bot.build();
    }
}

 
package DiscordBot;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import java.time.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommands extends ListenerAdapter {

    private static String prefix = "-";
    private static HashMap<User, LocalDateTime> message_logs = new HashMap<>(); 
    private static logsActions logsActions = new logsActions();

    static private void retrieve_log(MessageReceivedEvent event) throws IOException{
        message_logs.put(event.getMessage().getAuthor(), LocalDateTime.now());
        logsActions.writeLogs(message_logs);
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(prefix + "dio")){
            event.getMessage().reply("cane!").queue();

            try {
                retrieve_log(event);
            } catch (IOException e) {
            
                e.printStackTrace();
            }
        }

        if(args[0].equalsIgnoreCase(prefix + "log")){
            EmbedBuilder embed = new EmbedBuilder();
            embed.setAuthor("Vic");
            embed.setColor(Color.GREEN);
            embed.setTitle("Log del bot");
            embed.setDescription("Bot creato da vic per scazzo");
            embed.addField("Comandi attuali:", "=> -ennio \n=> -browser <quello che desideri cercare>\n=> -games <gioco da cercare> ", false);
            embed.setFooter("By Vic", event.getGuild().getOwner().getUser().getAvatarUrl());
            
            event.getMessage().replyEmbeds(embed.build()).queue();
            embed.clear();

            try {
                retrieve_log(event);
            } catch (IOException e) {
                
                e.printStackTrace();
            } 
        }
        
        if(args[0].equalsIgnoreCase(prefix + "browser")){
            miniBrowser browser = new miniBrowser();
            EmbedBuilder embed = new EmbedBuilder();

            embed.setAuthor("Vic");
            embed.setColor(Color.GREEN);
            embed.setTitle("Mini browser");
            embed.setDescription("Ecco i primi siti: ");
      
            for(String link : browser.getContent(args)){
                embed.addField("\n", link, false);
            }
      
            embed.setFooter("By Vic", event.getGuild().getOwner().getUser().getAvatarUrl());
            event.getMessage().replyEmbeds(embed.build()).queue();
            embed.clear();
            
  
            try {
                retrieve_log(event);
            } catch (IOException e) {
                
                e.printStackTrace();
            } 
        }

        if(args[0].equalsIgnoreCase(prefix + "games")){
            gamesBrowser obj = new gamesBrowser(args);
            EmbedBuilder embed = new EmbedBuilder();

            embed.setAuthor("Vic");
            embed.setColor(Color.GREEN);
            embed.setTitle("Game Browser: SteamUnlocked");
            embed.setDescription("Giochi crackati");

            for(String el : obj.getContent()){
                embed.addField("\n", el, false);
            }

            embed.setFooter("By Vic", event.getGuild().getOwner().getUser().getAvatarUrl());
            event.getMessage().replyEmbeds(embed.build()).queue();
            embed.clear();

            try{
                retrieve_log(event);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        

        if(args[0].equalsIgnoreCase(prefix + "all") && args[1].equalsIgnoreCase("messages")  && args[2].equalsIgnoreCase("logs")){
            try {
                event.getMessage().reply(logsActions.inputLogs()).queue();
            } catch (FileNotFoundException e) {
                
                e.printStackTrace();
            } catch(NoSuchElementException e){
                e.printStackTrace();
            }
        }
    }
}

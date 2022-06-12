package DiscordBot;
import java.util.*;

import net.dv8tion.jda.api.entities.User;

import java.io.*;
import java.time.*;

public class logsActions{
    private static File logs = new File("logs.txt");

    public void writeLogs(HashMap<User, LocalDateTime> log) throws IOException{
        FileWriter file = new FileWriter("logs.txt", true);
        
        for(User i : log.keySet()) {
            file.write(i.toString() + " " + log.get(i).toString() + "\n\n");
        }

        file.close();
    }

    public String inputLogs() throws FileNotFoundException{
        Scanner fileInput = new Scanner(logs);
        StringBuffer string = new StringBuffer();

        while(fileInput.hasNextLine()) {
			string.append(fileInput.nextLine());
		}
        
        fileInput.close();

        return string.toString();
    }
}
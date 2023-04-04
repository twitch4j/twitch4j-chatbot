package me.cocoblue.twitchchatbot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.cocoblue.twitchchatbot.dto.BotDTO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class BotRunner implements CommandLineRunner {
    @Override
    public void run(String[] args) {
        BotDTO botDTO = new BotDTO();
        botDTO.registerFeatures();
        log.info("Bot registerFeatures Finished.");
        botDTO.start();
    }
}
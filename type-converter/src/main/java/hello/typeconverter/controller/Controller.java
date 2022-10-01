package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Controller {

    @GetMapping("/converter")
    public String converter(@RequestParam Integer data) {
        log.info("data = {}", data);
        return "ok";
    }

    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort data) {
        log.info("IP = {}", data.getIp());
        log.info("Port = {}", data.getPort());
        return "ok";
    }
}

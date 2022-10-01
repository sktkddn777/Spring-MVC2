package hello.typeconverter.type;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

// 127.0.0.1:8080 -> ip랑 port 랑 바로 나눌 수 있게
@Getter
@EqualsAndHashCode // 참조값이 달라도 안에 값이 같으면 같은거로 인식하도록 해줌 (테스트용)
@AllArgsConstructor
public class IpPort {

    private String ip;
    private int port;
}

package Interface;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 텍스트를 파싱할 때 사용하는 인터페이스
// 사용: 인터페이스, 컬렉션 프레임워크
public interface ITextParser {
    Map<String, String> hallyu = new HashMap<String, String>(); // 파일 파싱한 결과 저장 변수

    void parse(FileReader reader, char delimiter) throws IOException;
    void save(String key, String folder);
    String[] getKeys();
    String[] getKeys(boolean print);
    String getDetail(String key);

    // 입력된 값이 개행문자인지 아닌지 반환 
    static boolean chkNewLine(int ch) {
        return ch == 10 || ch == 13;
    }
}
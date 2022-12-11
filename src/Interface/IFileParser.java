package Interface;
import java.io.FileNotFoundException;
import java.io.IOException;

// 텍스트를 불러올 때 사용하는 인터페이스
// 사용: 인터페이스
public interface IFileParser {
    void parse(String path, char delimiter) throws FileNotFoundException, IOException;
}
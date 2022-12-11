import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Interface.IFileParser;

// 다형성(인터페이스)를 사용한 텍스트를 파싱하는 클래스를 상속하여 불러오는 기능을 더한 클래스
// 사용: 다형성(상속 / 인터페이스), 클래스 상속
public class FileParser extends TextParser implements IFileParser{
    @Override
    public void parse(String path, char delimiter) throws FileNotFoundException, IOException {
        FileReader reader = new FileReader(path);
        super.parse(reader, delimiter);
    }
}

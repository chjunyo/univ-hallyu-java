import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import Enum.EChar;
import Interface.ITextParser;

// 다형성(인터페이스)를 사용한 텍스트를 불러오고 파싱하는 클래스
// 사용: 다형성(인터페이스), 참조 타입(배열, 열거타입), 파일 입/출력 사용, 예외 처리, 컬렉션 프레임워크
public class TextParser implements ITextParser {
    @Override
    // hallyu Map에서 키에 맞는 값을 리턴하는 함수
    // 사용: 컬렉션 프레임워크
    public String getDetail(String key) {
        return hallyu.get(key);
    }
    
    @Override
    // hallyu Map의 키들을 String 배열의 형태로 리턴하는 함수
    // 사용: 컬렉션 프레임워크, 참조 타입(배열)
    public String[] getKeys() {
        return hallyu.keySet().toArray(new String[hallyu.size()]);
    }
    
    @Override
    // print가 true일시, 키들을 가져오고 그 내용들을 번호와 함께 출력하는 함수
    // 사용: 참조 타입(배열)
    public String[] getKeys(boolean print) {
        String[] keys = getKeys();
        if (print) {
            for (int i = 0; i < keys.length; i++) {
                System.out.println(String.format("%2d", i) + ". " + keys[i]);
            }
        }
        return keys;
    }

    @Override
    // 입력된 폴더에 해당 키 이름으로 키값에 해당하는 값이 있는 텍스트 파일을 생성하는 함수
    // 사용: 예외처리, 파일 출력
    public void save(String key, String folder) {
        String detail = getDetail(key);
        File file = Path.of(folder, key + ".txt").toFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(detail);
            System.out.println("저장되었습니다.");
        } catch (IOException e) { // 예외 처리
            System.out.println("파일을 쓸 수 없습니다.");
        }
    }
    
    @Override
    // 입력된 reader를 읽고 delimiter를 기준으로 제목과 내용을 나누는 함수
    // File Content Example.
    // # TITLE
    // DETAIL...
    // 사용: 파일 입력, 참조 타입(열거타입)
    public void parse(FileReader reader, char delimiter) throws IOException {
        int ch;
        EChar type = EChar.TITLE;
        String title = "";
        String detail = "";
        while ((ch = reader.read()) != -1) {
            if (ch == delimiter) { // delimiter일시
                if (title != "") hallyu.put(title, detail); // title이 비어있지 않으면 추가
                //새 항목이므로 변수 초기화 및 CharType.TITLE 설정
                type = EChar.TITLE;
                title = "";
                detail = "";
            } else if (type == EChar.TITLE) {
                if (ITextParser.chkNewLine(ch)) { // 제목에서 개행문자가 감지되면 내용(EChar.DETAIL)으로 타입 변경
                    type = EChar.DETAIL;
                } else { // 그렇지 않으면 title에 해당 문자 추가
                    title = title.concat(Character.toString(ch));
                }
            } else if (type == EChar.DETAIL && (detail != "" || !ITextParser.chkNewLine(ch))) {
                // 타입이 내용일시, detail이 비어있지 않거나 개행문자가 아닐 경우(title에서 넘어올 때, 남아있는 개행 문자 무시) detail에 해당 문자 추가
                detail = detail.concat(Character.toString(ch));
            }
        }
    }
}

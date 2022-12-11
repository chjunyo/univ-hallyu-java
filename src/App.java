import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    // 특정한 형식으로 저장된 한류에 관련된 정보(hallyu.txt)가 저장된 txt파일의 경로를 입력하면 해당 텍스트 파일을 읽고 파싱한다.
    // 이후, 각 제목들에 해당하는 내용들을 따로 볼 수 있으며 그 부분을 따로 경로를 지정하여 텍스트파일에 저장할 수 있다.
    // 사용: 예외 처리, 참조 타입(배열)
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in, "EUC-KR")) {
            System.out.print("파일 경로를 입력해주세요: ");
            String path = scanner.nextLine();
            FileParser fp = new FileParser();
            try {
                fp.parse(path, '#'); // 파일을 불러오고 #을 기준으로 파싱
                boolean exit = false; // 반복문을 빠져나가기 위한 변수
                String[] keys = fp.getKeys(); // 제목 키값 저장(참조타입(배열))
                while (!exit) { // 사용자가 4번을 입력할 떄까지 반복
                    System.out.println("--------------------------------------------------------");
                    System.out.println("1. 파싱한 제목들 출력");
                    System.out.println("2. 제목 번호를 사용하여 내용 출력");
                    System.out.println("3. 제목 번호를 사용하여 내용 저장");
                    System.out.println("4. 종료");
                    System.out.print("원하는 작업에 해당하는 번호를 입력해주세요: ");
                    int type = scanner.nextInt();
                    switch (type) {
                        case 1:
                            // 파싱한 제목들을 출력하고 값들을 keys에 달라질 수 있기 때문에 재저장
                            keys = fp.getKeys(true);
                            break;
                        case 2:
                            // 제목 번호를 입력받고 그 번호에 해당하는 키 값을 가져오고 그에 해당하는 내용을 출력
                            System.out.print("제목 번호를 입력해주세요: ");
                            int pos1 = scanner.nextInt();
                            try {
                                System.out.println(fp.getDetail(keys[pos1]));
                            } catch (ArrayIndexOutOfBoundsException e) { // keys 배열의 인덱스를 넘어가는 값 예외 처리
                                System.out.println("올바르지 않은 제목 번호입니다.");
                            }
                            break;
                        case 3:
                        try {
                                // 제목 번호를 입력받고 그 번호에 해당하는 키 값을 가져오고 그에 해당하는 내용을 입력받은 폴더에 해당 키값의 이름으로 저장
                                System.out.print("제목 번호를 입력해주세요: ");
                                String key = keys[scanner.nextInt()];
                                scanner.nextLine(); // nextInt() 다음 엔터를 무시하기 위해
                                System.out.print("저장 폴더 경로를 입력해주세요: ");
                                String folder = scanner.nextLine();
                                fp.save(key, folder);
                            } catch (ArrayIndexOutOfBoundsException e) { // keys 배열의 인덱스를 넘어가는 값 예외 처리
                                System.out.println("올바르지 않은 제목 번호입니다.");
                            }
                            break;
                        case 4:
                            exit = true; // 반복문을 빠져나가기 위해 true로 설정
                            break;
                    
                        default:
                            System.out.println("올바르지 않은 값입니다."); // 입력값이 1,2,3,4가 아닐 경우 해당하는 작업이 없음
                            break;
                    }
                    System.out.println("--------------------------------------------------------");
                }
            } catch (FileNotFoundException e) { // 파일 미존재 예외 처리
                System.out.println("파일이 존재하지 않습니다.");
            } catch (IOException e) { // IO예외 처리
                System.out.println("올바른 파일이 아닙니다.");
            }
        }
    }
}

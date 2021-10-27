package database;

public class Main {

	public static void main(String[] args) {
		
		DBConnection connection = new DBConnection();
		
		// Game이 종료된 후, 사용자의 이름(name)과 점수(score)변수에 대한 값을 MySQL의 DB로 전달하여 저장한다.
		// MYSQL → Database : ranking, Table : rank (name(20),score(20))
		// 레코드 삽입 및 정렬, 추출 방법에 대해 알아보기.
		// DB 사용 (use `DB name`) → 테이블 조회 (DESC tablename;) → 데이터 조회(select * from tablename;)

	}
}

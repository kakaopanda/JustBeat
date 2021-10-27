package database;

import java.sql.*;

// DB 생성 : create database `Ranking` CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;
// Table 생성 : create table ranking(Name varchar(20), Score varchar(20));
// Table 정렬 : select * from ranking order by Score DESC;
// Table 조회 : select * from ranking;

public class DBConnection {

	String SQL = "select * from ranking;";
	private Connection con;
	private Statement st; // 질의용 Statement 생성
	private ResultSet rs;
	int count = 0;
	String ranking[][] = new String[3][3];
	
	String name = "test4";
	int score = 500;
	
	public DBConnection() {
		
		try {
			//JDBC Driver Load.
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ranking","root","kakaopanda");
			st = con.createStatement();
			rs = st.executeQuery(SQL);
			System.out.println("데이터 베이스 연결 성공.");	
			// st.executeUpdate("insert into ranking values ('"+name+"', '"+score+"');");
			// rs = st.executeQuery("select * from ranking order by Score DESC;");
			
			// Operation not allowed after ResultSet closed Error
			// Loop 내부에서 rs가 사용한 statement를 사용해서 다시 execute를 실행 할 수 없다.
			// 따라서, Loop 안에서 새로운 statement를 생성하여 새로 execute 해줘야 한다.
			
			while(rs.next()) // 1위 ~ 3위의 데이터만 추출.
			{
				if(count<3)
				{
					st = con.createStatement();
					String name = rs.getString("Name");
					String score = rs.getString("Score");
				
					System.out.println("name : "+name+"| score : "+score);
					st.close();
					count++;
				}
			}
			
		}catch(Exception e) {
			System.out.println("데이터 베이스 연결 오류 : "+ e.getMessage());
		}
	}
	
	public boolean isAdmin(String adminID, String adminPassword) {
		try {
			String SQL = "SELECT * FROM ranking WHERE adminID = '" + adminID + "'and adminPassword = '" + adminPassword + "'";
			rs = st.executeQuery(SQL);
			if(rs.next()) 
			{
				return true;
			}
		}
		catch(Exception e) {
			System.out.println("데이터베이스 검색 오류 : "+e.getMessage());
		}
		return false;
	}
}

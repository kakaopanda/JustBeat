package justbeat;

import java.sql.*;

// DB 생성 : create database `Ranking` CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;
// Table 생성 : create table ranking(Name varchar(20), Score varchar(20));
// Table 정렬 : select * from rank order by score*-1;
// Table 조회 : select * from ranking;

public class DBConnection {

	String SQL = "select * from rank;";
	public static Connection con;
	public static Statement st;
	public static ResultSet rs;
	
	int count = 0;
	public static String ranking[][] = new String[3][12];
	
	public DBConnection() {
		
		try {
			//JDBC Driver Load.
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ranking","root","kakaopanda");
			st = con.createStatement();
			rs = st.executeQuery(SQL);
			System.out.println("Database connection Sucessful."); 	
			// st.executeUpdate("insert into ranking values ('"+name+"', '"+score+"');");
			rs = st.executeQuery("select * from rank order by score*-1;");
			// 정수 데이터의 내림차순 정렬을 위해서는 검색 대상 컬럼값에 (-1)을 곱해줘야 한다.
			
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
						String acc_combo = rs.getString("Acc_Combo");
						String max_combo = rs.getString("Max_Combo");
						String total_beat = rs.getString("Total_Beat");
						String accuracy = rs.getString("Accuracy");
						String perfect = rs.getString("Perfect");
						String great = rs.getString("Great");
						String good = rs.getString("Good");
						String early = rs.getString("Early");
						String late = rs.getString("Late");
						String miss = rs.getString("Miss");
						
						ranking[count][0] = name; ranking[count][1] = score; ranking[count][2] = acc_combo;
						ranking[count][3] =	max_combo; ranking[count][4] = total_beat; ranking[count][5] = accuracy;
						ranking[count][6] = perfect; ranking[count][7] = great; ranking[count][8] = good;
						ranking[count][9] = early; ranking[count][10] = late; ranking[count][11] = miss;
						
						System.out.printf("%d위 : %s 점수 : %s \t 총 콤보 : %s \t 최고 콤보 : %s \t 정확도 : %s \n",count+1,ranking[count][0],ranking[count][1],
								ranking[count][2],ranking[count][3],ranking[count][5]);
						st.close();
					}
				count++;
			}
		}catch(Exception e) {
			System.out.println("Database connection fail. : "+ e.getMessage());
		}
		
	}
}

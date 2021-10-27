package database;

import java.sql.*;

// DB ���� : create database `Ranking` CHARACTER SET UTF8 COLLATE UTF8_GENERAL_CI;
// Table ���� : create table ranking(Name varchar(20), Score varchar(20));
// Table ���� : select * from ranking order by Score DESC;
// Table ��ȸ : select * from ranking;

public class DBConnection {

	String SQL = "select * from ranking;";
	private Connection con;
	private Statement st; // ���ǿ� Statement ����
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
			System.out.println("������ ���̽� ���� ����.");	
			// st.executeUpdate("insert into ranking values ('"+name+"', '"+score+"');");
			// rs = st.executeQuery("select * from ranking order by Score DESC;");
			
			// Operation not allowed after ResultSet closed Error
			// Loop ���ο��� rs�� ����� statement�� ����ؼ� �ٽ� execute�� ���� �� �� ����.
			// ����, Loop �ȿ��� ���ο� statement�� �����Ͽ� ���� execute ����� �Ѵ�.
			
			while(rs.next()) // 1�� ~ 3���� �����͸� ����.
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
			System.out.println("������ ���̽� ���� ���� : "+ e.getMessage());
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
			System.out.println("�����ͺ��̽� �˻� ���� : "+e.getMessage());
		}
		return false;
	}
}

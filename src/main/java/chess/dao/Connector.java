package chess.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.DataAccessException;

public class Connector {

	public static Connection getConnection() {
		String server = "localhost:3306"; // MySQL 서버 주소
		String database = "chess"; // MySQL DATABASE 이름
		String option = "?useSSL=false&serverTimezone=UTC";
		String userName = "woowa2"; //  MySQL 서버 아이디
		String password = "test123"; // MySQL 서버 비밀번호

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(
				"jdbc:mysql://" + server + "/" + database + option, userName, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new DataAccessException(e);
		}
	}

	public static void executeUpdate(String query, Object... args) {
		try (Connection con = getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			for (int i = 1; i <= args.length; i++) {
				pstmt.setObject(i, args[i - 1]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public static <T> T executeQuery(String query, RowMapper<T> rowMapper) {
		try (Connection con = getConnection();
			 PreparedStatement pstmt = con.prepareStatement(query)) {
			return rowMapper.mapRow(pstmt.executeQuery());
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}

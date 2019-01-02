package com.biz.bbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.bbs.vo.BBsMainVO;

/*
 * 반드시 
 * DaoImp는 Dao interface를 implement 해서 
 * 구현을 해야한다
 */
public class BBsMainDaoImp implements BBsMainDao {

	/*
	 * DB 연결 설정 
	 */
	
	Connection dbConn;
	
	public BBsMainDaoImp() {
		this.dbConntion();
	}
	
	
	/*
	 * dbConn 멤버변수를 초기화 하는 메서드
	 * dbConn 멤버변수 : db에 접속하기 위한 통로를 마련하고
	 * 그 정보를 가지고 있는 변수
	 */
	public void dbConntion() {
		
		String dbDriver = "oracle.jdbc.driver.OracleDriver";
		
		// Driver Loading
		try {
			Class.forName(dbDriver);
			
			// DB접속 profile
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "bbsUser";
			String password = "1234";
			
			dbConn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(BBsMainVO vo) {
		// TODO vo를 매개변수로 전달받아 DB에 저장하기
		
		String strDate = vo.getB_date();
		String strAuth = vo.getB_auth();
		String strSubject = vo.getB_subject();
		String strText = vo.getB_text();
		String sql = " INSERT INTO tbl_bbs_main ";
		sql += " VALUES( " + 5 +", ";
		sql += " '" + strDate  + "', ";
		sql += " '" + strAuth + "', ";
		sql += " '" + strSubject + "', ";
		sql += " '" + strText + "' " ;
		sql += " ) " ;
		
		sql = " INSERT INTO tbl_bbs_main ";
		sql += " VALUES( SEQ_BBS_MAIN.NEXTVAL, ?, ?, ?, ? ) ";
		
		PreparedStatement ps;
		try {
			ps = dbConn.prepareStatement(sql);
			ps.setString(1, strDate);
			ps.setString(2, strAuth);
			ps.setString(3, strSubject);
			ps.setString(4, strText);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public List<BBsMainVO> selectAll() {
		// TODO 
		String sql = " SELECT * FROM tbl_bbs_main ";
		/*
		 * sql 문자열을 JDBC에 보내는 절차
		 */
		PreparedStatement ps;
		
		try {
			/*
			 * 문자열로 되어있는 SQL 명령문을
			 * JDBC가 알 수 있도록 변환하는 과정
			 */
			ps = dbConn.prepareStatement(sql);
			
			/*
			 * 드디어 DB에게 JDBC를 통해 SQL을 실행하라고 명령
			 * 그리고 SQL이 정상적으로 수행이 되면 그 결과(SELECT를 실행했으므로 그 리스트)를
			 * rs 변수에 받아라.
			 */
			ResultSet rs = ps.executeQuery();
			
			/*
			 * rs에 받아놓은 데이터 리스트를 List에 옮겨 닮아야 한다.
			 */
			List<BBsMainVO> bbsList = new ArrayList();
			
			// rs에 받아놓은 데이터를 추출하기위해 while문을 사용
			// rs.next()는 리턴값이 boolean형이기 때문에 while(rx.next())로 사용할수있다.
			// 값이 없으면 false를 리턴한다.
			while(rs.next()) {
				
				/*
				 * rs.next()에 의해서 추출된 한 줄의 데이터에서
				 * 각 칼럼의 값을들 추출해서
				 * 변수에 저장해 둔다.
				 */
				long id = rs.getLong("b_id");
				String strDate = rs.getString("b_date");
				String strSubject = rs.getString("b_subject");
				String strAuth = rs.getString("b_auth");
				String strText = rs.getString("b_text");
				
				/*
				 * 변수에 저장된 값들을
				 * vo를 만들어서 vo의 member변수에 저장하라
				 */
				BBsMainVO vo = new BBsMainVO();
				vo.setB_id(id);
				vo.setB_date(strDate);
				vo.setB_subject(strSubject);
				vo.setB_auth(strAuth);
				vo.setB_text(strText);
				
				// 값들이 저장된 vo를 bbsList에 추가해서 list를 만들어라
				bbsList.add(vo);
			}
			return bbsList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BBsMainVO findById(long id) {
		// TODO Auto-generated method stub
		
		String sql = " SELECT * FROM tbl_bbs_main ";
		sql += " WHERE b_id = ? ";
		
		PreparedStatement ps ;
		try {
			ps = dbConn.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			BBsMainVO vo = new BBsMainVO();
			vo.setB_id(rs.getLong("b_id"));
			vo.setB_date(rs.getString("b_date"));
			vo.setB_auth(rs.getString("b_auth"));
			vo.setB_subject(rs.getString("b_subject"));
			vo.setB_text(rs.getString("b_text"));
			
			return vo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public void update(BBsMainVO vo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		// TODO ID를 매개변수로 받아 해당 레코드를 삭제
		String sql = " DELETE FROM tbl_bbs_main ";
		sql += " WHERE b_id = ? ";
		
		PreparedStatement ps;
		try {
			ps = dbConn.prepareStatement(sql);
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		
	}

}

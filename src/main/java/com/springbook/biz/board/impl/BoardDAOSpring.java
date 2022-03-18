package com.springbook.biz.board.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.springbook.biz.board.BoardVO;

// DAO(Data Access Object)
@Repository
public class BoardDAOSpring {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// SQl 명령어들
	private final String BOARD_INSERT = "INSERT INTO BOARD(SEQ, TITLE, WRITER, CONTENT) VALUES((SELECT IFNULL(MAX(SEQ), 0) + 1 FROM BOARD NICKNAME), ?, ?, ?)";
	private final String BOARD_UPDATE = "UPDATE BOARD SET TITLE = ?, CONTENT = ? WHERE SEQ=?";
	private final String BOARD_DELETE = "DELETE FROM BOARD WHERE SEQ=?";
	private final String BOARD_GET = "SELECT * FROM BOARD WHERE SEQ=?";
	private final String BOARD_LIST = "SELECT * FROM BOARD ORDER BY SEQ DESC";
	private final String BOARD_LIST_T = "SELECT * FROM BOARD WHERE TITLE LIKE '%'||?||'%' ORDER BY SEQ DESC";
	private final String BOARD_LIST_C = "SELECT * FROM BOARD WHERE CONTENT LIKE '%'||?||'%' ORDER BY SEQ DESC";

	// CRUD 기능의 메소드 구현
		// 글 등록
		public void insertBoard(BoardVO vo) {
			System.out.println("===> JDBC로 insertBoard() 기능 처리");
			jdbcTemplate.update(BOARD_INSERT, vo.getTitle(), vo.getWriter(), vo.getContent());
		}

		// 글 수정
		public void updateBoard(BoardVO vo) {
			System.out.println("===> JDBC로 updateBoard() 기능 처리");
			jdbcTemplate.update(BOARD_UPDATE, vo.getTitle(), vo.getContent(), vo.getSeq());

		}
		
		// 글 삭제
		public void deleteBoard(BoardVO vo) {
			System.out.println("===> JDBC로 deleteBoard() 기능 처리");
			jdbcTemplate.update(BOARD_DELETE, vo.getSeq());

		}
		
		// 글 상세 조회
		public BoardVO getBoard(BoardVO vo) {
			System.out.println("===> JDBC로 getBoard() 기능 처리");
			Object[] args = {vo.getSeq()};
			return jdbcTemplate.queryForObject(BOARD_GET, args, new BoardRowMapper());
			
		}
		
		// 글 목록 조회
		public List<BoardVO> getBoardList(BoardVO vo) {
			System.out.println("===> JDBC로 getBoardList() 기능 처리");
			Object[] args = {vo.getSearchKeyword()};
			if(vo.getSearchCondition().equals("TITLE")) {
				return jdbcTemplate.query(BOARD_LIST_T, args, new BoardRowMapper());
			} else if(vo.getSearchKeyword().equals("CONTENT")) {
				return jdbcTemplate.query(BOARD_LIST_C, args, new BoardRowMapper());
			}
			return null;
		}
		

		class BoardRowMapper implements RowMapper<BoardVO>{
			public BoardVO mapRow(ResultSet rs, int rowNum) throws SQLException{
				BoardVO board = new BoardVO();
				board.setSeq(rs.getInt("SEQ"));
				board.setTitle(rs.getString("TITLE"));
				board.setWriter(rs.getString("WRITER"));
				board.setContent(rs.getString("CONTENT"));
				board.setRegDate(rs.getDate("REGDATE"));
				board.setCnt(rs.getInt("CNT"));
				return board;
			}
		}
		
		
		
}

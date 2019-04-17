package kr.goott.gymcarry.model.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import kr.goott.gymcarry.model.dto.CommunityDTO;

@Repository
public class CommunityDAO implements CommunityDAOInterface {

	private static final Logger logger = LoggerFactory.getLogger(CommunityDAO.class);
	
	@Inject
	SqlSession sqlSession;
	
	@Override
	public void insertCommunity(CommunityDTO dto) {
		logger.info("insert community called...");
		logger.info("dao에서 dto userid"+dto.getUserid());
		sqlSession.insert("community.insertCommunity" , dto);  //dto 불러온거를 sqlsessiond을 통해 때려받는다
	}

	@Override
	public List<CommunityDTO> comList() {
		logger.info("community list called...");
		return sqlSession.selectList("community.listCommunity");
		
	}

	@Override
	public CommunityDTO viewCommunity(int comNo) {
		logger.info("view community called...");
		return sqlSession.selectOne("community.viewCommunity",comNo);
	}

	@Override
	public CommunityDTO editCommunity(CommunityDTO dto) {
		logger.info("edit community called");
		return sqlSession.selectOne("community.editCommunity",dto);

	}

	@Override
	public void deleteCommunity(int comNo) {
		sqlSession.delete("community.deleteCommunity", comNo);
		
	}

	@Override
	public void viewCount(int comNo) {
		sqlSession.update("community.viewCount", comNo);
		
	}

	@Override
	public void replyCount(int comNo) {
		sqlSession.update("community.replyCount", comNo);
		
	}

	@Override
	public void likeCount(int comNo) {
		sqlSession.update("community.likeCount", comNo);
		
	}

	@Override
	public void replyCountSub(int comNo) {
		sqlSession.update("community.replyCountSub", comNo);
		
	}

	@Override
	public void likeCountSub(int comNo) {
		sqlSession.update("community.replyCountSub", comNo);
		
	}

	@Override
	public void insertLike(int comNo, String userid) {
		sqlSession.insert("community.insertLike", comNo,userid);
		
	}

	@Override
	public void deleteLike(int comNo, String userid) {
		sqlSession.delete("community.deletetLike", comNo,userid);
		
	}
	
	



}

package kr.co.kmarket2.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.co.kmarket2.dao.AdminDAO;
import kr.co.kmarket2.vo.Cate1VO;
import kr.co.kmarket2.vo.Cate2VO;
import kr.co.kmarket2.vo.ProductVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminService {

	@Autowired
	private AdminDAO dao; 
	
	public List<Cate1VO> selectCate1 (){
		return dao.selectCate1();
	};
	public List<Cate2VO> selectCate2 (int cate1){
		return dao.selectCate2(cate1);
	};
	public int insertProduct (ProductVO vo) {
		List<String> names = fileUpload(vo);
		vo.setThumb1("/thumb/"+names.get(0));
		vo.setThumb2("/thumb/"+names.get(1));
		vo.setThumb3("/thumb/"+names.get(2));
		vo.setDetail("/thumb/"+names.get(3));
		int result = 0;
		if(vo.getThumb1() != null) {
			result = dao.insertProduct(vo);
		}
		log.info("prodCate1 : "+vo.getProdCate1());
		return result;
	};
	public List<ProductVO> selectProducts(int start){
		return dao.selectProducts(start);
	};
	public List<ProductVO> selectProductsSeller(int start){
		return dao.selectProductsSeller(start);
	};
	public List<ProductVO> searchProducts(String search, String type, int start){
		return dao.searchProducts(search,type,start);
	};
	public List<ProductVO> searchProductsSeller(String seller,String search,String type,int start){
		return dao.searchProductsSeller(seller,search,type,start);
	};
	public int updateProduct (ProductVO vo){
		return dao.updateProduct(vo);
	};
	public int deleteProduct (String prodNo){
		return dao.deleteProduct(prodNo);
	};
	// 썸네일 업로드 ////////////
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath; // 프로젝트 내 가상 경로
	public List<String> fileUpload(ProductVO vo) throws NullPointerException {
		// 첨부 파일
		MultipartFile[] multi = {vo.getThumb1_(),vo.getThumb2_(),vo.getThumb3_(),vo.getDetail_()};
		List<String> names = new ArrayList<>();
		for(MultipartFile file : multi) {
			
				// 시스템 경로
				String path = new File(uploadPath).getAbsolutePath();
				
				// 새 파일명 생성
				String oName = file.getOriginalFilename();
				String ext = oName.substring(oName.lastIndexOf("."));
				String nName = UUID.randomUUID().toString()+ext;
				
				// 파일 저장
				try {
					file.transferTo(new File(path, nName));
					names.add(nName);
				} catch (IllegalStateException e) {
					log.error(e.getMessage());
				} catch (IOException e) {
					log.error(e.getMessage());
				}
				
		}
		return names;
	}
	// 페이징 처리 시작 ///////////////////////////////////////////////////////
		// 현재 페이지 번호
		public int getCurrnetPage(String pg) {
			int currentPage = 1;
			if(pg != null) {
				currentPage = Integer.parseInt(pg);
			}
			return currentPage;
		}
		// 페이지 시작값
		public int getLimitStart(int currentPage) {
			return (currentPage-1) * 10;
		}
		// 게시물 총 갯수
		public int selectCountTotal() {
			return dao.selectCountTotal();
		}
		// 게시물 총 갯수(판매자)
		public int selectCountTotalSeller(String seller) {
			return dao.selectCountTotalSeller(seller);
		}
		// 검색 게시물 총 갯수
		public int selectCountTotalSearch (String search,String type) {
			return dao.selectCountTotalSearch(search,type);
		};
		public int selectCountTotalSearchSeller (String seller, String search, String type) {
			return dao.selectCountTotalSearchSeller(seller, search, type);
		};
		// 마지막 페이지 번호
		public int getLastPageNum(int total) {
			int lastPageNum = 0;
			if(total % 10 == 0){
				lastPageNum = total / 10;
			}else{
				lastPageNum = total / 10 + 1;
			}
			return lastPageNum;
		}
		// 시작 페이지 번호
		public int getPageStartNum(int total, int start) {
			return total - start;
		}
		// 페이지 그룹
		public int[] getPageGroup(int currentPage, int lastPageNum) {
			int currentPageGroup = (int)Math.ceil(currentPage / 10.0);
			int pageGroupStart = (currentPageGroup - 1) * 10 + 1;
			int pageGroupEnd = currentPageGroup * 10;
			if(pageGroupEnd > lastPageNum){
				pageGroupEnd = lastPageNum;
			}
			int[] result = {pageGroupStart,pageGroupEnd};
			return result;
		}
		// 페이징 처리 끝 ///////////////////////////////////////////////////////
	/////// 관리자 고객센터 관련 /////////////////////////////////////////////
}

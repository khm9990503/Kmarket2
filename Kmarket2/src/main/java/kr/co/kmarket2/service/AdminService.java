package kr.co.kmarket2.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
}

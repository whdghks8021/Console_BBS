package com.biz.bbs.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.biz.bbs.dao.BBsMainDao;
import com.biz.bbs.dao.BBsMainDaoImp;
import com.biz.bbs.vo.BBsMainVO;

/*
 * Dao와 연계해서 CRUD에 대한 구체적인 실행을 실시하는 class
 */
public class BBsMainService {
	
	/*
	 * member 변수들을 생성하는데
	 */
	// dao.selectAll() 에서 return한
	// bbsMainVO 들을 담을 List
	List<BBsMainVO> bbsMainList;
	
	/*
	 * 어떤 클래스에 대한 객체를 선언할때
	 * 만약 해당 클래스들을 대표하는 interface가 있으면
	 * interface를 자료형으로 선ㅇ언을한ㄷ.
	 */
	BBsMainDao mainDao;
	Scanner scan ;

	public BBsMainService() {
		bbsMainList = new ArrayList();
		mainDao = new BBsMainDaoImp();
		scan = new Scanner(System.in);
	}
	/*
	 * 게시판 List를 보는 method() 선언
	 */
	public void bbsMenu() {
		while(true) {
			System.out.println("===========================================================");
			System.out.println("1.리스트보기 2.추가 3.수정 4.삭제 0.종료");
			System.out.println("-----------------------------------------------------------");
			System.out.print("선택 >>");
			String strM = scan.nextLine();
			int intM = Integer.valueOf(strM);
			if(intM == 0) return;
			if(intM == 1) this.viewBBsList();
			if(intM == 2) this.insertBBs(); // 데이터 추가
			if(intM == 3) this.updateBBs(); // 데이터 수정
			if(intM == 4) {
				// 삭제할 데이터를 확인
				this.viewBBsList();
				this.deleteBBs(); // 데이터 삭제
			}
		}
		
	}
	private void insertBBs() {
		/*
		 * 키보드에서
		 * 작성자, 제목, 내용을 입력받고
		 * 현재 컴퓨터 날짜를 작성일자로 하여 DB 저장하기.
		 */
		System.out.print("작성자 >>");
		String strAuth = scan.nextLine();
		System.out.print("제목 >>");
		String strSubject = scan.nextLine();
		System.out.print("내용 >>");
		String strText = scan.nextLine();
		
		// 작성일자 생성
		// old버전
		// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		// Date toDay = new Date();
		// String strDate = sf.format(toDay);
		
		// New(1.8이상) 버전
		LocalDate ld = LocalDate.now();
		String strDate = ld.toString();
		
		// 변수를 vo에 담기
		BBsMainVO vo = new BBsMainVO();
		vo.setB_date(strDate);
		vo.setB_auth(strAuth);
		vo.setB_subject(strSubject);
		vo.setB_text(strText);
		
		// vo를 dao에게 전달
		mainDao.insert(vo);
		
	}
	private void deleteBBs() {
		System.out.print("삭제할 번호 >>");
		String strId = scan.nextLine();
		
		long longId = Long.valueOf(strId);
		
		// 삭제하기 전에
		// 삭제할 데이터를 다시 확인 시켜주자
		
		// id를 기준으로 데이터 1개 가져오기
		BBsMainVO vo = mainDao.findById(longId);
		
		System.out.println("===================");
		System.out.println("삭제할 데이터 확인");
		System.out.println("-------------------");
		System.out.println("작성일자 : " + vo.getB_date());
		System.out.println("작성자 : " + vo.getB_auth());
		System.out.println("제목 : " + vo.getB_subject());
		System.out.println("내용 : " + vo.getB_text());
		System.out.println("===================");
		System.out.println("정말 삭제 하시겠습니까? (YES) >>");
		
		String confirm = scan.nextLine();
		if(confirm.equals("YES")) {
			mainDao.delete(longId);
			System.out.println("삭제 되었습니다.");
		} else {
			System.out.println("취소 되었습니다.");
		}
		
	}
	private void updateBBs() {
		
	}
	public void viewBBsList() {
		bbsMainList = mainDao.selectAll();
		System.out.println("=====================================================================");
		System.out.println("나의 게시판 v1.1");
		System.out.println("=====================================================================");
		System.out.println("NO\t날짜\t작성자\t제목\t\t내용");
		System.out.println("---------------------------------------------------------------------");
		for(BBsMainVO v : bbsMainList) {
			System.out.print(v.getB_id()+"  ");
			System.out.print(v.getB_date()+"\t");
			System.out.print(v.getB_auth()+"\t");
			System.out.print(v.getB_subject()+"\t\t");
			System.out.println(v.getB_text());
		}
		
	}

}

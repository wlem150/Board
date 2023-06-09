package org.mine.controller;

import java.util.HashMap;
import java.util.Map;

import org.mine.domain.SampleVO;
import org.mine.domain.Ticket;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@RestController // jsp 와 달리 순수한 데이터를 반환하는 형태이다. 주로 json 이나 xml 을 전달한다.
@RequestMapping("/sample")
@Log4j
public class SampleController {
	// 단순한 텍스트
	@GetMapping(value = "/getText", produces = "text/plain; charset=utf-8")
	public String getText() {
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		return "안녕하세요";
	}
	
	// json, xml 형태로 전송
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112,"스타", "로드");
	}
	
	// map 형태로 전송시
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		map.put("first", new SampleVO(111,"그루트","주니어"));
		return map;
	}
	
	// responseEntity 타입으로 전송 / 상태코드와 함께 전송
	@GetMapping(value="/check", params= {"height", "weight"})
	public ResponseEntity<SampleVO> check(double height, double weight){
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		ResponseEntity<SampleVO> result = null;
		if (height < 150) {
			//sample/check.json?height=140&weight=60 / height 가 150보다 작을 시 bad_gateway
			// 네트워크에 가서 header 를 확인해야 됨
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);	
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	// PathVariable 을 사용해서 xml, json 으로 파싱
	@GetMapping(value = "/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") int pid) {
		// http://localhost:8080/sample/product/bags/1234
		return new String[] {"category : " + cat, "productid : " + pid};
	}
	
	// 이것만 유일하게 Post 
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert........ ticket" + ticket);
		return ticket;
	}
	
	
}

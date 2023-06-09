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

@RestController // jsp �� �޸� ������ �����͸� ��ȯ�ϴ� �����̴�. �ַ� json �̳� xml �� �����Ѵ�.
@RequestMapping("/sample")
@Log4j
public class SampleController {
	// �ܼ��� �ؽ�Ʈ
	@GetMapping(value = "/getText", produces = "text/plain; charset=utf-8")
	public String getText() {
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		return "�ȳ��ϼ���";
	}
	
	// json, xml ���·� ����
	@GetMapping(value = "/getSample", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE,
			MediaType.APPLICATION_XML_VALUE})
	public SampleVO getSample() {
		return new SampleVO(112,"��Ÿ", "�ε�");
	}
	
	// map ���·� ���۽�
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap(){
		Map<String, SampleVO> map = new HashMap<String, SampleVO>();
		map.put("first", new SampleVO(111,"�׷�Ʈ","�ִϾ�"));
		return map;
	}
	
	// responseEntity Ÿ������ ���� / �����ڵ�� �Բ� ����
	@GetMapping(value="/check", params= {"height", "weight"})
	public ResponseEntity<SampleVO> check(double height, double weight){
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		ResponseEntity<SampleVO> result = null;
		if (height < 150) {
			//sample/check.json?height=140&weight=60 / height �� 150���� ���� �� bad_gateway
			// ��Ʈ��ũ�� ���� header �� Ȯ���ؾ� ��
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);	
		}else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo);
		}
		return result;
	}
	
	// PathVariable �� ����ؼ� xml, json ���� �Ľ�
	@GetMapping(value = "/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat, @PathVariable("pid") int pid) {
		// http://localhost:8080/sample/product/bags/1234
		return new String[] {"category : " + cat, "productid : " + pid};
	}
	
	// �̰͸� �����ϰ� Post 
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert........ ticket" + ticket);
		return ticket;
	}
	
	
}

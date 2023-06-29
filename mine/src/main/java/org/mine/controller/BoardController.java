package org.mine.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.mine.domain.BoardAttachVO;
import org.mine.domain.BoardVO;
import org.mine.domain.Criteria;
import org.mine.domain.PageDTO;
import org.mine.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {
	private BoardService service;

	private void deleteFiles(List<BoardAttachVO> attachList) {
		if (attachList == null || attachList.size() == 0) {
			return;
		}

		log.info("delete attach files=============================");
		log.info(attachList);

		attachList.forEach(attach -> {
			try {
				Path file = Paths.get(
						"C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

				Files.deleteIfExists(file);

				if (Files.probeContentType(file).startsWith("image")) {
					Path thumbnail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_"
							+ attach.getFileName());

					Files.delete(thumbnail);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		);
	}
	
	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("getListWithPaging");
//		model.addAttribute("list", service.getListWithPaging(cri));
//		model.addAttribute("pageMaker", new PageDTO(cri, 123));d

		int total = service.getTotalCount(cri);
		model.addAttribute("list", service.getListWithPaging(cri));
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}

	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register : ====" + board);
		log.info(board.getAttachList());
		if (board.getAttachList() != null) {
			log.info("tes============================================");
			board.getAttachList().forEach(attach -> log.info(attach));
		}

		rttr.addFlashAttribute("result", board.getBno());
		service.register(board);
		return "redirect:/board/list";
	}

	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void register() {

	}

	@GetMapping({ "/get", "/modify" })
	public void get(@RequestParam("bno") long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		log.info("/get or modify");
		model.addAttribute("board", service.get(bno));
	}
	
	@PreAuthorize("principal.username == #wrtier")
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("/ modify");
		if (service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		log.info(rttr.addAttribute("keyword", cri.getKeyword()));
		return "redirect:/board/list";
	}

	@PreAuthorize("principal.username == #wrtier")
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") long bno, RedirectAttributes rttr, Criteria cri, String writer) {
		log.info("remove =========== " + bno);
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		if(service.remove(bno)) {
			deleteFiles(attachList);
			
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list" + cri.getListLink();
	}

	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(long bno) {
		log.info("getAttachList" + bno);
		return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);
	}


}

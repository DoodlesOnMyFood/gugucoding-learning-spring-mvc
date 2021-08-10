package org.zerock.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
    private BoardService service;

    @GetMapping("list")
    public void list(Model model){
        log.info("list");
        model.addAttribute("list", service.getList());
    }

    @GetMapping("get")
    public void get(Long bno, Model model){
        log.info("get");
        model.addAttribute("board", service.get(bno));
    }

    @PostMapping("modify")
    public String modify(BoardVO board, RedirectAttributes rttr){
        log.info("modify : " + board);
        if(service.modify(board)){
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PostMapping("remove")
    public String remove(Long bno, RedirectAttributes rttr){
        log.info("Remove : " + bno);

        if(service.remove(bno)){
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }
}
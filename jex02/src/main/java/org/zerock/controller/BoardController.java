package org.zerock.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@AllArgsConstructor
@RequestMapping("/board/*")
public class BoardController {
    private BoardService service;

    @GetMapping("list")
    public void list(Criteria criteria,Model model){
        log.info("list");
        log.info(criteria);
        PageDTO pageDTO = new PageDTO(criteria, service.getTotal(criteria));
        log.info(pageDTO);
        model.addAttribute("list", service.getList(criteria));
        model.addAttribute("pageMaker", pageDTO);
    }

    @GetMapping({"get", "modify"})
    public void get(@RequestParam Long bno, @ModelAttribute("criteria") Criteria criteria, Model model){
        log.info("get or modify");
        model.addAttribute("board", service.get(bno));
    }


    @PostMapping("register")
    public String register(BoardVO boardVO, RedirectAttributes rttr){
        log.info("register");
        service.register(boardVO);
        rttr.addFlashAttribute("result", boardVO.getBno());
        return "redirect:/board/list";
    }

    @PostMapping("modify")
    public String modify(BoardVO board, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes rttr){
        log.info("modify : " + board);
        if(service.modify(board)){
            rttr.addFlashAttribute("result", "success");
        }
        rttr.addAttribute("pageNum", criteria.getPageNum());
        rttr.addAttribute("amount", criteria.getAmount());
        return "redirect:/board/list";
    }

    @PostMapping("remove")
    public String remove(Long bno, @ModelAttribute("criteria") Criteria criteria, RedirectAttributes rttr){
        log.info("Remove : " + bno);

        if(service.remove(bno)){
            rttr.addFlashAttribute("result", "success");
        }

        rttr.addAttribute("pageNum", criteria.getPageNum());
        rttr.addAttribute("amount", criteria.getAmount());
        return "redirect:/board/list";
    }

    @GetMapping("register")
    public void register(){

    }
}

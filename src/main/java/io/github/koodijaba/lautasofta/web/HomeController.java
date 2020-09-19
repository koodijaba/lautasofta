package io.github.koodijaba.lautasofta.web;

import io.github.koodijaba.lautasofta.domain.*;
import io.github.koodijaba.lautasofta.domain.Thread;
import io.github.koodijaba.lautasofta.domain.repository.jpa.BoardRepository;
import io.github.koodijaba.lautasofta.domain.repository.jpa.ReplyRepository;
import io.github.koodijaba.lautasofta.domain.repository.jpa.ThreadRepository;
import io.github.koodijaba.lautasofta.domain.repository.search.ThreadSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Instant;
import java.util.Collections;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.data.domain.Sort.sort;

@Controller
public class HomeController {
    private final ThreadRepository threadRepository;
    private final ThreadSearchRepository threadSearchRepository;
    private final ReplyRepository replyRepository;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id", "createdAt", "modifiedAt");
    }

    @Autowired
    public HomeController(ThreadRepository threadRepository, ThreadSearchRepository threadSearchRepository, ReplyRepository replyRepository) {
        this.threadRepository = threadRepository;
        this.threadSearchRepository = threadSearchRepository;
        this.replyRepository = replyRepository;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/{board}")
    public String showBoard(@PathVariable Board board, @RequestParam(required = false) Instant before, Model model) {
        model.addAttribute("board", board);
        Pageable pageable = PageRequest.of(0, 10, sort(Thread.class).by(Thread::getModifiedAt).descending());
        model.addAttribute("threads", before == null
                ? threadRepository.findByBoard(board, pageable)
                : threadRepository.findByBoardAndModifiedAtIsBefore(board, before, pageable)
        );
        model.addAttribute("thread", new Thread());
        return "board";
    }

    @GetMapping("/mythreads")
    @ResponseBody
    public Object myThreads(@RequestParam(required = false) Instant before, Principal principal, Model model) {
        Pageable pageable = PageRequest.of(0, 10, sort(Thread.class).by(Thread::getModifiedAt).descending());
        model.addAttribute("threads", before == null
                ? threadRepository.findByCreatedBy(principal.getName(), pageable)
                : threadRepository.findByCreatedByAndModifiedAtBefore(principal.getName(), before, pageable)
        );
        return model.getAttribute("threads");
    }

    @GetMapping("/repliedthreads")
    @ResponseBody
    public Object repliedThreads(@RequestParam(required = false) Instant before, Principal principal, Model model) {
        model.addAttribute("threads", replyRepository.findByCreatedBy(principal.getName()).stream()
                    .map(Reply::getThread)
                    .collect(toUnmodifiableList()));
        return model.getAttribute("threads");
    }

    @PostMapping("/{board}")
    public String createThread(@PathVariable Board board, @Valid Thread thread) {
        threadRepository.save(thread);
        threadSearchRepository.save(thread);
        return "redirect:/" + board.getName();
    }

    @GetMapping("/{board}/{thread}")
    public String showThread(@PathVariable Board board, @PathVariable Thread thread, Model model) {
        // TODO: Throw 404 if board and thread pathvariables dont match
        model.addAttribute("thread", thread);
        model.addAttribute("replies", replyRepository.findByThread(thread, sort(Reply.class).by(Reply::getCreatedAt)));
        model.addAttribute("reply", new Reply());
        return "thread";
    }

    @PostMapping("/{board}/{thread}")
    public String addReply(@PathVariable Board board, @PathVariable Thread thread, @Valid Reply reply) {
        thread.getReplies().add(reply);
        threadRepository.save(thread);
        return "redirect:/" + board.getName() + "/" + thread.getId();
    }
}

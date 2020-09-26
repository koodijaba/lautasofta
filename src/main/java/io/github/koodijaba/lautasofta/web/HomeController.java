package io.github.koodijaba.lautasofta.web;

import io.github.koodijaba.lautasofta.domain.Board;
import io.github.koodijaba.lautasofta.domain.Reply;
import io.github.koodijaba.lautasofta.domain.Thread;
import io.github.koodijaba.lautasofta.domain.repository.jpa.ReplyRepository;
import io.github.koodijaba.lautasofta.domain.repository.jpa.ThreadRepository;
import io.github.koodijaba.lautasofta.domain.repository.search.ThreadSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.mapping.SimpleSolrMappingContext;
import org.springframework.data.solr.repository.support.SolrEntityInformationCreatorImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static java.util.stream.Collectors.toUnmodifiableList;
import static org.springframework.data.domain.Sort.sort;

@Controller
public class HomeController {
    private final ThreadRepository threadRepository;
    private final ThreadSearchRepository threadSearchRepository;
    private final ReplyRepository replyRepository;
    private final SolrTemplate solrTemplate;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id", "createdAt", "modifiedAt");
    }

    @Autowired
    public HomeController(ThreadRepository threadRepository, ThreadSearchRepository threadSearchRepository, ReplyRepository replyRepository, SolrTemplate solrTemplate) {
        this.threadRepository = threadRepository;
        this.threadSearchRepository = threadSearchRepository;
        this.replyRepository = replyRepository;
        this.solrTemplate = solrTemplate;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/{board}")
    public String showBoard(@PathVariable Board board,
                            @RequestParam(required = false) byte[] cursor,
                            Model model,
                            @ModelAttribute("thread") Thread thread) {
        model.addAttribute("board", board);
        Optional<Instant> before = Optional.ofNullable(cursor)
            .map(Base64Utils::decode)
            .map(String::new)
            .map(Instant::parse);
        Pageable pageable = PageRequest.of(0, 10, sort(Thread.class).by(Thread::getModifiedAt).descending());
        model.addAttribute("threads", before
                .map(b -> threadRepository.findByBoardAndModifiedAtIsBefore(board, b, pageable))
                .orElseGet(() -> threadRepository.findByBoard(board, pageable)));
        return "board";
    }

    @PostMapping("/{board}")
    public String createThread(@PathVariable Board board,
                               @Valid Thread thread,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return showBoard(board, null, model, thread);
        }
        threadRepository.save(thread);
        // SimpleSolrRepository#save(S, Duration) does an unnecessary manual commit so we use SolrTemplate directly
        solrTemplate.saveBean(
                new SolrEntityInformationCreatorImpl(new SimpleSolrMappingContext()).getEntityInformation(Thread.class).getCollectionName(),
                thread,
                Duration.ofMinutes(1)
        );
        return "redirect:/" + board.getName();
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

    @GetMapping("/{board}/{thread}")
    public String showThread(@PathVariable Board board,
                             @PathVariable Thread thread,
                             @ModelAttribute("reply") Reply reply,
                             Model model) {
        // TODO: Throw 404 if board and thread pathvariables dont match
        model.addAttribute("replies", replyRepository.findByThread(thread, sort(Reply.class).by(Reply::getCreatedAt)));
        return "thread";
    }

    @PostMapping("/{board}/{thread}")
    public String addReply(@PathVariable Board board,
                           @PathVariable Thread thread,
                           @Valid Reply reply,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return showThread(board, thread, reply, model);
        }
        thread.getReplies().add(reply);
        threadRepository.save(thread);
        return "redirect:/" + board.getName() + "/" + thread.getId();
    }
}

package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.domain.dreamboard.CreateBoardDTO;
import com.beyou.dreamsBoard.domain.dreamboard.DreamBoard;
import com.beyou.dreamsBoard.domain.dreamboard.DreamBoardRepository;
import com.beyou.dreamsBoard.domain.reason.Reason;
import com.beyou.dreamsBoard.user.User;
import com.beyou.dreamsBoard.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


import java.util.*;

@RestController
@RequestMapping("/dreamboard")
public class DreamBoardController {
    @Autowired
    private DreamBoardRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{id}")
    public List<DreamBoard> getDreamboards(@PathVariable UUID id){
        List<DreamBoard> dreamBoards = repository.findAllByUserId(id);
        return dreamBoards;
    }

    @GetMapping(value = "/getDreamBoard/{id}")
    public ResponseEntity<?> getUniqueDreamboard(@PathVariable UUID id){
        try {
            DreamBoard dreamBoard = repository.findById(id).orElseThrow();
            return ResponseEntity.ok(dreamBoard);
        }catch (Exception e){
            ResponseEntity.badRequest().body(Map.of("status", "error"));
        }
        return ResponseEntity.badRequest().body(Map.of("status", "unknownError"));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<?> handleMissingParameter(MissingServletRequestPartException e){
        return ResponseEntity.badRequest().body(Map.of("status", "errorMissing"));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createDreamBoard(@RequestBody CreateBoardDTO createBoardDTO){
        try{
            User user = userRepository.findById(createBoardDTO.userId()).orElseThrow(() -> new UsernameNotFoundException("User not Found"));

            DreamBoard dreamBoard = new DreamBoard(createBoardDTO, user, new ArrayList<>());

            List<Reason> reasons = dreamBoard.getReasons();
            if (reasons == null) {
                reasons = new ArrayList<>();
            }

            List<Reason> reasonsToAdd = new ArrayList<Reason>(createBoardDTO.reasons());

            reasons.add(new Reason(reasonsToAdd.get(0).getTitle(), reasonsToAdd.get(0).getImg(), reasonsToAdd.get(0).getText(), dreamBoard));
            reasons.add(new Reason(reasonsToAdd.get(1).getTitle(), reasonsToAdd.get(1).getImg(), reasonsToAdd.get(1).getText(), dreamBoard));
            reasons.add(new Reason(reasonsToAdd.get(2).getTitle(), reasonsToAdd.get(2).getImg(), reasonsToAdd.get(2).getText(), dreamBoard));
            dreamBoard.setReasons(reasons);
            try {
                dreamBoard = repository.save(dreamBoard);
                return ResponseEntity.ok().body(Map.of("success", "Dreamboard saved successfully!"));
            } catch (Exception e) {
                throw new RuntimeException("Error trying to save dreamboard", e);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(Map.of("status", "errorDreamboard"));
        }

    }

    @DeleteMapping(value = "/{dreamBoardId}")
    public ResponseEntity<?> deleteDreamboard(@PathVariable UUID dreamBoardId){
        Optional<DreamBoard> dreamBoardToDeleteOptional = repository.findById(dreamBoardId);
        if(dreamBoardToDeleteOptional.isPresent()){
            try{
                DreamBoard dreamBoardToDelete = dreamBoardToDeleteOptional.get();
                repository.delete(dreamBoardToDelete);
                return ResponseEntity.ok().body(Map.of("success", "DreamBoard deleted successfully"));
            }catch (Exception e){
                return ResponseEntity.badRequest().body(Map.of("error", "error trying to delete DreamBoard"));
            }
        }else{
            return ResponseEntity.badRequest().body(Map.of("error", "DreamBoard not finded, try again"));
        }

    }

    @PutMapping(value = "/{dreamBoardId}")
    public ResponseEntity<?> editDreamBoard(@PathVariable UUID dreamBoardId,
                                            @RequestParam("title") String title,
                                            @RequestParam("mainObjective_text") String mainObjective_text,
                                            @RequestParam("objective_text") String objective_text,
                                            @RequestParam("reason_title") String reason_title,
                                            @RequestParam("background_img") String backgroundImg,
                                            @RequestParam("mainObjective_img") String mainObjective_img,
                                            @RequestParam("objective_img") String objective_img,
                                            @RequestParam("0[title]") String reason_title0,
                                            @RequestParam("0[img]") String reason_img,
                                            @RequestParam("0[text]") String reason_text,
                                            @RequestParam("1[title]") String reason_title1,
                                            @RequestParam("1[img]") String reason_img1,
                                            @RequestParam("1[text]") String reason_text1,
                                            @RequestParam("2[title]") String reason_title2,
                                            @RequestParam("2[img]") String reason_img2,
                                            @RequestParam("2[text]") String reason_text2){

        Optional<DreamBoard> dreamBoardToEditOptional = repository.findById(dreamBoardId);
        if(dreamBoardToEditOptional.isPresent()){
             DreamBoard dreamBoardToEdit = dreamBoardToEditOptional.get();
             dreamBoardToEdit.setTitle(title);
             dreamBoardToEdit.setMainObjective_text(mainObjective_text);
             dreamBoardToEdit.setObjective_text(objective_text);
             dreamBoardToEdit.setReason_title(reason_title);
             dreamBoardToEdit.setBackground_img(backgroundImg);
             dreamBoardToEdit.setMainObjectiveImg(mainObjective_img);
             dreamBoardToEdit.setObjective_img(objective_img);

            List<Reason> reasonsEdit = dreamBoardToEdit.getReasons();
            if (reasonsEdit == null) {
                reasonsEdit = new ArrayList<>();
            }else{
                reasonsEdit.clear();
            }

            reasonsEdit.add(new Reason(reason_title0, reason_img, reason_text, dreamBoardToEdit));
            reasonsEdit.add(new Reason(reason_title1, reason_img1, reason_text1, dreamBoardToEdit));
            reasonsEdit.add(new Reason(reason_title2, reason_img2, reason_text2, dreamBoardToEdit));
             dreamBoardToEdit.setReasons(reasonsEdit);
             try{
                 repository.save(dreamBoardToEdit);
                 return ResponseEntity.ok().body(Map.of("success", "Dreamboard Edited successfully!"));
             }catch(Exception e) {
                 return ResponseEntity.badRequest().body(Map.of("error", "Error trying edit dreamBoard, try again"));
             }

        }else{
            return ResponseEntity.badRequest().body(Map.of("error", "Error trying to find dreamBoard, try again"));
        }
    }


}

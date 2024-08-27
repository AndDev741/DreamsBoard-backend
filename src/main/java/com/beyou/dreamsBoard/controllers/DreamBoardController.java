package com.beyou.dreamsBoard.controllers;

import com.beyou.dreamsBoard.domain.dreamboard.CreateBoardDTO;
import com.beyou.dreamsBoard.domain.dreamboard.DreamBoard;
import com.beyou.dreamsBoard.domain.dreamboard.DreamBoardRepository;
import com.beyou.dreamsBoard.domain.dreamboard.EditBoardDTO;
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

    @PutMapping(value = "/{dreamBoardId}", consumes = "application/json")
    public ResponseEntity<?> editDreamBoard(@PathVariable UUID dreamBoardId, @RequestBody EditBoardDTO editBoardDTO){

        Optional<DreamBoard> dreamBoardToEditOptional = repository.findById(dreamBoardId);
        if(dreamBoardToEditOptional.isPresent()){
             DreamBoard dreamBoardToEdit = dreamBoardToEditOptional.get();
             dreamBoardToEdit.setTitle(editBoardDTO.title());
             dreamBoardToEdit.setMainObjective_text(editBoardDTO.mainObjective_text());
             dreamBoardToEdit.setObjective_text(editBoardDTO.objective_text());
             dreamBoardToEdit.setReason_title(editBoardDTO.reason_title());
             dreamBoardToEdit.setBackground_img(editBoardDTO.background_img());
             dreamBoardToEdit.setMainObjectiveImg(editBoardDTO.mainObjective_img());
             dreamBoardToEdit.setObjective_img(editBoardDTO.objective_img());

            List<Reason> reasonsEdit = dreamBoardToEdit.getReasons();
            if (reasonsEdit == null) {
                reasonsEdit = new ArrayList<>();
            }else{
                reasonsEdit.clear();
            }
            List<Reason> ReasonsEdited = new ArrayList<Reason>(editBoardDTO.reasons());

            reasonsEdit.add(new Reason(ReasonsEdited.get(0).getTitle(), ReasonsEdited.get(0).getImg(), ReasonsEdited.get(0).getText(), dreamBoardToEdit));
            reasonsEdit.add(new Reason(ReasonsEdited.get(1).getTitle(), ReasonsEdited.get(1).getImg(), ReasonsEdited.get(1).getText(), dreamBoardToEdit));
            reasonsEdit.add(new Reason(ReasonsEdited.get(2).getTitle(), ReasonsEdited.get(2).getImg(), ReasonsEdited.get(2).getText(), dreamBoardToEdit));
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

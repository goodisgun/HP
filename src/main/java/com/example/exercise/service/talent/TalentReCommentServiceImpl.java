package com.example.exercise.service.talent;

import com.example.exercise.dto.talent.TalentReCommentRequestDto;
import com.example.exercise.entity.TalentComment;
import com.example.exercise.entity.TalentReComment;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.repository.TalentCommentRepository;
import com.example.exercise.repository.TalentReCommentRepository;
import com.example.exercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TalentReCommentServiceImpl implements TalentReCommentService {

  private final UserRepository userRepository;

  private final TalentCommentRepository talentCommentRepository;

  private final TalentReCommentRepository talentReCommentRepository;

  //대댓글 생성
  @Override
  public ResponseEntity<Void> createTalentReComment(Long talentCommentId, User user,
      TalentReCommentRequestDto talentReCommentRequestDto) {

    user = _getUser(user.getUsername());
    TalentComment talentComment = _getTalentComment(talentCommentId);

    TalentReComment talentReComment = new TalentReComment(talentComment, user, talentReCommentRequestDto);
    talentReCommentRepository.save(talentReComment);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  // 대댓글 수정
  @Override
  public ResponseEntity<Void> updateTalentReComment(Long talentReCommentId, User user,
      TalentReCommentRequestDto talentReCommentRequestDto) {

    TalentReComment talentReComment = _getTalentReComment(talentReCommentId);
    user = _getUser(user.getUsername());

    if (!talentReComment.getUser().equals(user) && user.getUserRole() != UserRoleEnum.ADMIN) {
      throw new AccessDeniedException("작성자만 댓글 삭제가 가능합니다.");
    }

    talentReComment.updateTalentReComment(talentReCommentRequestDto.getContent());
    talentReCommentRepository.save(talentReComment);
    return ResponseEntity.ok().build();
  }

  // 대댓글 삭제
  @Override
  public void deleteTalentReComment(Long talentReCommentId, User user) {

    TalentReComment talentReComment = _getTalentReComment(talentReCommentId);
    user = _getUser(user.getUsername());

    if (!talentReComment.getUser().equals(user) && user.getUserRole() != UserRoleEnum.ADMIN) {
      throw new AccessDeniedException("작성자만 댓글 삭제가 가능합니다.");
    }

    talentReCommentRepository.delete(talentReComment);
  }



  //중복코드
  private User _getUser(String username){
    User user = userRepository.findByUsername(username).orElseThrow(
        ()->new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
    );
    return user;
  }

  //중복코드
  private TalentComment _getTalentComment(Long talentCommentId){
    TalentComment talentComment = talentCommentRepository.findById(talentCommentId).orElseThrow(
        ()-> new IllegalArgumentException("해당 댓글이 존재하지 않습니다.")
    );
    return talentComment;
  }

  //중복코드
  private TalentReComment _getTalentReComment(Long talentReCommentId){
    TalentReComment talentReComment = talentReCommentRepository.findById(talentReCommentId).orElseThrow(
        ()-> new IllegalArgumentException("해당 대댓글이 존재하지 않습니다.")
    );
    return talentReComment;
  }
}

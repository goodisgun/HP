package com.example.exercise.service.talent;

import com.example.exercise.dto.talent.TalentCommentRequestDto;
import com.example.exercise.entity.Talent;
import com.example.exercise.entity.TalentComment;
import com.example.exercise.entity.User;
import com.example.exercise.entity.enums.UserRoleEnum;
import com.example.exercise.repository.TalentCommentRepository;
import com.example.exercise.repository.TalentRepository;
import com.example.exercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TalentCommentServiceImpl implements TalentCommentService {

  private final TalentCommentRepository talentCommentRepository;

  private final TalentRepository talentRepository;

  private final UserRepository userRepository;


  //댓글 생성
  @Override
  public ResponseEntity<Void> createTalentComment(Long talentId, User user,
      TalentCommentRequestDto talentCommentRequestDto) {
    Talent talent = _getTalent(talentId);
    user = _getUser(user.getUsername());

    TalentComment talentComment = new TalentComment(user, talent, talentCommentRequestDto);
    talentCommentRepository.save(talentComment);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  //댓글 수정
  @Override
  public ResponseEntity<Void> updateTalentComment(Long talentCommentId, User user,
      TalentCommentRequestDto talentCommentRequestDto) {
    TalentComment talentComment = _getTalentComment(talentCommentId);

    user = _getUser(user.getUsername());

    if (!talentComment.getUser().equals(user)) {
      if (user.getUserRole() != UserRoleEnum.ADMIN) {
        throw new AccessDeniedException("작성자만 댓글 수정이 가능합니다.");
      }
    }
    talentComment.updateTalentComment(talentCommentRequestDto.getContent());
    talentCommentRepository.save(talentComment);

    return ResponseEntity.ok().build();
  }

  @Override
  public void deleteComment(Long talentCommentId, User user) {
    TalentComment talentComment = _getTalentComment(talentCommentId);
    user = _getUser(user.getUsername());

    if (!talentComment.getUser().equals(user) && user.getUserRole() != UserRoleEnum.ADMIN) {
      throw new IllegalArgumentException("작성자만 댓글 삭제가 가능합니다.");
    }

    talentCommentRepository.delete(talentComment);
    }


  //중복코드
  private Talent _getTalent(Long talentId){
    Talent talent = talentRepository.findById(talentId).orElseThrow(
        ()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
    );
    return talent;
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
}

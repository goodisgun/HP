package com.example.exercise.service.gathering;

import com.example.exercise.dto.gathering.EnrollmentResponseDto;
import com.example.exercise.entity.Enrollment;
import com.example.exercise.entity.Gathering;
import com.example.exercise.entity.User;
import com.example.exercise.repository.EnrollmentRepository;
import com.example.exercise.repository.GatheringRepository;
import com.example.exercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final GatheringRepository gatheringRepository;
    private final UserRepository userRepository;

    // 참여 신청 & 취소
    @Override
    public void enrollment(Long gatheringId, User user) {
        Gathering gathering = gatheringRepository.findById(gatheringId)
                .orElseThrow(() -> new IllegalArgumentException("모임글이 존재하지 않습니다."));

        User validateUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));

        String phoneNumber = validateUser.getPhoneNumber();
        if (phoneNumber == null || !Pattern.matches("^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", phoneNumber)) {
            throw new IllegalArgumentException("핸드폰 번호를 올바르게 입력해 주세요.");
        }
        Optional<Enrollment> enrollment = enrollmentRepository.findByGatheringIdAndUserId(gatheringId, user.getId());
        enrollment.ifPresentOrElse(enrollmentRepository::delete, () -> {
            if (gathering.getCurrentEnrollmentCount() >= gathering.getMaxEnrollmentCount()) {
                throw new IllegalArgumentException("모집인원이 다 찼습니다.");
            }

            Enrollment newEnrollment = Enrollment.builder()
                    .user(validateUser)
                    .gathering(gathering)
                    .nickname(validateUser.getNickname())
                    .phoneNumber(phoneNumber)
                    .build();
            enrollmentRepository.save(newEnrollment);

            gathering.setCurrentEnrollmentCount(gathering.getCurrentEnrollmentCount() + 1);
            gatheringRepository.save(gathering);
        });
    }


    //내가 참여한 모임 보기
    @Override
    public List<EnrollmentResponseDto> getMyGathering(Long userId) {
        List<Enrollment> myEnrollments = enrollmentRepository.findAllByUserId(userId);
        if (myEnrollments.isEmpty()) {
            throw new IllegalArgumentException("참여한 모임이 없습니다.");
        }
        return myEnrollments.stream()
                .map(EnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

    //모임 게시글에서 참여 신청한 사람 리스트 보기
    @Override
    public List<EnrollmentResponseDto> getParticipantList(Long gatheringId) {
        List<Enrollment> enrollments = enrollmentRepository.findAllByGatheringId(gatheringId);
        if (enrollments.isEmpty()) {
            throw new IllegalArgumentException("참여한 사람이 없습니다.");
        }
        return enrollments.stream()
                .map(EnrollmentResponseDto::new)
                .collect(Collectors.toList());
    }

}

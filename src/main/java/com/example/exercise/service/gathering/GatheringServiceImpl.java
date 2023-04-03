package com.example.exercise.service.gathering;

import com.example.exercise.dto.gathering.AllGatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringRequestDto;
import com.example.exercise.dto.gathering.GatheringResponseDto;
import com.example.exercise.dto.gathering.GatheringUpdateRequestDto;
import com.example.exercise.entity.Gathering;
import com.example.exercise.entity.User;
import com.example.exercise.repository.GatheringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringServiceImpl implements GatheringService{

    private final GatheringRepository gatheringRepository;

    //모임 게시글 작성
    @Override
    public GatheringResponseDto createGathering(GatheringRequestDto requestDto, User user) {
        Gathering gathering = new Gathering(requestDto.getTitle(), requestDto.getContent(), requestDto.getImage(), requestDto.getMaxEnrollmentCount(),requestDto.getGatheringTime());
        gatheringRepository.save(gathering);
        gathering.setMaxEnrollmentCount(requestDto.getMaxEnrollmentCount());
        int maxEnrollmentCount = gathering.getMaxEnrollmentCount();
        return new GatheringResponseDto(gathering, maxEnrollmentCount);
    }

    //모임 게시글 전체 조회
    @Override
    @Transactional(readOnly = true)
    public List<AllGatheringResponseDto> getAllGathering() {
        List<Gathering> gatherings = gatheringRepository.findAll();
        List<AllGatheringResponseDto> responseDto = new ArrayList<>();
        for (Gathering gathering : gatherings){
            responseDto.add(new AllGatheringResponseDto(gathering));
        }
        return responseDto;
    }

    //모임 게시글 상세 조회
    @Override
    @Transactional(readOnly = true)
    public GatheringResponseDto getGathering(Long gatheringId) {
        Gathering gathering = findGathering(gatheringId);
        int maxEnrollmentCount = gathering.getMaxEnrollmentCount();
        return new GatheringResponseDto(gathering, maxEnrollmentCount);
    }

    //모임 게시글 수정
    @Override
    public GatheringResponseDto updateGathering(Long gatheringId, User user, GatheringUpdateRequestDto updateRequestDto) {
        Gathering gathering = findGathering(gatheringId);
        validateUser(gathering, user);
        int maxEnrollmentCount = gathering.getMaxEnrollmentCount();
        gathering.update(updateRequestDto,maxEnrollmentCount);

        return new GatheringResponseDto(gathering, maxEnrollmentCount);
    }

    //모임 게시글 삭제
    @Override
    public void deleteGathering(Long gatheringId, User user) {
        Gathering gathering = findGathering(gatheringId);
        validateUser(gathering, user);
        gatheringRepository.delete(gathering);

    }

    //중복 코드_존재여부
    private Gathering findGathering(Long gatheringId){
        return gatheringRepository.findById(gatheringId).orElseThrow(
                ()-> new IllegalArgumentException("해당 모임이 존재하지 않습니다.")
        );
    }

    //중복 코드_권한여부
    private void validateUser(Gathering gathering, User user) {
        if(!gathering.getUser().equals(user)){
            throw new IllegalArgumentException("수정/삭제할 권한이 없습니다.");
        }
    }
}





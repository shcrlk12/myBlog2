package com.zerobase.fastlms.member.mapper;

import com.zerobase.fastlms.member.dto.LoginHistoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LoginHistoryMapper {
    List<LoginHistoryDto> selectLoginHistory(String userId);
}

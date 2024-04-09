package com.movieland.common;

import com.movieland.entity.User;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;

@Data
@Builder
public class Session {
    private String token;
    private LocalDateTime expireDate;
    private User user;
}

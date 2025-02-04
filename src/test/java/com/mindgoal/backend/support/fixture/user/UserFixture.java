package com.mindgoal.backend.support.fixture.user;

import com.mindgoal.domain.user.entity.User;

public class UserFixture {
    public static User 오션() {
        return new User(1L,"email@gmail.com", "password", "오션", "010-0000-1111", "https://images", false);
    }

    public static User 스텝() {
        return new User(2L,"email@naver.com", "password22", "ocean", "010-0000-2222", "https://images", true);
    }

    public static User 심리_전문가() {
        return new User(3L,"email@naver.com", "password22", "ocean", "010-0000-2222", "https://images", true);
    }

    public static User 진로_전문가() {
        return new User(4L,"email@naver.com", "password22", "ocean", "010-0000-2222", "https://images", true);
    }
}

package com.mindgoal.backend.support.fixture.expert;

import com.mindgoal.domain.expert.entity.Expert;

public class ExpertFixture {
    public static Expert 심리_전문가() {
        return new Expert(
                1L,
                10,
                200,
                70,
                50000,
                85,
                90,
                4.8,
                true,
                "심리 상담",
                "10년 경력의 심리 상담 전문가",
                "다양한 심리 치료 기법을 활용한 상담 제공",
                "https://instagram.com",
                "상담사",
                "인지 행동 치료, 심리 상담",
                "심리 상담사",
                "https://youtube.com"
        );
    }

    public static Expert 진로_전문가() {
        return new Expert(
                2L,
                8,
                150,
                60,
                40000,
                80,
                85,
                4.6,
                true,
                "진로 상담",
                "8년 경력의 진로 컨설팅 전문가",
                "개인 맞춤형 진로 설계 및 상담 제공",
                "https://instagram.com",
                "컨설턴트",
                "진로 설계, 취업 컨설팅",
                "진로 상담사",
                "https://youtube.com"
        );
    }

}

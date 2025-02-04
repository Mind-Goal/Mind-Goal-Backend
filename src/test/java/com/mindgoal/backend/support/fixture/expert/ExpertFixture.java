package com.mindgoal.backend.support.fixture.expert;

import com.mindgoal.domain.expert.entity.Expert;

public class ExpertFixture {
    public static Expert 심리_전문가() {
        return new Expert(
                1L,
                3L,
                "심리 상담",
                "인지 행동 치료, 심리 상담",
                "상담사",
                "서울",
                10,
                "다양한 심리 치료 기법을 활용한 상담 제공",
                "10년 경력의 심리 상담 전문가",
                "온라인, 오프라인",
                50000,
                "https://youtube.com",
                "https://instagram.com",
                true,
                4.8,
                200,
                70,
                85,
                90
        );
    }

    public static Expert 진로_전문가() {
        return new Expert(
                2L,
                4L,
                "진로 상담",
                "진로 설계, 취업 컨설팅",
                "컨설턴트",
                "부산",
                8,
                "개인 맞춤형 진로 설계 및 상담 제공",
                "8년 경력의 진로 컨설팅 전문가",
                "온라인",
                40000,
                "https://youtube.com",
                "https://instagram.com",
                true,
                4.6,
                150,
                60,
                80,
                85
        );
    }
}

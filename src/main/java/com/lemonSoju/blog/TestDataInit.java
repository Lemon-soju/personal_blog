package com.lemonSoju.blog;

//import com.lemonSoju.blog.domain.Member;
//import com.lemonSoju.blog.domain.Post;
//import com.lemonSoju.blog.repository.PostDataRepository;
//import com.lemonSoju.blog.repository.MemerDataRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.time.LocalDateTime;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataInit {
//
//    private final PostDataRepository postDataRepository;
//    private final MemerDataRepository memerDataRepository;
//
//    /**
//     * 테스트용 데이터 추가
//     */
//    @PostConstruct
//    public void init() {
//
//        Member admin = createAdmin();
//        Member member01 = createUser();
//        Post post01 = createPost(member01, "개발자라면 꼭 알아야 할 프로그래밍 언어 5가지", "현재 개발 시장에서 인기 있는 프로그래밍 언어를 선정하고, 각 언어의 특징과 장단점을 비교하며, 개발자로서 어떤 언어를 배우면 유용한지에 대해 제안합니다. 예를 들어, Python의 간결한 문법과 다양한 라이브러리를 활용하여 빠르게 개발을 진행할 수 있다는 점이나, Java의 안정성과 대규모 프로젝트에 적합하다는 점 등을 소개합니다.");
//        Post post02 = createPost(member01, "내가 경험한 세계 여행 TOP 5", "여행 중 방문한 나라와 그 나라에서 경험한 색다른 문화와 음식, 관광지 등을 소개하며 추억에 남는 순위권 5개의 여행을 공유합니다. 예를 들어, 일본에서 찾은 마음의 평화를 느낄 수 있는 절이나 프랑스에서 맛보았던 진짜 마카롱 등의 이야기를 공유합니다. 이를 통해 독자들은 혹시 여행 계획을 세우고 있다면 도움을 받을 수 있습니다.");
//        Post post03 = createPost(member01, "일주일만에 몸매 변화를 만들어낸 루틴 5가지", "일주일 동안 지속 가능한 운동 루틴을 소개하며, 운동에 대한 팁과 지식도 함께 제공합니다. 예를 들어, 유산소 운동과 근력 운동을 적절하게 조합하여 다이어트와 근력 강화를 동시에 이룰 수 있는 방법, 운동 전과 후의 스트레칭의 중요성 등을 설명합니다. 이를 통해 독자들은 몸매 변화를 만들어내기 위해 필요한 정보와 방법을 얻을 수 있습니다.");
//        Post post04 = createPost(admin, "외국어 학습의 장점과 방법", "외국어를 학습하는 이유와 그 장점들에 대해 설명하고, 어떻게 학습해야 효과적인지에 대해 논의합니다. 구체적인 학습 방법과 도움이 되는 자료와 어플리케이션을 소개합니다.");
//        Post post05 = createPost(admin, "혼자 여행하기, 그 시작과 끝", "혼자 여행을 시작한 계기와 그 경험에 대해 소개하고, 혼자 여행을 추천하는 이유와 그리고 혼자 여행을 떠나기 전 준비해야 하는 것들을 다룹니다. 또한, 혼자 여행을 끝내고 나서의 느낌과 그것이 미친 영향에 대해서도 이야기합니다.");
//        Post post06 = createPost(member01, "IT 기술의 발전과 미래", "IT 기술의 발전 과정과 그것이 사회에 미치는 영향에 대해 다루고, 미래에는 어떤 IT 기술이 발전할 것인지 예측합니다. 또한, 그것이 어떤 변화와 혁신을 가져올 수 있는지에 대해 생각해봅니다.");
//        Post post07 = createPost(member01, "나만의 창작물 만들기", "나만의 창작물을 만들기 위해서는 어떤 과정이 필요한지, 창작물을 만들기 위해 필요한 자질이 무엇인지에 대해 설명합니다. 또한, 창작물을 만들면서 어떤 어려움과 즐거움이 있는지도 논의합니다.");
//        Post post08 = createPost(member01, "자신감을 갖는 방법", "자신감을 갖는 것이 중요한 이유와 그것이 미치는 영향에 대해 다룹니다. 또한, 자신감을 갖기 위해서는 어떤 노력과 방법이 필요한지, 자신감을 유지하는 방법은 무엇인지에 대해 논의합니다.");
//        Post post09 = createPost(member01, "나만의 힐링 루틴, 집에서 하는 5가지", "요즘은 바쁜 일상으로 인해 스트레스가 많이 쌓이는데, 나는 집에서 간단하게 해볼 수 있는 힐링 루틴을 발견해서 이를 소개하려고 한다. 이번에 소개할 것은 요가, 숨쉬기 운동, 캔들 라이트, 힐링 음악 감상, 따뜻한 차 마시기인데, 각각의 방법을 자세히 설명하고 어떤 효과가 있는지 알려줄 것이다.");
//        Post post10 = createPost(admin, "제목: 여행가서 즐기는 맛집 베스트 5", "나는 여행을 가면 반드시 현지 맛집을 찾아다니는 취미가 있다. 그래서 최근 여행 다녀온 나만의 맛집 베스트 5를 소개하려고 한다. 이번에 가본 곳은 한국, 일본, 이탈리아, 태국, 프랑스였는데, 그 나라마다 다양한 음식을 맛 볼 수 있었다. 이 중에서도 특히 강추하는 맛집은 무엇인지, 그 곳에서 먹은 음식들은 어떤 특징이 있는지 자세히 소개할 예정이다.");
//        Post post11 = createPost(member01, "나의 삶을 바꾼 자기계발서 추천", "나는 책읽기를 좋아하고, 특히 자기계발서를 많이 읽는 편이다. 그 중에서도 내 삶을 많이 변화시켜준 책을 소개하려고 한다. 이번에 추천할 책은 '성공하는 습관', '슈퍼맨이 되는 법', '좋은 습관의 힘', '바보를 위한 경제학', '뇌를 자극하는 수학' 등이다. 각 책마다 내가 어떤 부분에서 자극을 받았는지, 어떤 변화를 겪었는지 등을 소개할 예정이다.");
//        Post post12 = createPost(admin, "모바일 앱 개발을 시작하는 방법", "모바일 앱 개발에 관심이 있는 분들은 먼저 언어를 선택해야 합니다. 대표적으로 iOS 앱 개발은 Swift 언어, 안드로이드 앱 개발은 Java 또는 Kotlin 언어를 사용합니다. 이어서 개발을 위한 도구인 Xcode나 Android Studio를 설치하고 개념적인 내용을 공부하면 됩니다. 이후에는 앱에 필요한 UI/UX 디자인 방법과 테스트 방법도 함께 학습합니다. 예를 들어, 앱의 기능을 설계하는 기획 단계부터 시작해서 UI 디자인, 프로그래밍, 기능 테스트, 배포 등의 단계를 거쳐 완성합니다.");
//        Post post13 = createPost(member01, "빅데이터 분석을 위한 파이썬 라이브러리 추천", "빅데이터 분석을 하려면 파이썬을 잘 다룰 수 있어야 한다. 그래서 빅데이터 분석을 위해 필요한 파이썬 라이브러리를 소개한다. 예를 들면, Numpy, Pandas, Matplotlib, Seaborn, Scikit-Learn 등의 라이브러리를 자세히 알아보고 각각의 특징과 활용 방법을 소개한다.");
//        Post post14 = createPost(admin, "클라우드 컴퓨팅을 이용한 웹 개발", "최근에는 클라우드 컴퓨팅이 대세인데, 클라우드 컴퓨팅을 이용한 웹 개발 방법에 대해서 알아보자. 먼저 클라우드 컴퓨팅이란 무엇인지, 어떤 장단점이 있는지 알아본 후에 클라우드 컴퓨팅을 이용한 웹 개발 방법과 필요한 기술들을 소개할 것이다.");
//        Post post15 = createPost(member01, "머신러닝 알고리즘 종류와 활용 예시", "머신러닝은 최근에 가장 핫한 분야 중 하나이다. 그래서 머신러닝 알고리즘의 종류와 활용 예시를 알아보자. 선형 회귀, 로지스틱 회귀, 의사결정트리, 랜덤 포레스트, SVM, KNN 등 다양한 머신러닝 알고리즘들을 자세히 소개하고 각 알고리즘의 활용 예시도 함께 알아볼 것이다.");
//        Post post16 = createPost(member01, "머신러닝의 개념과 활용 분야", "머신러닝은 인공지능 분야에서 가장 활용도가 높은 기술 중 하나입니다. 머신러닝은 데이터를 이용하여 학습하고 예측하는 기술로, 다양한 분야에서 활용됩니다. 예를 들어, 머신러닝은 광고 추천 시스템, 스팸 필터링, 의료 진단, 주식 예측, 이미지 분석, 자연어 처리 등 다양한 분야에서 활용됩니다. 머신러닝은 크게 지도 학습, 비지도 학습, 강화 학습으로 구분됩니다. 각 학습 방법에 따라 다양한 머신러닝 알고리즘이 있으며, 이를 통해 다양한 문제를 해결할 수 있습니다.");
//        Post post17 = createPost(admin, "머신러닝 모델의 성능을 향상시키는 방법", "머신러닝 모델의 성능을 향상시키는 것은 머신러닝 기술의 핵심 중 하나입니다. 머신러닝 모델의 성능을 향상시키기 위해서는 다양한 방법이 있습니다. 예를 들어, 모델의 입력 데이터를 정규화하거나, 입력 데이터를 늘리거나, 모델 구조를 변경하거나, 하이퍼 파라미터를 조정하는 등의 방법이 있습니다. 이외에도 모델 성능 평가를 위한 다양한 지표들이 있으며, 이를 통해 모델의 성능을 정량적으로 평가할 수 있습니다.");
//        Post post18 = createPost(admin, "데이터 전처리의 중요성과 방법", "데이터 전처리는 머신러닝 분야에서 가장 중요한 단계 중 하나입니다. 데이터 전처리를 통해 데이터를 정제하고 준비하여 머신러닝 모델의 성능을 향상시킬 수 있습니다. 데이터 전처리의 방법에는 결측치 처리, 이상치 처리, 데이터 스케일링, 데이터 인코딩 등이 있습니다. 이외에도 데이터셋을 분할하여 훈련, 검증, 테스트 데이터로 나누는 것이 좋습니다.");
//        Post post19 = createPost(member01, "강화학습의 개념과 응용 분야", "강화학습은 머신러닝의 한 분야로, 에이전트가 환경과 상호작용하며 보상을 최대화하는 정책을 학습하는 기술입니다. 강화학습은 게임, 제조 공정 제어, 로봇 제어 등 다양한 분야에서 응용되고 있습니다. 강화학습은 에이전트가 특정 행동을 취함으로써 얻는 보상을 최대화하는 방향으로 정책을 개선하며, 이를 통해 최적의 정책을 찾습니다.");
//        Post post20 = createPost(member01, "머신러닝 모델의 해석성과 해석 방법", "머신러닝 모델의 해석성은 머신러닝 분야에서 가장 중요한 이슈 중 하나입니다. 머신러닝 모델은 대개 블랙박스 모델로써 내부 동작을 이해하기 어렵기 때문입니다. 하지만 머신러닝 모델의 해석성을 향상시키기 위한 방법들이 제안되고 있습니다. 예를 들어, LIME, SHAP, CAM 등의 방법을 이용하여 모델의 결정 과정을 해석할 수 있습니다.");
//        Post post21 = createPost(member01, "머신러닝 모델의 과적합과 방지 방법", "머신러닝 모델의 과적합은 머신러닝 분야에서 가장 흔한 문제 중 하나입니다. 과적합은 모델이 학습 데이터에 지나치게 적합하여 새로운 데이터에서 성능이 떨어지는 현상을 말합니다. 과적합을 방지하기 위한 방법으로는 교차 검증, 정규화, 조기 종료, 데이터 확장 등이 있습니다. 이외에도 모델 구조를 변경하여 과적합을 줄이는 방법도 있습니다.");
//        Post post22 = createPost(admin, "딥러닝의 구조와 원리", "딥러닝은 인공 신경망을 이용한 머신러닝 기법 중 하나입니다. 딥러닝 모델은 일반적으로 입력층, 은닉층, 출력층으로 구성되며, 이 층들은 각각의 노드들로 이루어져 있습니다. 각 노드는 입력값을 받아 가중치와 활성화 함수를 이용하여 출력값을 계산합니다. 이때 가중치는 모델이 학습을 통해 최적화하는 값으로, 활성화 함수는 노드의 출력값을 결정하는 함수입니다.\n" +
//                "\n" +
//                "딥러닝 모델은 일반적으로 역전파 알고리즘을 이용하여 학습합니다. 역전파 알고리즘은 손실 함수를 정의하고, 이 손실 함수를 최소화하기 위한 가중치를 찾는 과정입니다. 학습 데이터는 일반적으로 미니배치 방식으로 모델에 입력되며, 이를 통해 모델이 가중치를 업데이트합니다. 이러한 과정을 반복하면서 모델은 학습을 진행하며, 최종적으로는 손실 함수를 최소화하는 최적의 가중치를 찾게 됩니다.\n" +
//                "\n" +
//                "딥러닝 모델은 이미지 분류, 음성 인식, 자연어 처리 등 다양한 분야에서 응용됩니다. 예를 들어, 이미지 분류를 위한 딥러닝 모델은 컨볼루션 신경망(Convolutional Neural Network, CNN) 구조를 이용하여 구성됩니다. CNN은 이미지에서 특징을 추출하고, 이를 이용하여 이미지를 분류하는 방식으로 작동합니다.\n" +
//                "\n" +
//                "딥러닝 모델은 빅 데이터와 고성능 컴퓨팅 자원의 발전으로 함께 성장해왔습니다. 최근에는 딥러닝 모델의 구조와 학습 방법을 최적화하기 위한 다양한 연구들이 활발히 진행되고 있습니다.");
//        Post post23 = createPost(member01, "가상화 기술의 개념과 활용", "가상화 기술은 하드웨어나 소프트웨어 등의 자원을 가상적으로 생성하여 이용하는 기술입니다. 가상화 기술을 이용하면 물리적 자원을 최대한 활용할 수 있어서 비용을 절감하고, 유연성과 안정성을 높일 수 있습니다. 가상화 기술은 서버 가상화, 스토리지 가상화, 네트워크 가상화 등 다양한 분야에서 사용됩니다.\n" +
//                "\n" +
//                "서버 가상화는 가상 머신(Virtual Machine, VM)을 이용하여 물리적 서버 하나에서 여러 개의 가상 서버를 운영하는 기술입니다. 각 가상 서버는 독립적인 운영체제와 애플리케이션을 가지고 있으며, 서로 간섭하지 않습니다. 이를 통해 서버 자원을 효율적으로 활용할 수 있습니다.\n" +
//                "\n" +
//                "스토리지 가상화는 여러 개의 스토리지 장치를 가상적으로 통합하여 사용하는 기술입니다. 이를 통해 스토리지 공간을 효율적으로 사용하고, 유연하게 관리할 수 있습니다.\n" +
//                "\n" +
//                "네트워크 가상화는 가상 네트워크를 구성하여 물리적 네트워크를 가상적으로 분할하는 기술입니다. 이를 통해 가상적인 네트워크를 구성함으로써, 물리적인 네트워크를 관리하거나 확장하기 쉬워집니다.\n" +
//                "\n" +
//                "가상화 기술은 클라우드 컴퓨팅 등 다양한 분야에서 활용됩니다. 클라우드 컴퓨팅에서는 가상화 기술을 이용하여 가상 머신을 생성하고, 이를 이용하여 서비스를 제공합니다. 이를 통해 클라우드 서비스를 이용하는 사용자들은 자신의 요구에 맞게 가상 머신을 생성하여 이용할 수 있습니다.\n" +
//                "\n" +
//                "가상화 기술은 보안, 비즈니스 연속성 등 다양한 분야에서 유용하게 사용될 수 있습니다. 따라서 가상화 기술에 대한 이해와 활용 능력은 IT 분야에서 매우 중요한 역할을 합니다.");
//        Post post24 = createPost(admin, "DevOps 개념과 장단점", "DevOps는 Development(개발)와 Operations(운영)를 합쳐서 만든 용어입니다. 개발과 운영의 경계를 허물고, 통합된 개발과 운영을 수행함으로써 빠르고 안정적인 서비스 제공을 목표로 합니다. DevOps를 적용하면 개발팀과 운영팀이 소통하고 협력하여 시스템을 개발하고 운영할 수 있습니다.\n" +
//                "\n" +
//                "DevOps의 장점은 다음과 같습니다. 첫째, 더 빠른 배포가 가능합니다. 개발과 운영의 통합으로 릴리즈 사이클을 단축하고, 신속한 배포가 가능합니다. 둘째, 높은 품질과 안정성을 제공합니다. 개발과 운영의 통합으로 인해 더 높은 품질과 안정성을 제공할 수 있습니다. 셋째, 더 나은 협력과 커뮤니케이션이 이루어집니다. 개발팀과 운영팀이 소통하고 협력함으로써 조직 내부의 협력과 커뮤니케이션이 더 원활해집니다.\n" +
//                "\n" +
//                "하지만 DevOps의 단점도 있습니다. 첫째, 기존의 방식에 익숙한 개발자와 운영자들은 적응하기 어려울 수 있습니다. 둘째, 구성이 복잡하고 구축 비용이 높을 수 있습니다. 셋째, DevOps를 위한 툴과 기술에 대한 이해와 학습이 필요합니다.\n" +
//                "\n" +
//                "DevOps는 개발과 운영의 경계를 허물고, 통합된 개발과 운영을 수행함으로써 더욱 빠르고 안정적인 서비스를 제공할 수 있습니다. 하지만 이를 위해서는 조직 내부의 협력과 커뮤니케이션이 중요하며, 새로운 기술과 툴에 대한 이해와 학습이 필요합니다.");
//        Post post25 = createPost(member01, "클라우드 컴퓨팅의 장단점과 적용사례", "클라우드 컴퓨팅은 인터넷을 통해 가상의 컴퓨팅 자원을 제공하는 기술입니다. 클라우드 컴퓨팅의 장점은 다음과 같습니다. 첫째, 인프라 관리 비용을 절감할 수 있습니다. 둘째, 탄력적인 컴퓨팅 자원 확장이 가능합니다. 셋째, 더욱 안전하고 보안성이 높은 시스템을 구축할 수 있습니다. 하지만 클라우드 컴퓨팅을 사용할 때에는 데이터 보호와 관련된 이슈도 고려해야 합니다.\n" +
//                "\n" +
//                "제목: 머신러닝의 개념과 응용");
//        Post post26 = createPost(member01, "머신러닝의 개념과 응용", "머신러닝은 인공지능의 한 분야로, 데이터를 통해 스스로 학습하고 판단하는 기술입니다. 머신러닝은 다양한 분야에서 응용됩니다. 예를 들어, 스팸 필터링, 음성 인식, 이미지 분석, 추천 시스템 등이 있습니다. 머신러닝은 데이터를 기반으로 예측, 분류, 군집화 등 다양한 분석을 수행할 수 있습니다.");
//        Post post27 = createPost(member01, "오픈소스 소프트웨어의 장단점과 사용 사례", "오픈소스 소프트웨어는 누구나 소스 코드를 열람하고 수정할 수 있는 소프트웨어입니다. 오픈소스 소프트웨어의 장점은 다음과 같습니다. 첫째, 비용 절감이 가능합니다. 둘째, 개발자들이 함께 개발하며 생산성이 향상됩니다. 셋째, 커뮤니티 지원이 활발합니다. 하지만 오픈소스 소프트웨어를 사용할 때에는 보안과 관련된 이슈도 고려해야 합니다.");
//        Post post28 = createPost(admin, "인공지능 기술의 발전과 적용 분야" , "인공지능 기술은 빠르게 발전하고 있으며, 다양한 분야에서 응용됩니다. 예를 들어, 자율주행차, 음성인식, 언어번역, 게임 등이 있습니다. 인공지능은 기존의 문제를 해결하고 새로운 가능성을 열어주는 기술입니다. 그러나 인공지능을 사용할 때에는 개인정보 보호와 같은 이슈도 고려해야 합니다.");
//        Post post29 = createPost(admin, "사물인터넷(IoT)의 개념과 적용 분야", "사물인터넷은 사물에 센서와 통신 기능을 부여하여 인터넷과 연결된 기술입니다. 사물인터넷의 적용 분야는 다양합니다. 예를 들어, 홈 자동화, 스마트 시티, 산업 자동화 등이 있습니다. 사물인터넷은 다양한 데이터를 수집하고 분석하여 효율적인 운영과 관리를 가능케 합니다.");
//        Post post30 = createPost(admin, "가상현실(VR)의 개념과 적용 분야" , "가상현실은 가상 공간에서 인간이 체감하는 경험을 제공하는 기술입니다. 가상현실의 적용 분야는 게임을 비롯한 엔터테인먼트 분야뿐만 아니라 교육, 의료, 건축 등 다양합니다. 가상현실은 현실에서는 어려운 체험을 제공하고, 시각적으로 재미있는 경험을 가능케 합니다.");
//    }

//    private Member createAdmin() {
//        Member member = Member.builder()
//                .uid("admin")
//                .pwd("1q2w3e4r1!")
//                .name("lemonSoju")
//                .authority("ROLE_ADMIN")
//                .build();
//        memerDataRepository.save(member);
//        return member;
//    }

//    private Member createUser() {
//        Member member = Member.builder()
//                .uid("user01")
//                .pwd("1q2w3e4r1!")
//                .name("James")
//                .authority("ROLE_USER")
//                .build();
//        memerDataRepository.save(member);
//        return member;
//    }

//    private Post createPost(Member member, String title, String content) {
//        Post post = Post.builder()
//                .title(title)
//                .content(content)
//                .writer(member)
//                .createDate(LocalDateTime.now())
//                .build();
//        return postDataRepository.save(post);
//    }
//}
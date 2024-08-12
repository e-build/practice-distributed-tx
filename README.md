| event 기반 아키텍처 설계

# 이벤트 사용 이유
- 코드 결합 해소
- MQ 와의 차이점
    - MQ를 사용하지 않으면 이벤트를 발행한 인스턴스 내에서만 이벤트를 전파할 수 있지만, MQ는 분리된 타 인스턴스로 전파 가능
    - 추후 서버가 분리되는 것을 고려한다면 MQ 필요
- spring event 발행 -> 같은 인스턴스 내 구독중인 모든 spring event listener 들에게 이벤트 전파
    - spring event는 '정확히 한 번만 전달'을 보장
    - spring event만 사용할 경우 다른 인스턴스로의 전달은 불가능. 별도의 도구 활용 필요 ex) MQ, Kafka

# 메세징 솔루션
- 이벤트 발행-구독 도구에 따른 처리 전략 수립
    - Kafka - 1 -> N
    - Redis PubSub - 1 -> N
    - SQS - 1 -> 1
    - SNS - 1 -> N
    - Rabbit MQ
- 이벤트 발행 보장 기법
    - CDC
    - Transactional Outbox Pattern
        - 분산 시스템에서 메시지 전달의 일관성을 보장하는 것이 목적
        - Producer는 Outbox 저장소를 활용하여 'At-Least Once Delivery' 방식으로 메시지를 발행
        - Consumer는 메시지를 중복으로 받더라도 '멱등성'있게 처리하여 일관되게 메시지를 처리함
- 분산 트랜잭션
    - 2PC (Two Phase Commit)
    - TC/C (Try-Confirm/Cancel)
    - Saga

# 적용
### 메세징 솔루션 사용 시 고려사항
- 메세지 간의 발행-소비 순서에 대한 보장 필요
- 중복 처리에 대한 추가 작업 필요
    - '정확히 한 번만 전달'을 보장해야 함 and
    - 멱등해야 함  and
    - 중복된 메세지 소비일 경우 처리를 막아야 함
### Spring event 발행-구독 보장 전략
- 전체 흐름
    1. 로직 1 수행
    2. 이벤트 발행
    3. 이벤트 소비
    4. 로직 2 수행
- 구성 상세
- 발행자 (== 로직 1)
- 구독자 1
    - AWS의 SNS에 이벤트 발행
    - 로직 1 commit 이후에 실행 보장 필요
    - 로직 1이 정상적으로 처리되지 않으면, 이벤트는 발행되어선 안됨
- 구독자 2
    - DB에 SNS 이벤트 발행 내용 기록 (:: Transactional Outbox Pattern)
    - 구독자 1에서 이벤트 발행요청이 HTTP 통신으로 이루어지기 때문에, 실패 가능성을 대비해야 함.
    - 구독자 1에서 이벤트 발행요청이 실패할 경우, 5분 간격으로 스케줄러가 돌며 재발행
- 로직 1 - 트랜잭션 1
- 로직 2 - 트랜잭션 2
### 분산 트랜잭션 원자성 보장 전략
- 로직 1 성공 && 로직 2 실패 경우 failover 전략
    - no action
    - retry - require configuring time, unit
    - publish fail event
### 샤플 적용 시 고민되는 점
- 어떤 메세지 브로커를 사용할 지
    - AWS SQS - 100만건 까지 무료
    - AWS SNS - 100만건 까지 무료
    - RabbitMQ
    - AWS AmazonMQ
    - AWS Kinesis
    - AWS MSK (Kafka) - 비쌈
- SQS 하나로 구현하는 방식
    - 현재 모놀로식으로 서비스하고 있기 때문에 하나로 모든 이벤트를 몰아서 받고, 하나의 컨슈머에서 각 핸들러들에게 전파할 수 있음.
    - 정확히 한번만 전달한다는 전제
- SNS-SQS 구조로 갈 경우
    - Q. 이벤트가 추가될 떄마다 SNS의 토픽과, SQS 큐를 AWS 콘솔에서 생성해줘야 하는 건가?
        - 한번 생성하고 발생되는 이벤트를 동적으로 발행/소비할 수 있는 방법을 찾아보자

# 추가 탐색 필요
- Zero Payload
- SQS Dead Letter queue 활용 유즈케이스
- SQS Message Group 활용만으로는 EDA 구현이 어렵나?
    - 어려움.
    - 추후 모놀로식에서 MSA 로 전환 시, 이벤트 발행이 아니라 특정 큐에 전송해줘야 하는 형태가 됨.
    - 이벤트 발행이 아니면, 모든
- SNS, SQS 를 모두 사용 시 어디에 FIFO 설정을 해야할까
    - https://docs.aws.amazon.com/ko_kr/sns/latest/dg/fifo-message-grouping.html

- SNS 와 SQS Message Group 을 함께 활용하는 방법은 어떨까?
    - SNS+SQS vs SNS+SQS Message Group
- SNS, SQS 생성 및 구독 자동화
    - 언제 해당 작업이 필요할까?
        - 도메인을 별도 서버로 분리 할 때
    - 어떤 작업이 필요할까?
        - SNS 토픽 생성
        - SQS 큐 생성
        - SNS 구독에 SQS 추가
- Transactional Outbox Pattern의 publish 플래그 true 변경 시점은?

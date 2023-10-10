230915 네트워크 프로그래밍 3주차 정리

0. 헤더 순서 : (Physical >) Ethernet(or Wi-Fi) > IP > TCP > Data
- Ethernet 헤더 : 도착 주소 / 출발 주소 / 타입(= 뒤에 오는 헤더의 종류) [2계층에서 주소는 6 byte짜리 MAC 주소]

1. IP 헤더
- Version : 4 아니면 6
- IHL : 헤더 길이 / 4
- Type of Service(TOS) : 패킷의 우선순위(0~2), 설정(3~6), 사용안함(7) => 지금와서는 사용하지 않음
- Identification : 패킷의 고유번호. 각 조각이 동일한 데이터그램에 속하면 동일한 Identification 번호를 가짐.
- Flags : 한 패킷을 쪼개서 보내는 경우에 사용. Fragmentation 옵션
- Fragment offset : Fragment 번호. 쪼갠거 합칠때 씀
	- 패킷 사이즈가 큰 것을 한 번에 보내면, 오버헤드가 적지만 오류에 약하고
	- 패킷을 쪼개서 여러 번 보내면, 오류에 강하지만 오버헤드가 많아진다
	- 실제로는 TCP에서 알아서 잘라주기 때문에 쪼개지 않음. 
	- 그래서 IPv6에서는 해당 필드가 존재하지 않음
- TTL : Time To Live. 최대 홉 수. 라우터 하나 지날 때마다 1씩 감소하고, 0이 되면 패킷을 날려버림. 무한루프 막기 위해 만들어짐
	- 윈도우에서는 초기값을 128로 설정함
	- 맥, 리눅스에서는 초기값을 64로 설정
	- 같은 소스에서 보낸 패킷이어도 TTL은 다를 수 있음
- Protocol : IP 헤더 뒤에 뭐가 있는지 알려줌. TCP 헤더는 6, UDP 헤더는 17
- Checksum : IP 헤더 데이터가 깨졌는지 확인하는 용도. 헤더의 값을 전부 더했을때 FFFF....로 되면 정상임.
	- 성능은 구린데도 쓰고 있음. 왜냐면 1, 2계층에서 알아서 검사하기 때문에 에러 날 일이 별로 없음
	- 1 홉으로 데이터를 보낼때는 1, 2계층을 거치는데 이때 이더넷은 CRC(32 bit)라는걸 붙여서 보냄 -> 에러를 잘 잡음
	- IPv6에서는 Checksum 삭제됨

2. Network Layer (= Internet Layer)
- 3계층
- IP는 가장 많이 사용되는 3계층 프로토콜이고, 자바가 유일하게 이해하는 프로토콜임
- IPv4는 32 bit 주소, IPv6은 128 bit 주소를 사용함

3. Transport Layer 
- 4계층
- TCP :
	- 패킷을 제대로 순서대로 받았는지(Sequence Number), 데이터가 유실되거나 손상되지 않았는지 책임짐
	- Sequence Number : 패킷을 보낼때마다 1씩 증가하는게 아니라 비트 수를 고려해서 증가함. (1:100 > 101:100)
	- 패킷이 유실되면 재전송 하라고 요청함 
	- 대신에 TCP는 오버헤드가 많음. 기능이 많아서(순서 맞추기, 데이터 재전송 요청하기 등등)
	- ACK Number는 다음에 받아야 할 데이터의 Sequence Number가 들어감.
	- 같은 ACK Number가 세 번 오면 재전송 해줘야함 (duplicate Ack)
	- 옛날 재전송 방법으로는 정해진 시간동안 Ack 안오면 재전송 하는게 있었음 (timeout)
- UDP : 오류는 검출하지만(Checksum) 순서는 맞추지 않음. Sequence Number가 없음

- TCP = Reliable Protocol / UDP = Unreliable Protocol(의외로 쓸만함)

4. Application Layer
- 5계층
- HTTP : 웹 프로토콜
- SMTP, POP, IMAP : 이메일용 프로토콜
- FTP, FSP, TFTP ... : 파일 전송 프로토콜
- SIP(Skype) : 전화 프로토콜


5. IP 주소, Domain Name
- IP version 4는 32 bit를 8 bit씩 끊어서 10진수 3자리 4개로 표현함. (= Dotted Quad Format)
- IP 주소를 다 외울 수는 없으니까 그걸 글자로 바꾼게 Domain Name
- Domain Name을 알고있는게 DNS
- DHCP 서버는 IP 주소를 할당해주고, DNS 서버의 주소를 알려줌
- 인터넷 같은거에 연결하면 DHCP 있는지 브로드캐스팅으로 찾음
- IP version 6은 128 bit를 16 bit씩 끊어서 16진수 4자리 8개로 표현함. 
- 127.0.0.1 은 local loopback address 라고 해서 자기 자신을 의미
- 255.255.255.255 는 broadcast address 라고 해서 로컬 네트워크에 접속한 모든 노드에게 전송할 목적임

6. Port
- 4계층에서 쓰임
- 어플리케이션을 구분하기 위해 사용함
- 유명한 어플리케이션은 포트가 정해져있기도 함
- echo 포트(7)는 두 노드가 제대로 연결 되었나 확인하려는 용도임. 받은 대로 똑같이 보내주는거
- daytime 포트(13)은 그 서버의 시간을 문자로 보내줌

7. tracert (= traceroute)
- ICMP 프로토콜을 사용함
- TTL = 1 로 설정하고 보내면 받은 쪽에서 Reply를 해줌. 그때 받은 IP 주소로 라우터를 알아냄
- TTL 1을 3번씩 보냄
- 자기 주소를 알려주지 않는 경우도 많음

8. Internet address Blocks
- 큰 네트워크까지 들어오기 위해서 앞에 일부 비트를 봄
- 네트워크에 들어온 뒤에는 호스트까지 알아서 연결시켜줌
- /24 라고 하면 앞에 24자리가 네트워크를 의미하는 것

9. Network Address Translation (NAT, 공유기)
- 공유기는 아이피를 공유함
- 외부 아이피 하나 아래에 내부 아이피 여러개를 두는 방식
- 192.168.x.x 같은거
- 포트 넘버를 가지고 내부 노드를 구별해줌

10. Internet Standards
- Internet Engineering Task Force (IETF) 단체가 인터넷 표준을 정의했음
- Request For Comments (RFCs)가 표준 문서













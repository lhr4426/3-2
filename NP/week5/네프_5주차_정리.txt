Stream : 데이터 흐름. 1 바이트 단위로 데이터가 왔다갔다 한다
Input Stream 클래스 안에는 Read()!!!!
Output Stream 클래스 안에는 Write()!!!!
모든 데이터가 1 byte는 아니라서 쪼개서 보내야 한다는게 정말 불편함. 그걸 편하게 해주는 클래스들도 있음

인코딩 : 문자를 표현하는 약속. 여러가지 종류가 있음
	- ASCII : 영어랑 숫자, 특수문자(+ 제어문자)를 7 bit로 표현함 (1 bit는 안씀)
	- Unicode : 영숫자/특수문자 제외하고도 전세계 문자를 2 byte로 표현함
	- UTF-8 : 유니코드 기반의 인코딩. 영어는 1 byte, 다른나라 언어는 3 byte로 표현함 (거의 표준)

콘솔에 찍든 파일에 찍든 소켓에 넣어서 찍든 스트림을 활용한다는건 똑같음

Reader, Writer는 문자열을 읽고 쓰는걸 편리하게 한 클래스

스트림은 Synchronous 하다! (Non-Blocking 방법도 있기는 한데 기본적으로는 아님)
Synchronous : 해당 작업이 끝날 때 까지 다음 작업으로 넘어가지 않는다.

< Output Stream 실습 >

write(byte[] data) : array 통으로 다 보냄
write(byte[] data, int offset, int length) : array 일부를 보냄 (offset : 시작점, length : 시작점으로부터 길이)
Ex. write(data, 2, 5) : data[2] ~ data[5] 까지 보냄

Carriage Return : 타자기에서 따온 말. 글을 다 쓰고 나서 맨 앞줄로 오는 것 (\r) [윈도우만 씀. 맥/리눅스는 안씀]
Line feed : 개행 (\n)

콘솔 스트림 : System.out
파일 스트림 : FileOutputStream
BufferedOutputStream 이 좀 더 빠름
DataOutputStream은 데이터를 그 크기대로 채워서 보냄

DataOutputStream.writeInt() : 4 byte 채움
DataOutputStream.writeDouble() : 8 byte 채움
DataOutputStream.writeUTF() : 앞에 무조건 2 byte(길이용)이 붙고, 뒤에는 데이터가 붙음

< Input Stream 실습 >

read(byte[] input) : input array 최대 길이까지 받음
read(byte[] input, int offset, int length) : input array의 offset 부터 "채우기 시작해서" length 까지 채움

DataInputStream.readInt() : 4 byte 가져옴
DataInputStream.readDouble() : 8 byte 가져옴
DataInputStream.readUTF() : 7 byte 가져옴 (뒤 5 byte가 데이터)




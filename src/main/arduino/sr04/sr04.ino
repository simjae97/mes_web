int trigPin = 6;
int echoPin = 7;
 
void setup(){
  Serial.begin(9600);       // 시리얼 속도 설정      
  pinMode(echoPin, INPUT);   // echoPin 입력    
  pinMode(trigPin, OUTPUT);  // trigPin 출력    
}

void checkfloat(){
  long duration, distance;
  digitalWrite(trigPin, HIGH);  // trigPin에서 초음파 발생(echoPin도 HIGH)        
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);    // echoPin 이 HIGH를 유지한 시간을 저장 한다.
  distance = ((float)(340 * duration) / 1000) / 2; 
 
  
  Serial.println(distance);
 
  delay(100);

} 
// void loop(){
//   if (Serial.available() > 0) {
//     String command = Serial.readString(); // 데이터를 읽음
//     checkfloat();
//   }
// }

void loop(){
  checkfloat();
}
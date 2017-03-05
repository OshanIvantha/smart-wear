void setup() {
  Serial.begin(9600);
}

void loop() {
  int s1 = analogRead(A0);
  int s2 = analogRead(A1);

  Serial.print("[");

  if (s1 > 999) {
  } else if (s1 > 99) {
    Serial.print("0");
  } else if (s1 > 9) {
    Serial.print("00");
  } else {
    Serial.print("000");
  }
  Serial.print(s1);

  Serial.print("--");

  if (s2 > 999) {
  } else if (s2 > 99) {
    Serial.print("0");
  } else if (s2 > 9) {
    Serial.print("00");
  } else {
    Serial.print("000");
  }
  Serial.print(s2);

  Serial.println("]");
}

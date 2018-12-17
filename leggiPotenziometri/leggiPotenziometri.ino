void setup() 
{
  Serial.begin(9600);
}

bool leggi=false;
byte a='0',b='0',c='0';
void loop() 
{
  if(Serial.available()>0)
  {
    leggi=Serial.read()=='0';
  }
  if(!leggi)
    return;
  Serial.write(a);
  Serial.write(b);
  Serial.write(c);
  a = ((a-'0')+1)%10+'0';
  leggi=false;
  delay(100);
}

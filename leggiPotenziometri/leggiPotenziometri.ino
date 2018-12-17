void setup() 
{
  Serial.begin(9600);
}

bool leggi=false;
byte a='a',b='a',c='a';
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
  a = ((a-'a')+1)%26+'a';
  leggi=false;
  delay(100);
}

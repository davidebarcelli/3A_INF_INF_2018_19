#include <errno.h>
#include <fcntl.h> 
#include <string.h>
#include <termios.h>
#include <unistd.h>
#include <stdio.h>
#include "serialLib.h"

int set_interface_attribs (int fd, int speed, int parity)
{
	struct termios tty;
	memset (&tty, 0, sizeof tty);
	if (tcgetattr (fd, &tty) != 0)
	{
		printf ("error %d from tcgetattr", errno);
		return -1;
	}

	cfsetospeed (&tty, speed);
	cfsetispeed (&tty, speed);

	tty.c_cflag = (tty.c_cflag & ~CSIZE) | CS8;     // 8-bit chars
	// disable IGNBRK for mismatched speed tests; otherwise receive break
	// as \000 chars
	tty.c_iflag &= ~IGNBRK;         // disable break processing
	tty.c_lflag = 0;                // no signaling chars, no echo,
	// no canonical processing
	tty.c_oflag = 0;                // no remapping, no delays
	tty.c_cc[VMIN]  = 0;            // read doesn't block
	tty.c_cc[VTIME] = 5;            // 0.5 seconds read timeout

	tty.c_iflag &= ~(IXON | IXOFF | IXANY); // shut off xon/xoff ctrl

	tty.c_cflag |= (CLOCAL | CREAD);// ignore modem controls,
	// enable reading
	tty.c_cflag &= ~(PARENB | PARODD);      // shut off parity
	tty.c_cflag |= parity;
	tty.c_cflag &= ~CSTOPB;
	tty.c_cflag &= ~CRTSCTS;

	if (tcsetattr (fd, TCSANOW, &tty) != 0)
	{
		printf ("error %d from tcsetattr", errno);
		return -1;
	}
	return 0;
}

void set_blocking (int fd, int should_block)
{
	struct termios tty;
	memset (&tty, 0, sizeof tty);
	if (tcgetattr (fd, &tty) != 0)
	{
		printf ("error %d from tggetattr", errno);
		return;
	}

	tty.c_cc[VMIN]  = should_block ? 1 : 0;
	tty.c_cc[VTIME] = 5;            // 0.5 seconds read timeout

	if (tcsetattr (fd, TCSANOW, &tty) != 0)
		printf ("error %d setting term attributes", errno);
}

int fd;
char buf [3];

JNIEXPORT jint JNICALL Java_Pannello_apriSeriale (JNIEnv *env, jclass obj, jstring myString)
{
	const char* portname = (*env)->GetStringUTFChars(env,myString,NULL);
	//char *portname = "/dev/ttyUSB0";

	fd = open (portname, O_RDWR | O_NOCTTY | O_SYNC);
	if (fd < 0)
	{
		printf ("error %d opening %s: %s", errno, portname, strerror (errno));
		return -1;
	}
	set_interface_attribs (fd, B9600, 0);  // set speed to 115,200 bps, 8n1 (no parity)
	set_blocking (fd, 0);                // set no blocking
	int n;
	do
	{
		char c;
		write(fd,"0",1);
		n=read(fd,&c,1);
	}while(n<1);
	set_blocking (fd, 1);
}

JNIEXPORT void JNICALL Java_Pannello_leggiDaSeriale (JNIEnv *env, jclass obj)
{
	if(fd<0)
		return;
	write (fd, "0", 1);
	int n = read (fd, buf, 3);
}

JNIEXPORT jint JNICALL Java_Pannello_prendiValoreLetto
  (JNIEnv *env, jclass obj, jint i)
{
	if(i<0 || i>2)
		return 0;
	return buf[i];
}


JNIEXPORT void JNICALL Java_Pannello_chiudiSeriale (JNIEnv *env, jclass obj)
{
	close(fd);
	fd=-1;
}



// Controls TMP102 sensor

#include "mbed.h"

#define ADDR7BIT 0x48
#define SENSOR_RESOLUTION 0.0625  // degrees celcius per LSB
#define TEMP_REG_ADDR 0x00  // read-only temperature register

I2C i2c(p9, p10);
const int addr8bit = 0x48 << 1;
const char tempRegAddr = TEMP_REG_ADDR;
unsigned char data[2];

float getTemp()
{
    i2c.write(addr8bit,&tempRegAddr,1);
    i2c.read(addr8bit, data, 2);
    float temp = ( ((data[0]&0x7)<<8) | data[1] ) * SENSOR_RESOLUTION;
    if ( (data[0]>>3)&0x1 ) temp *= -1;  // converts temp to negative if MSB is 1 (hopefully not necessary in our house)
    return temp;
}
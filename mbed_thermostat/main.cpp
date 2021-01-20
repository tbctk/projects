/* mbed Microcontroller Library
 * Copyright (c) 2019 ARM Limited
 * SPDX-License-Identifier: Apache-2.0
 */

#include "mbed.h"
#include "platform/mbed_thread.h"
#include "input.h"
#include "output.h"
#include "rtc.h"
#include "sensor.h"


// Blinking rate in tens of milliseconds
#define CYCLE_TIME_10MS 100
#define TEMP_OFFSET 0
#define T_OVER_V 1
//#define FAKE_IT     // for testing



int main()
{    
    // Initialize outputs
    DigitalOut powerLed(LED1);
    DigitalOut heating(LED4);
    
    // Initialize inputs  
    AnalogIn tempDial(p15);
    I2C tempSensor(p9, p10);  
    
    
    // Initialize integers
    int turn_on_time[2] = [0,0]; // [hours,minutes] in 24h clock
    int turn_off_time[2] = [0,0];
    int myTemp;
    int curTemp;
    int count = 0;

    
    while (true) {   
            
        if ( count = CYCLE_TIME_10MS ) {
            count = 0;
            myTemp = tempDial*T_OVER_V;
            curTemp = tempSensor*T_OVER_V;
            if ((curTemp < myTemp + TEMP_OFFSET && !heating) ||
                (curTemp >= myTemp + TEMP_OFFSET && heating)) {
                heating = !heating;
            }
        }
        count++;
        powerLed = !powerLed;
    }
}




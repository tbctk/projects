#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "polyheader.h"

/*
 * Utilities associated with polyapp: parsing lines into integers and evaluating powers
 * ************************************************************************
 * * Author                   Dept      Date          Notes
 * ************************************************************************
 * * Tarik Bouchoutrouch-Ku   Physics   Apr 14 2020   Finished project
 * */

void parse (char line[50], int *coef, int *exp)
{
	// Takes a line from the data file as a string and stores the coefficient and exponent into pointers	
	*coef = atoi(strtok(line," "));
	*exp = atoi(strtok(NULL," "));
}

int powi (int x, int exp)
{
	// Returns x^exp by multiplying x by itself, exp times
	int result = 1;
	for (int i = 0; i < exp; i++)
	{
		result *= x;
	}
	return result;
}

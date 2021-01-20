#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "polyheader.h"

/*
 * Main program to evaluate a polynomial
 * ************************************************************************
 * * Author                   Dept      Date          Notes
 * ************************************************************************
 * * Tarik Bouchoutrouch-Ku   Physics   Apr 14 2020   Finished project
 * */

int main (int argc, char *argv[])
{
	// Declare file variable
	FILE *data;
	// Check that an argument has been passed
	if ( argc < 2 ) {
		fputs("Error: no filename\n",stderr);
		exit(-1);
	}
	// Proceed with main only if the file exists
	if ( data = fopen(argv[1],"r") ) {
		// Initializing variables
		char line[50];
		int *thisCoef = (int*) malloc(sizeof(int*));
		int *thisExp = (int*) malloc(sizeof(int*));
		if ( thisCoef == NULL || thisExp == NULL ) {
			fputs("Error: ran out of memory while retrieving data\n",stderr);
			exit(-1);
		}
		int values[5] = { -2, -1, 0, 1, 2 };
		// Iterate through lines of the file
		while ( fgets(line,49,data) != NULL )
		{
			//Parse the data into thisCoef and thisExp
			parse(line,thisCoef,thisExp);
			// Throw an error if addPolyTerm unsuccessful
			if ( addPolyTerm(*thisCoef,*thisExp) != 0 ) {
				fputs("Error: ran out of memory while adding terms\n",stderr);
				exit(-1);
			}
		}
	
		// Print polynomial to screen
		displayPolynomial();
	
		// Evaluate polynomial at each of the given values
		for ( int i = 0; i < 5; i++ )
		{
			printf("for x=%d, y=%d\n",values[i],evaluatePolynomial(values[i]));
		}
		free(thisCoef);
		free(thisExp);
		if ( cleanUp() != 0 ) {
			fputs("Error: ran out of memory while cleaning up",stderr);
		}
		exit(0);

	} else {
		fputs("Error file does not exist\n",stderr);
		exit(-1);
	}
}

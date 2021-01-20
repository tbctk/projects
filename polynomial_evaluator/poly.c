#include <stdio.h>
#include <stdlib.h>
#include "polyheader.h"

/*
 * Program implements a linkedlist representing a polynomial
 * ************************************************************************
 * * Author                   Dept      Date          Notes
 * ************************************************************************
 * * Tarik Bouchoutrouch-Ku   Physics   Apr 14 2020   Finished project
 * */

// Define structure for a polynomial term node in the polynomial linked list
struct PolyTerm
{
	int coeff;
	int expo;
	struct PolyTerm *next;
};

// Initialize the head node to null indicating an empty list
struct PolyTerm *head = NULL;

// A method to add a term to our polynomial linked list
int addPolyTerm (int coef, int exp)
{
	// Allocate memory for a new term
	struct PolyTerm *newTerm = (struct PolyTerm *) malloc(sizeof(struct PolyTerm));
	
	// Returns -1 if there is an error while allocating memory
	if ( newTerm == NULL ) {
		return -1;
	}
	
	// Give values to the fields of the new term
	newTerm->coeff = coef;
	newTerm->expo = exp;
	newTerm->next = NULL;

	if ( head == NULL ) { // Sets new term as head if list is empty
		head = newTerm;
		return 0;
	} else if ( exp == head->expo ) { // Adds coefficients if new term has the same exponent as head
		head->coeff += coef;
		return 0;
	} else if ( exp < head->expo ) { // Puts new term at the head of the list if its exponent is less than the head's
		newTerm->next = head;
		head = newTerm;
		return 0;
	}
	
	// Now we know that the new term's exponent is greater than the head's
	// So we create an iterator pointer to go through the terms, starting at head
	struct PolyTerm *cur = head;
	
	// Iterate through the terms and insert the new term at its correct place
	while ( cur->next != NULL ) {
		if ( exp > cur->next->expo) { // Continue if exp is greater than the exponent of the next term
			cur = cur->next;
		} else if ( exp == cur->next->expo ) { // Add coef to the next term's coefficient if they have the same exponent
			cur->next->coeff += coef;
			return 0;
		} else if ( exp < cur->next->expo ) { // If the next term's exponent is greater than exp, we insert the new term before the next
			newTerm->next = cur->next;
			cur->next = newTerm;
			return 0;
		}
	}
	// If we reach the end of the polynomial, then we append the new term
	cur->next = newTerm;
	return 0;
}

// A method to display the polynomial
void displayPolynomial ()
{
	// Use an iterator to iterate through the terms
	struct PolyTerm *cur = head;
	while ( cur->next != NULL )
	{
		// Print each term
		printf("%dx%d",cur->coeff,cur->expo);
		// Print a + sign if the next term is positive
		if ( cur->next->coeff >= 0 ) {
			printf("+");
		}
		cur = cur->next;
	}
	// Print the last term
	printf("%dx%d\n",cur->coeff,cur->expo);
}

// Evaluates the polynomial at x
int evaluatePolynomial (int x)
{
	int result = 0;
	// Creates an iterator
	struct PolyTerm *cur = head;
	// Adds the first term at x
	result += cur->coeff * powi(x, cur->expo);	
	while ( cur->next != NULL ) {
		// Adds each next term at x
		cur = cur->next;
		result += cur->coeff * powi(x, cur->expo);
	}
	return result;
}

// Method to free all allocated memory
int cleanUp() {
	struct PolyTerm *cur = head;
	struct PolyTerm *last = (struct PolyTerm *) malloc(sizeof(struct PolyTerm));
	if ( last == NULL ) return -1;
	while ( cur->next != NULL ) {
		last = cur;
		cur = cur->next;
		free(last);
	}
	free(cur);
	return 0;
}

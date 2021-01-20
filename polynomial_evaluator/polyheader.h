/*
 * Header file for the polyapp project
 * * ************************************************************************
 * * Author                   Dept      Date          Notes
 * ************************************************************************
 * * Tarik Bouchoutrouch-Ku   Physics   Apr 14 2020   Finished project
 * */

// Global variables
extern struct PolyTerm *head;

// Global functions
int addPolyTerm(int coeff, int expo);
void displayPolynomial();
int evaluatePolynomial(int x);
void parse(char line[50], int *coeff, int *expo);
int powi(int x, int expo);
int cleanUp();

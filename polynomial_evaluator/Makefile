# Makefile compiles .c files into executable; also has clean function
# By Tarik Bouchoutrouch-Ku on April 14, 2020

polyapp: polyapp.o utils.o poly.o
	gcc -o polyapp polyapp.o utils.o poly.o
polyapp.o: polyapp.c
	gcc -c polyapp.c
utils.o: utils.c
	gcc -c utils.c
poly.o: poly.c
	gcc -c poly.c
clean:
	rm -f *.o polyapp

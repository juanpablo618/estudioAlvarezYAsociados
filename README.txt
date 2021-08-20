

JAVA EE APPLICATION

El objetivo principal de este software es el manejo de expedientes.
cada Exp tiene un id que lo identifica univocamente pero lo más importante para
diferenciarlo de otro exp. es el nro de orden, nro por el cual se linkea el exp
virtual con el fisico que está en el estudio juridico.

Existen 3 tipos de exp. 
"administrativo"
"judicial"
"sin carpeta"

estos Strings deben respetarse por que son usados como filtros y para las creaciones
por ejemplo los exp. administrativos en la lista su fondo es blanco
los judiciales son verdes
y los sin carpeta son color burlywood.


El ciclo de vida:
Lo primero que pasa cuando una persona nueva se llega al estudio es que se le crea
un exp. este comienza siendo "sin carpeta" por que no se sabe el curso legal que 
tomará ese exp.
Se lo puede pasar a administrativo, ahora con una nueva funcionalidad que hay en 
la pantalla de edición de un exp. sin carpeta, luego de administrativo solo puede 
moverse a ser judicial y será el ultimo tipo en el que quedará.



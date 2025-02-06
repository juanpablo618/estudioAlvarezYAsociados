

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


el botón amarillo debe:

a la agenda seleccionada = pasarla a reagendada

y debe crear una agenda nueva con todos los mismos datos que la anterior
solo que realizado = no
y fecha = 1 día hábil màs q la actual (seleccionada)


Muy buenos días!!! Tenemos novedades en el sistema y deberían ser buenas!! jaja Porfa presten atención al video que les mando a continuación, y la siguiente explicación. A partir de hoy, en los turnos y agendas diarias personales van a encontrar una nueva funcionalidad del sistema que consiste en dos botones nuevos que han sido agregados para que el sistema sea más "amigable" (ya estamos trabajando en un tercero, pero quedará para más adelante). Estos dos botones nuevos son solo para las agendas, no para las actividades ni turnos y tienen las siguientes funciones: 
1. BOTÓN AMARILLO: REAGENDAR PARA PRÓXIMO DÍA HÁBIL Apretando este botón, automáticamente se crea una agenda idéntica en blanco (no realizada) para el día siguiente hábil, y se marca la agenda del día original en amarillo. Se supone que esta función va a colaborar para que no queden más agendas diarias en blanco, y es una herramienta para reagendar para el día siguiente de un modo mucho más simple (nos ahorramos varios pasos). Es importante remarcar que esta función copia la agenda idéntica, y no se puede editar. Recomendamos que la utilicen cuando ya se acerca la hora final del laburo y vemos que no llegamos con todo lo que teníamos agendado. 
2. BOTÓN VERDE FLUOR: MARCAR AGENDA COMO REALIZADA: En este caso, a los fines de evitar pasos, presionando el botón se marca la actividad del día directamente como realizada. Pero OJO, no es una actividad que vayamos a utilizar con tanta frecuencia, porque va en contra de la regla (que todos sabemos) en donde generalmente toda agenda realizada va concatenada con una agenda futura que hay que generar. 
3. BOTON VERDE OSCURO: VER Y EDITAR AGENDAS: Este botón, que ya existía y será el próximo para mejorar, es el que seguiremos utilizando para crear las agendas concatenadas con las actividades que tenemos diariamente. Desde allí podemos reagendar y editar las agendas, marcarlas como realizadas o reagendadas según el caso, etc.  Es el que seguiremos utilizando para la mayoría de las actividades diarias, a los fines de generar las agendas próximas conectadas con las tareas diarias que vamos haciendo. 
Asimismo, agregamos otro servidor para que el sistema tenga mayor velocidad y archivamos las agendas pasadas del año 2022 (solo quedó el año 2023 para que podamos ver en el sistema). De esta manera se supone que el sistema debería funcionar mucho mejor En caso de necesitar las agendas del 2022 (o 2021) Juan tiene un archivo de Excel de respaldo con todas las agendas pasadas. 
Porfa usemos el sistema responsablemente, sin perder de vista la lógica de siempre: "todo expediente debe tener una agenda futura". 
P.D.: no queremos ver más una agenda pasada en blanco(sin realizar!). Lo que no llegamos a realizar se reagenda para el próximo día hábil!!!